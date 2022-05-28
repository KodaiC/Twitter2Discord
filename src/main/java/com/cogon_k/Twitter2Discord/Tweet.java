package com.cogon_k.Twitter2Discord;

public class Tweet {

	private final long id;
	private final String name;
	private final String screenName;
	private final String avatarUrl;

	public Tweet(long id, String name, String screenName, String avatarUrl) {
		this.id = id;
		this.name = name;
		this.screenName = screenName;
		this.avatarUrl = avatarUrl;
	}

	public long getId() {
		return id;
	}

	public String getUrl() {
		return "https://twitter.com/" + screenName + "/status/" + id;
	}

	public String getName() {
		return name;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public String getJson() {
		return String.format("{\"content\":\"%s\", \"username\":\"%s\", \"avatar_url\":\"%s\"}", getUrl(), name + "(Twitter)", avatarUrl);
	}
}
