package de.cuuky.varo.threads.daily.dailycheck.checker;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.YouTubeVideo;
import de.cuuky.varo.threads.daily.dailycheck.Checker;

public class YouTubeCheck extends Checker {

	private ArrayList<YouTubeVideo> loadVideos(String url, VaroPlayer player) {
		ArrayList<String> lines = new ArrayList<String>();
		ArrayList<YouTubeVideo> videos = new ArrayList<YouTubeVideo>();
		URLConnection connection = null;
		try {
			connection = new URL(url + "/videos").openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)	Chrome/70.0.3538.67 Safari/537.36");
			Scanner scanner = new Scanner(connection.getInputStream());

			while(scanner.hasNextLine())
				lines.add(scanner.nextLine());

			scanner.close();
		} catch(Exception ex) {
			System.out.println(Main.getConsolePrefix() + "Could not load videos for " + player.getName());
			new Alert(AlertType.NO_YOUTUBE_UPLOAD, "Could not load videos for " + player.getName());
			return null;
		}

		String videoId = null, videoTitle = null, link = null, duration = null;
		for(String line : lines) {
			if(line.contains("yt-lockup-title")) {
				try {
					videoTitle = line.split("title=\"")[1].split("\"")[0];
					if(!videoTitle.toLowerCase().contains(ConfigEntry.YOUTUBE_VIDEO_IDENTIFIER.getValueAsString().toLowerCase()))
						continue;

					videoId = line.split("href=\"")[1].split("\"")[0];
					link = "https://youtube.com" + videoId;
					videoId = videoId.replace("/watch?v=", "");
					duration = line.split("> - ")[1].split("</span>")[0];

					if(player.getStats().hasVideo(videoId))
						continue;

					videos.add(new YouTubeVideo(videoId, videoTitle, link, duration));
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("AT LINE " + line);
				}
				continue;
			}
		}

		return videos;
	}

	@Override
	public void check() {
		for(VaroPlayer vp : VaroPlayer.getAlivePlayer()) {
			if(vp.getStats().getYoutubeLink() == null)
				continue;

			ArrayList<YouTubeVideo> videos = loadVideos(vp.getStats().getYoutubeLink(), vp);
			if(videos == null)
				new Alert(AlertType.NO_YOUTUBE_UPLOAD, "Die Videos von " + vp.getName() + " konnten nicht geladen werden!");

			if(videos.size() == 0)
				new Alert(AlertType.NO_YOUTUBE_UPLOAD, vp.getName() + " hat kein Varo Video hochgeladen!");

			for(YouTubeVideo video : videos)
				vp.getStats().addVideo(video);
		}
	}
}