package de.varoplugin.varo.threads.daily.checks;

import org.bukkit.scheduler.BukkitRunnable;

import de.varoplugin.varo.Main;
import de.varoplugin.varo.alert.Alert;
import de.varoplugin.varo.alert.AlertType;
import de.varoplugin.varo.configuration.configurations.config.ConfigSetting;
import de.varoplugin.varo.player.VaroPlayer;
import de.varoplugin.varo.player.stats.stat.Strike;
import de.varoplugin.varo.player.stats.stat.YouTubeVideo;
import de.varoplugin.varo.threads.daily.Checker;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class YouTubeCheck extends Checker {

	@Override
	public void check() {
		if (ConfigSetting.YOUTUBE_ENABLED.getValueAsBoolean()) {
			new BukkitRunnable() {
				@Override
				public void run() {
					for (VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
						if (vp.getStats().getYoutubeLink() == null) {
							alert(vp);
							continue;
						}

						List<YouTubeVideo> videos = loadNewVideos(vp);
						if (videos == null) {
							new Alert(AlertType.NO_YOUTUBE_UPLOAD, "Die Videos von " + vp.getName() + " konnten nicht geladen werden!");
							continue;
						}

						if (videos.size() == 0) {
							alert(vp);
						} else
							for (YouTubeVideo video : videos)
								vp.getStats().addVideo(video);
					}
				}
			}.runTaskAsynchronously(Main.getInstance());
		}

	}
	
	private void alert(VaroPlayer player) {
		new Alert(AlertType.NO_YOUTUBE_UPLOAD, player.getName() + " hat kein Varo Video hochgeladen!");
		
		if (ConfigSetting.YOUTUBE_STRIKE.getValueAsBoolean())
			player.getStats().strike("Missing youtube video", "CONSOLE");
	}
	
	public static List<YouTubeVideo> loadNewVideos(VaroPlayer player) {
		ArrayList<String> lines = new ArrayList<>();
		ArrayList<YouTubeVideo> videos = new ArrayList<>();
		URLConnection connection;
		try {
			connection = new URL(player.getStats().getYoutubeLink() + "/videos").openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:96.0) Gecko/20100101 Firefox/96.0");
			try(Scanner scanner = new Scanner(connection.getInputStream())) {

				while (scanner.hasNextLine())
					lines.add(scanner.nextLine());
			}
		} catch (Throwable t) {
			Main.getInstance().getLogger().severe(Main.getConsolePrefix() + "Could not load videos for " + player.getName());
			new Alert(AlertType.NO_YOUTUBE_UPLOAD, "Could not load videos for " + player.getName());
			return null;
		}

		for (String line : lines) {
			if (line.startsWith("</script><script src="))
				try {
					String[] videoSplit = line.split("\"title\"\\:\\{\"runs\"\\:\\[\\{\"text\"\\:\"");
					for (int i = 1; i < videoSplit.length; i++) {
						String[] titleSplit = videoSplit[i].split("\"}]", 2);
						String videoTitle = titleSplit[0];
						if (!videoTitle.toLowerCase().contains(ConfigSetting.YOUTUBE_VIDEO_IDENTIFIER.getValueAsString().toLowerCase()))
							continue;

						if (videoTitle.length() > 200)
							videoTitle = videoTitle.substring(0, 200);

						String videoId = titleSplit[1].split("\\{\"url\":\"/watch\\?v=", 2)[1].split("\"")[0];
						String videoLink = "https://youtube.com/watch?v=" + videoId;

						if (player.getStats().hasVideo(videoId))
							continue;
						
						Main.getInstance().getLogger().info(String.format("Found video(title: \"%s\", id: \"%s\", link: \"%s\") for player %s", videoTitle, videoId, videoLink, player.getName()));

						videos.add(new YouTubeVideo(videoId, videoTitle, videoLink));
					}
				} catch (Throwable t) {
					t.printStackTrace();
				}
		}

		return videos;
	}
}