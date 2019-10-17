package de.cuuky.varo.utils;

import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class UUIDUtils {

	public static UUID getUUID(String name) throws Exception {
		Scanner scanner = new Scanner(new URL("https://api.mojang.com/users/profiles/minecraft/" + name).openStream());

		if(scanner.hasNext()) {
			String s1 = scanner.nextLine();
			String s = s1.replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("\"", "").split(",")[0].split(":")[1];
			scanner.close();
			return UUID.fromString(s.replaceFirst("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)", "$1-$2-$3-$4-$5"));
		}

		scanner.close();
		return null;
	}
}
