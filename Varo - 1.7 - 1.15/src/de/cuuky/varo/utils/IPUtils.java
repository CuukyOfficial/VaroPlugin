package de.cuuky.varo.utils;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public final class IPUtils {
	static HashMap<String, String> ipStorage = new HashMap<>();

	public static String ipToTimezone(String ip) {
		String timezone;
		if (ipStorage.containsKey(ip)) {
			timezone = ipStorage.get(ip);
		} else {
			String url = "https://api.ipgeolocation.io/timezone?apiKey=e31f8643ecc8488cbb120bdb6da3d4e8&ip=" + ip;
			JSONObject object;
			try {
				object = (JSONObject) JSONValue.parse(getUrlSource(url));
			} catch (IOException e) {
				return null;
			}
			timezone = (String) object.get("timezone");
			ipStorage.put(ip, timezone);
		}
		return timezone;
	}

	public static ZonedDateTime ipToTime(String ip) {
		ZonedDateTime cal = ZonedDateTime.now(ZoneId.of(ipToTimezone(ip)));
		return cal;
	}

	private static String getUrlSource(String url) throws IOException {
		Scanner scanner = new Scanner(new URL(url).openStream());
		String result = "";
		while (scanner.hasNext()) {
			result += scanner.nextLine();
		}
		return result;
	}
}