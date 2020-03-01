package de.cuuky.varo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class JavaUtils {

	public static ArrayList<String> addIntoEvery(ArrayList<String> input, String into, boolean start) {
		for(int i = 0; i < input.size(); i++)
			input.set(i, (start ? into + input.get(i) : input.get(i) + into));

		return input;
	}

	public static String[] addIntoEvery(String[] input, String into, boolean start) {
		for(int i = 0; i < input.length; i++)
			input[i] = (start ? into + input[i] : input[i] + into);

		return input;
	}

	public static String[] arrayToCollection(List<String> strings) {
		String[] newStrings = new String[strings.size()];

		for(int i = 0; i < strings.size(); i++)
			newStrings[i] = strings.get(i);

		return newStrings;
	}

	public static ArrayList<String> collectionToArray(String[] strings) {
		ArrayList<String> newStrings = new ArrayList<>();
		for(String string : strings)
			newStrings.add(string);

		return newStrings;
	}

	public static String[] combineArrays(String[]... strings) {
		ArrayList<String> string = new ArrayList<>();

		for(String[] ss : strings)
			for(String strin : ss)
				string.add(strin);

		return getAsArray(string);
	}

	public static String getArgsToString(ArrayList<String> args, String insertBewteen) {
		String command = "";
		for(String arg : args)
			if(command.equals(""))
				command = arg;
			else
				command = command + insertBewteen + arg;

		return command;
	}

	public static String getArgsToString(String[] args, String insertBewteen) {
		String command = "";
		for(String arg : args)
			if(command.equals(""))
				command = arg;
			else
				command = command + insertBewteen + arg;

		return command;
	}

	public static String[] getAsArray(ArrayList<String> string) {
		String[] list = new String[string.size()];
		for(int i = 0; i < string.size(); i++)
			list[i] = string.get(i);

		return list;
	}

	public static ArrayList<Object> getAsList(String[] lis) {
		ArrayList<Object> list = new ArrayList<>();
		for(Object u : lis)
			list.add(u);

		return list;
	}

	public static int getNextToNine(int to) {
		int offset = 0;
		while(true) {
			int temp = to + offset;
			if(temp % 9 == 0)
				return temp;
			if(temp >= 54)
				return 54;

			if(temp <= 9)
				return 9;

			temp = to - offset;
			if(temp % 9 == 0)
				return temp;
			if(temp >= 54)
				return 54;

			if(temp <= 9)
				return 9;

			offset++;
		}
	}

	public static Object getStringObject(String obj) {
		try {
			return Integer.parseInt(obj);
		} catch(NumberFormatException e) {}

		try {
			return Long.parseLong(obj);
		} catch(NumberFormatException e) {}

		try {
			return Double.parseDouble(obj);
		} catch(NumberFormatException e) {}

		if(obj.equalsIgnoreCase("true") || obj.equalsIgnoreCase("false"))
			return obj.equalsIgnoreCase("true") ? true : false;
		else
			return obj;
	}

	/**
	 * @param min
	 *            The minimum Range
	 * @param max
	 *            The maximum Range
	 * @return Returns a random Integer between the min and the max range
	 */
	public static int randomInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static String[] removeString(String[] string, int loc) {
		String[] ret = new String[string.length - 1];
		int i = 0;
		boolean removed = false;
		for(String arg : string) {
			if(i == loc && !removed) {
				removed = true;
				continue;
			}

			ret[i] = arg;
			i++;
		}

		return ret;
	}

	public static String replaceAllColors(String s) {
		String newMessage = "";
		boolean lastPara = false;
		for(char c : s.toCharArray()) {
			if(lastPara) {
				lastPara = false;
				continue;
			}

			if(c == '§' || c == '&') {
				lastPara = true;
				continue;
			}

			newMessage = newMessage.isEmpty() ? String.valueOf(c) : newMessage + c;
		}

		return newMessage;
	}
}