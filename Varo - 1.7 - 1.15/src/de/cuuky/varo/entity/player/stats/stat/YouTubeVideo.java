package de.cuuky.varo.entity.player.stats.stat;

import java.util.ArrayList;
import java.util.Date;

import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.serialize.identifier.VaroSerializeField;
import de.cuuky.varo.serialize.identifier.VaroSerializeable;

public class YouTubeVideo implements VaroSerializeable, Comparable<YouTubeVideo> {

	private static ArrayList<YouTubeVideo> videos;

	static {
		videos = new ArrayList<YouTubeVideo>();
	}

	@VaroSerializeField(path = "detectedAt")
	private Date detectedAt;
	@VaroSerializeField(path = "duration")
	private String duration;
	@VaroSerializeField(path = "link")
	private String link;
	@VaroSerializeField(path = "title")
	private String title;
	@VaroSerializeField(path = "videoId")
	private String videoId;

	public YouTubeVideo() {
		videos.add(this);
	}

	public YouTubeVideo(String videoId, String title, String link, String duration) {
		this.videoId = videoId;
		this.title = title;
		this.link = link;
		this.duration = duration;
		this.detectedAt = new Date();

		videos.add(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	public int compareTo(YouTubeVideo comparison) {
		return this.getDetectedAt().getDate() - comparison.getDetectedAt().getDate();
	}

	public Date getDetectedAt() {
		return detectedAt;
	}

	public String getDuration() {
		return duration;
	}

	public String getLink() {
		return link;
	}

	public VaroPlayer getOwner() {
		for(VaroPlayer vp : VaroPlayer.getVaroPlayer())
			if(vp.getStats().getVideos().contains(this))
				return vp;

		return null;
	}

	public String getTitle() {
		return title;
	}

	public String getVideoId() {
		return videoId;
	}

	@Override
	public void onDeserializeEnd() {}

	@Override
	public void onSerializeStart() {}

	public void remove() {
		VaroPlayer owner = getOwner();
		if(owner != null)
			owner.getStats().removeVideo(this);

		videos.remove(this);
	}

	public static YouTubeVideo getVideo(String videoId) {
		for(YouTubeVideo video : videos)
			if(video.getVideoId().equals(videoId))
				return video;

		return null;
	}

	public static ArrayList<YouTubeVideo> getVideos() {
		return videos;
	}
}
