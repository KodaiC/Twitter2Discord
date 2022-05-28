package com.cogon_k.Twitter2Discord;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Search extends TimerTask{

	private String text;
	static long before = 0;
	static String consumerKey = "";
	static String consumerSecret = "";
	static String accessToken = "";
	static String accessTokenSecret = "";

	public Search(String text) {
		this.text = text;
	}

	public void run() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(consumerKey)
		.setOAuthConsumerSecret(consumerSecret)
		.setOAuthAccessToken(accessToken)
		.setOAuthAccessTokenSecret(accessTokenSecret);
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();

		try {
			Query query = new Query(text);
			QueryResult result;
			result = twitter.search(query);
			List<Status> tweets = result.getTweets();

			int count = 0;
			for (Status tweet : tweets) {
				if (tweet.getCreatedAt().getTime() <= before) break;
				if (tweet.isRetweet()) continue;
				Tweet item = new Tweet(tweet.getId(), tweet.getUser().getName(), tweet.getUser().getScreenName(), tweet.getUser().get400x400ProfileImageURLHttps());
				Send.sendWebhook(item);

				//System.out.println(tweet.getUser().get400x400ProfileImageURLHttps());
				System.out.println(tweet.getId());
				System.out.println("@" + tweet.getUser().getScreenName() + ": " + tweet.isRetweet());
				System.out.println(tweet.getText());
				System.out.println();

				if (++count % 5 == 0) {
					try {
						Thread.sleep(5 * 1000);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			if (!tweets.isEmpty()) {
				before = tweets.get(0).getCreatedAt().getTime();
				System.out.println("before:" + before);
				Twitter2Discord.prop.setProperty("before", before + "");
				try(FileOutputStream writer = new FileOutputStream("settings.properties")) {
					Twitter2Discord.prop.store(writer, null);
					writer.flush();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}
	}
}
