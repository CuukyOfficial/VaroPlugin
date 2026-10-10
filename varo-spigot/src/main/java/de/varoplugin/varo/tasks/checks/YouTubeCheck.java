package de.varoplugin.varo.tasks.checks;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import de.varoplugin.varo.Main;
import de.varoplugin.varo.alert.Alert;
import de.varoplugin.varo.alert.AlertType;
import de.varoplugin.varo.config.VaroConfig;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.YouTubeVideo;
import de.varoplugin.varo.tasks.Task;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class YouTubeCheck implements Task {

    private static final String APPLICATION_NAME = "VaroPlugin";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    private static final long PLAYLIST_MAX_RESULTS = 10L;

    @Override
    public void check() {
        loadVideos(false);
    }

    public static void loadVideos(boolean silent) {
        if (!VaroConfig.YOUTUBE_ENABLED.getValue())
            return;

        final String apiKey = VaroConfig.YOUTUBE_API_KEY.getValue();
        final String identifier = VaroConfig.YOUTUBE_IDENTIFIER.getValue();

        final VaroPlayer[] players = VaroPlayer.getAlivePlayer().toArray(new VaroPlayer[0]);

        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Main.getInstance().getLogger().log(Level.INFO, "Checking for new Youtube Videos...");
            try {
                YouTube youtube = getYoutubeService();
                Map<VaroPlayer, List<YouTubeVideo>> playerVideos = new HashMap<>();

                for (VaroPlayer vp : players) {
                    if (vp.getStats().getYoutubeHandle() == null) {
                        // not having a youtube handle counts as not having uploaded any videos
                        playerVideos.put(vp, Collections.emptyList());
                        continue;
                    }

                    playerVideos.put(vp, findVideos(youtube, apiKey, identifier, vp));
                }

                Bukkit.getServer().getScheduler().runTask(Main.getInstance(), () -> handleVideos(playerVideos, silent));
            } catch (Throwable t) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Unable to load Youtube videos", t);
                return;
            }
            Main.getInstance().getLogger().log(Level.INFO, "Finished checking for new Youtube Videos");
        });
    }

    private static YouTube getYoutubeService() {
        NetHttpTransport httpTransport = new NetHttpTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME).build();
    }

    private static List<YouTubeVideo> findVideos(YouTube service, String apiKey, String identifier, VaroPlayer player) throws IOException {
        YouTube.Channels.List channelRequest = service.channels().list(Collections.singletonList("contentDetails"));
        ChannelListResponse channelResponse = channelRequest.setKey(apiKey).setForHandle(player.getStats().getYoutubeHandle()).execute();
        if (channelResponse.getItems() == null || channelResponse.getItems().isEmpty()) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Received null or empty items while fetching channel details for player "
                    + player.getName() + "! Make sure the player's YouTube handle is correct and the channel's uploads are accessible!");
            return Collections.emptyList(); // invalid handle or inaccessible channel counts as not having uploaded videos
        }

        Channel channel = channelResponse.getItems().get(0);
        ChannelContentDetails contentDetails = channel.getContentDetails();
        if (contentDetails == null) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Received null content details while fetching channel details for player " + player.getName());
            return null;
        }

        ChannelContentDetails.RelatedPlaylists relatedPlaylists = contentDetails.getRelatedPlaylists();
        if (relatedPlaylists == null) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Received null related playlists while fetching channel details for player " + player.getName());
            return null;
        }

        String uploads = relatedPlaylists.getUploads();
        if (uploads == null) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Received null upload playlist id while fetching channel details for player " + player.getName());
            return null;
        }

        YouTube.PlaylistItems.List playlistRequest = service.playlistItems().list(Arrays.asList("snippet", "contentDetails"));
        PlaylistItemListResponse playlistResponse = playlistRequest.setKey(apiKey).setMaxResults(PLAYLIST_MAX_RESULTS).setPlaylistId(uploads).execute();

        if (playlistResponse.getItems() == null || playlistResponse.getItems().isEmpty()) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Received null or empty videos while fetching videos for player " + player.getName());
            return null;
        }

        List<YouTubeVideo> videos = new ArrayList<>();
        for (PlaylistItem item : playlistResponse.getItems()) {
            PlaylistItemSnippet videoSnippet = item.getSnippet();
            if (videoSnippet == null) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Received null snippet while fetching videos for player " + player.getName());
                return null;
            }

            String title = videoSnippet.getTitle();
            if (title == null) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Received null title while fetching videos for player " + player.getName());
                return null;
            }

            if (!title.toLowerCase().contains(identifier.toLowerCase())) {
                Main.getInstance().getLogger().info("Ignoring video '" + title + "' for player "
                        + player.getName() + " because its title does not contain '" + identifier + "'");
                continue;
            }

            if (title.length() > 200)
                title = title.substring(0, 200);

            PlaylistItemContentDetails videoContentDetails = item.getContentDetails();
            if (videoContentDetails == null) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Received null content details while fetching videos for player " + player.getName());
                return null;
            }

            String id = videoContentDetails.getVideoId();
            if (id == null) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Received null video id while fetching videos for player " + player.getName());
                return null;
            }

            videos.add(new YouTubeVideo(id, title));
        }

        return videos;
    }

    private static void handleVideos(Map<VaroPlayer, List<YouTubeVideo>> playerVideos, boolean silent) {
        try {
            for (Map.Entry<VaroPlayer, List<YouTubeVideo>> entry : playerVideos.entrySet()) {
                if (entry.getValue() == null) { // null means an error occurred
                    new Alert(AlertType.NO_YOUTUBE_UPLOAD, "Die Videos von " + entry.getKey().getName() + " konnten nicht geladen werden!");
                    continue;
                }

                if (entry.getValue().isEmpty() && !silent) {
                    alert(entry.getKey());
                    continue;
                }

                for (YouTubeVideo video : entry.getValue()) {
                    if (entry.getKey().getStats().hasVideo(video.getVideoId()))
                        continue;

                    Main.getInstance().getLogger().info(String.format("Found video(title: \"%s\", id: \"%s\", link: \"%s\") for player %s",
                            video.getTitle(), video.getVideoId(), video.getLink(), entry.getKey().getName()));

                    entry.getKey().getStats().addVideo(video, silent);
                }
            }
        } catch (Throwable t) {
            Main.getInstance().getLogger().log(Level.SEVERE, "An error occurred while  ");
        }
    }

    private static void alert(VaroPlayer player) {
        new Alert(AlertType.NO_YOUTUBE_UPLOAD, player.getName() + " hat kein Varo Video hochgeladen!");

        if (VaroConfig.YOUTUBE_STRIKE.getValue())
            player.getStats().strike("Missing youtube video", "CONSOLE");
    }
}