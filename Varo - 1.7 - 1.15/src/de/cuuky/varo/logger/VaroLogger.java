package de.cuuky.varo.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class VaroLogger<T> {

	private static final File LOGS_FOLDER = new File("plugins/Varo/logs/");
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private final static HashFunction SHA256 = Hashing.sha256();
	static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	private final File file;
	private PrintWriter pw;
	private final Queue<T> queue;
	private byte[] remote;
	private byte[] prevSignature = new byte[0];

	protected VaroLogger(String name) {
		this.file = new File(LOGS_FOLDER, name + ".varolog");
		try {
			if (!this.file.exists()) {
				new File(this.file.getParent()).mkdirs();
				this.file.createNewFile();
			}

			this.pw = new PrintWriter(new FileWriter(this.file, true));
		}catch(IOException e) {
			e.printStackTrace();
		}
		this.queue = new ConcurrentLinkedQueue<>();
	}

	protected void load() throws IOException {}

	protected void queLog(T log) {
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
		String value = GSON.toJson(object);

		byte[] message = value.getBytes(StandardCharsets.UTF_8);
		byte[] data = new byte[this.remote.length + this.prevSignature.length + message.length];
		System.arraycopy(this.remote, 0, data, 0, this.remote.length);
		System.arraycopy(this.prevSignature, 0, data, this.remote.length, this.prevSignature.length);
		System.arraycopy(message, 0, data, this.remote.length + this.prevSignature.length, message.length);

		HashCode signature = SHA256.hashBytes(data);
		this.prevSignature = signature.asBytes();

		this.print(signature.toString(), value);
	}

	void print(String signature, String value) {
		this.pw.print(signature);
		this.pw.println(value);
	}

	public void cleanUp() {
		this.pw.close();
	}

	public File getFile() {
		return file;
	}

	public void setRemote(byte[] remote) {
		this.remote = remote;
	}

	protected String getCurrentDate() {
		return DATE_FORMAT.format(new Date());
	}

	protected Gson getGson() {
		return GSON;
	}
}