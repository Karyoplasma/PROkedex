package core;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DumpFileDownloader {

	private final static String landUrl = "https://pokemonrevolution.net/spawns/land_spawns.json";
	private final static String surfUrl = "https://pokemonrevolution.net/spawns/surf_spawns.json";

	private DumpFileDownloader() {

	}

	private static void getLandDump() throws MalformedURLException, IOException {
		URLConnection connection = new URL(landUrl).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();
		BufferedInputStream in  = new BufferedInputStream(connection.getInputStream());
		FileOutputStream fileOutputStream = new FileOutputStream("resources/land_spawns.json");
		byte dataBuffer[] = new byte[1024];
		int bytesRead;
		while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
			fileOutputStream.write(dataBuffer, 0, bytesRead);
		}
	}

	private static void getSurfDump() throws MalformedURLException, IOException {
		URLConnection connection = new URL(surfUrl).openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
		connection.connect();
		BufferedInputStream in  = new BufferedInputStream(connection.getInputStream());
		FileOutputStream fileOutputStream = new FileOutputStream("resources/surf_spawns.json");
		byte dataBuffer[] = new byte[1024];
		int bytesRead;
		while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
			fileOutputStream.write(dataBuffer, 0, bytesRead);
		}
	}

	private static void getDumps() throws MalformedURLException, IOException {
		getLandDump();
		getSurfDump();
	}


}
