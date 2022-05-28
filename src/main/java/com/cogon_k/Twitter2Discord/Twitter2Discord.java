package com.cogon_k.Twitter2Discord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Timer;

public class Twitter2Discord {
	public static Properties prop = new Properties();
	public static String text = "";

	public static void main(String[] args) {
		try(FileInputStream reader = new FileInputStream("settings.properties")) {
			prop.load(reader);
			text = prop.getProperty("text");
			Send.url = prop.getProperty("url");
			Search.before = Long.parseLong(prop.getProperty("before"));
			Search.consumerKey = prop.getProperty("consumerKey");
			Search.consumerSecret = prop.getProperty("consumerSecret");
			Search.accessToken = prop.getProperty("accessToken");
			Search.accessTokenSecret = prop.getProperty("accessTokenSecret");
		}
		catch (FileNotFoundException e) {
			try(FileOutputStream writer = new FileOutputStream("settings.properties")) {
				prop.setProperty("text", "");
				prop.setProperty("url", "");
				prop.setProperty("before", "0");
				prop.setProperty("consumerKey", "");
				prop.setProperty("consumerSecret", "");
				prop.setProperty("accessToken", "");
				prop.setProperty("accessTokenSecret", "");
				prop.store(writer, null);
				writer.flush();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new Search(text), 0, 60 * 1000);
	}
}
