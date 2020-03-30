package de.cuuky.varo.recovery;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

public class FileUploader {
	
	private File file;
	
	public FileUploader(File file) {
		this.file = file;
	}

	private JSONObject getJSONObject(JSONObject object, String path) {
		return (JSONObject) object.get(path);
	}
	
	public String uploadFile() {
		try {
			Process process = Runtime.getRuntime().exec("curl -F \"file=@" + file.getPath() +"\" https://api.anonfile.com/upload?token=894b0ea821338221");
			Scanner scanner = new Scanner(process.getInputStream());
			while(scanner.hasNextLine()) {
				JSONObject object = (JSONObject) JSONValue.parseWithException(scanner.nextLine());
				scanner.close();
				return (String) getJSONObject(getJSONObject(getJSONObject(object, "data"), "file"), "url").get("short");
			}
		} catch(IOException | ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}