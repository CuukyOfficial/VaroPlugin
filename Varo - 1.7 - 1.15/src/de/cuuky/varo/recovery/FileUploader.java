package de.cuuky.varo.recovery;

import java.io.File;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import de.cuuky.varo.recovery.utils.MultipartUtility;

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
			MultipartUtility multipart = new MultipartUtility("https://api.anonfile.com/upload?token=894b0ea821338221", "UTF-8");
			
			multipart.addHeaderField("User-Agent", "Varo Plugin by Cuuky");
            multipart.addHeaderField("Test-Header", "Header-Value");
			multipart.addFilePart("file", file);
			
			String response = multipart.finish().get(0);
			JSONObject object = (JSONObject) JSONValue.parseWithException(response);
			return (String) getJSONObject(getJSONObject(getJSONObject(object, "data"), "file"), "url").get("short");
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}