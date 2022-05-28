package com.cogon_k.Twitter2Discord;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Send {

	public static String url;

	static void sendWebhook(Tweet tweet){

		try{
			URL sendUrl = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection)sendUrl.openConnection();

			con.addRequestProperty("Content-Type", "application/JSON; charset=utf-8");
			con.addRequestProperty("User-Agent", "DiscordBot");
			con.setDoOutput(true);
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Length", String.valueOf(tweet.getJson().length()));
			OutputStream stream = con.getOutputStream();
			stream.write(tweet.getJson().getBytes("UTF-8"));
			stream.flush();
			stream.close();

			final int status = con.getResponseCode();
			if (status != HttpURLConnection.HTTP_OK && status != HttpURLConnection.HTTP_NO_CONTENT)
				System.out.println("error:" + status );

			con.disconnect();

		} catch(MalformedURLException e  ){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
}
