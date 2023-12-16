package core;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class SpawnDumpFileUpdater {
	private static final String SPAWNS_URL = "https://pokemonrevolution.net/spawns/";
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36";

	private SpawnDumpFileUpdater() {

	}

	private static boolean isFileChangedInLastHour(Path filePath) {
		try {
			FileTime lastModifiedTime = Files.getLastModifiedTime(filePath);
			Instant lastModifiedInstant = lastModifiedTime.toInstant();
			Instant currentTimeUTC = Instant.now().truncatedTo(ChronoUnit.HOURS);

			return lastModifiedInstant.isAfter(currentTimeUTC);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static String downloadSpawn(String fileUrl) {
		StringBuffer buffer = new StringBuffer();
		try {
			URL url = new URL(fileUrl);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("User-Agent", USER_AGENT);

			try (InputStream in = connection.getInputStream()) {
				String fileName = Paths.get(url.getPath()).getFileName().toString();
				Path destinationFolderPath = Paths.get("resources");
				Files.createDirectories(destinationFolderPath);
				Path destinationFilePath = destinationFolderPath.resolve(fileName);
				Files.copy(in, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

				buffer.append("File downloaded successfully to: " + destinationFilePath + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			buffer.append(e.getMessage());
		}
		return buffer.toString();
	}

	public static String downloadSpawns() {
		Path[] spawndumps = new Path[] { Paths.get("resources/land_spawns.csv"),
				Paths.get("resources/surf_spawns.csv") };
		StringBuffer buffer = new StringBuffer("Updating spawns:\n");
		for (Path dump : spawndumps) {
			if (isFileChangedInLastHour(dump)) {
				buffer.append(String.format("%s is already up to date!\n", dump.getFileName()));
				continue;
			}
			buffer.append(downloadSpawn(SPAWNS_URL + dump.getFileName()));

		}
		return buffer.toString();
	}
}
