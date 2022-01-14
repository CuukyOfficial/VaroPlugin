package de.cuuky.varo.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.google.common.base.Charsets;

public abstract class CachedVaroLogger<T> extends VaroLogger<T> {

	private List<T> logs = Collections.synchronizedList(new ArrayList<>());
	private Class<T> type;

	protected CachedVaroLogger(String name, Class<T> type) {
		super(name);
		this.type = type;
	}

	@Override
	protected void load() throws IOException {
		Scanner scanner = new Scanner(this.getFile(), Charsets.UTF_8.name());
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(!line.isEmpty())
				this.logs.add(GSON.fromJson(line, this.type));
		}
		scanner.close();
		super.load();
	}

	@Override
	protected void queueLog(T log) {
		if(log != null) {
			this.logs.add(log);
			super.queueLog(log);
		}
	}

	public List<T> getLogs() {
		return this.logs;
	}
}
