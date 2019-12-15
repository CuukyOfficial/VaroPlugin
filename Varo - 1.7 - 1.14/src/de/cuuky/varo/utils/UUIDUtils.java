package de.cuuky.varo.utils;

import java.net.URL;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public final class UUIDUtils {

	private UUIDUtils() {}

	public static UUID getUUID(String name) throws Exception {
		return getUUIDTime(name, -1);
	}
	
	public static String getNamesChanged(String name) throws Exception {
		Date Date = new Date();
		long Time = Date.getTime() / 1000;

		UUID UUIDOld = getUUIDTime(name, (Time-(60*60*24*30)));
		String uuid = UUIDOld.toString().replace("-", "");

		Scanner scanner = new Scanner(new URL("https://api.mojang.com/user/profiles/" + uuid + "/names").openStream());
		String input = scanner.nextLine();
		scanner.close();

		JSONArray nameArray = (JSONArray) JSONValue.parseWithException(input);
		String playerSlot = nameArray.get(nameArray.size()-1).toString();
		JSONObject nameObject = (JSONObject) JSONValue.parseWithException(playerSlot);
		String newName = nameObject.get("name").toString();
		return newName;
	}
	
	private static UUID getUUIDTime(String name, long time) throws Exception {
		Scanner scanner;
		if (time == -1) {
			scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream());
		} else {
			scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + name + "?at=" + String.valueOf(time)).openStream());
		}

		String input = scanner.nextLine();
		scanner.close();

		JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(input);
		String uuidString = UUIDObject.get("id").toString();
		String uuidSeperation = uuidString.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5");
		UUID uuid = UUID.fromString(uuidSeperation);
		return uuid;
	}
}