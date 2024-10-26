package de.cuuky.varo.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class VaroLogger<T> {

	private static final File LOGS_FOLDER = new File("plugins/Varo/logs/");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	private final File file;
	private PrintWriter pw;
	private final Queue<T> queue;

	protected VaroLogger(String name) {
		this.file = new File(LOGS_FOLDER, name + ".varolog2");
		try {
			if (!this.file.exists()) {
				new File(this.file.getParent()).mkdirs();
				this.file.createNewFile();
			}

			this.pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.file, true), Charsets.UTF_8));
		}catch(IOException e) {
			e.printStackTrace();
		}
		this.queue = new ConcurrentLinkedQueue<>();
	}

	protected void load() throws IOException {}

	protected void queueLog(T log) {
		if(log != null)
			this.queue.add(log);
	}

	public void processQueue() {
		if(!this.queue.isEmpty()) {
			T log;
			while((log = this.queue.poll()) != null)
				this.print(log);

			this.pw.flush();
		}
	}

	void print(Object object) {
		this.pw.println(GSON.toJson(object));
	}

	public void cleanUp() {
		this.pw.close();
	}

	public File getFile() {
		return file;
	}
	
	protected String getCurrentDate() {
		return DATE_FORMAT.format(new Date());
	}

	protected Gson getGson() {
		return GSON;
	}
}