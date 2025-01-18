package de.cuuky.varo.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.google.common.base.Charsets;

public abstract class CachedVaroLogger<T> extends VaroLogger<T> {

	private final List<T> logs = new ArrayList<>();
	private final Class<T> type;

	protected CachedVaroLogger(String name, Class<T> type) {
		super(name);
		this.type = type;
	}
	
	protected void appendLog(T log) {
	    this.logs.add(log);
	}

	@Override
	protected void load() throws IOException {
		try (Scanner scanner = new Scanner(this.getFile(), Charsets.UTF_8.name())) {
    		while(scanner.hasNextLine()) {
    			String line = scanner.nextLine();
    			if(!line.isEmpty())
    			    this.appendLog(GSON.fromJson(line, this.type));
    		}
		}
		super.load();
	}

	@Override
	protected void queueLog(T log) {
		if(log == null)
		    return;

		this.appendLog(log);
		super.queueLog(log);
	}

	public List<T> getLogs() {
		return Collections.unmodifiableList(this.logs);
	}
}
