package de.varoplugin.varo.logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

// TODO this should use a database
public abstract class VaroLogger<T> {

	private static final File LOGS_FOLDER = new File("plugins/Varo/logs/");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	private final File file;
	private PrintWriter pw;
	private final Queue<T> queue = new LinkedList<>();
	private volatile boolean running = true;

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
			this.running = false;
		}
	}

	protected abstract void load() throws IOException;

	protected void queueLog(T log) {
		if(log == null)
		    return;

		synchronized (this.queue) {
		    this.queue.add(log);
        }
	}

	public void processQueue() {
		synchronized (this.queue) {
		    if(this.running && !this.queue.isEmpty()) {
	            T log;
	            while((log = this.queue.poll()) != null)
	                this.print(log);

	            this.pw.flush();
	        }
        }
	}

	private void print(Object object) {
		this.pw.println(GSON.toJson(object));
	}

	public void cleanUp() {
	    synchronized (this.queue) {
	        this.processQueue();
	        this.pw.close();
	        this.running = false;
        }
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