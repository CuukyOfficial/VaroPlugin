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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class YouTubeCheck implements Task {
    
    private static final String APPLICATION_NAME = "VaroPlugin";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    
    private static final long PLAYLIST_MAX_RESULTS = 10L;

	@Override
	public void check() {
		if (!VaroConfig.YOUTUBE_ENABLED.getValue())
            return;
        
        final String apiKey = VaroConfig.YOUTUBE_API_KEY.getValue();
        final String identifier = VaroConfig.YOUTUBE_IDENTIFIER.getValue();

        Bukkit.getServer().getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Main.getInstance().getLogger().log(Level.INFO, "Checking for new Youtube Videos...");
            try {
                YouTube youtube = getYoutubeService();
                
                for (VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
                    if (vp.getStats().getYoutubeHandle() == null) {
                        this.alert(vp);
                        continue;
                    }

                    List<YouTubeVideo> videos = findVideos(youtube, apiKey, identifier, vp);
                    if (videos == null) {
                        new Alert(AlertType.NO_YOUTUBE_UPLOAD, "Die Videos von " + vp.getName() + " konnten nicht geladen werden!");
                        continue;
                    }

                    if (videos.isEmpty()) {
                        this.alert(vp);
                    } else
                        for (YouTubeVideo video : videos)
                            vp.getStats().addVideo(video);
                }
            } catch (Throwable t) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Unable to load Youtube videos", t);
                return;
            }
            Main.getInstance().getLogger().log(Level.INFO, "Finished checking for new Youtube Videos");
        });
	}
    
    public static void loadVideos() {
        if (!VaroConfig.YOUTUBE_ENABLED.getValue())
            return;

        final String apiKey = VaroConfig.YOUTUBE_API_KEY.getValue();
        final String identifier = VaroConfig.YOUTUBE_IDENTIFIER.getValue();

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try {
                YouTube youtube = getYoutubeService();

                // Copy the list to avoid ConcurrentModificationException
                // This is only executed once anyway so performance doesn't really matter
                for (VaroPlayer player : VaroPlayer.getVaroPlayers().toArray(new VaroPlayer[0])) {
                    if (player.getStats().getYoutubeHandle() == null)
                        continue;

                    try {
                        List<YouTubeVideo> videos = findVideos(youtube, apiKey, identifier, player);

                        if (videos != null && !videos.isEmpty())
                            // Add videos that have already been uploaded without sending a log/Discord message
                            player.getStats().getVideos().addAll(videos);
                    } catch (Throwable t) {
                        Main.getInstance().getLogger().log(Level.SEVERE, "Unable to load Youtube videos for player " + player.getName(), t);
                    }
                }
            } catch (Throwable t) {
                Main.getInstance().getLogger().log(Level.SEVERE, "Unable to load Youtube videos", t);
            }
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
            Main.getInstance().getLogger().log(Level.SEVERE, "Received null or empty items while fetching channel details for player " + player.getName());
            return null;
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

            if (player.getStats().hasVideo(id))
                continue;

            Main.getInstance().getLogger().info(String.format("Found video(title: \"%s\", id: \"%s\", link: \"%s\") for player %s", title, id, YouTubeVideo.WATCH_LINK + id, player.getName()));

            videos.add(new YouTubeVideo(id, title));
        }
        
        return videos;
    }
	
	private void alert(VaroPlayer player) {
		new Alert(AlertType.NO_YOUTUBE_UPLOAD, player.getName() + " hat kein Varo Video hochgeladen!");
		
		if (VaroConfig.YOUTUBE_STRIKE.getValue())
			player.getStats().strike("Missing youtube video", "CONSOLE");
	}
}