package de.cuuky.varo.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public abstract class CachedVaroLogger<T> extends VaroLogger<T> {

	private List<T> logs = Collections.synchronizedList(new ArrayList<>());
	private Class<T> type;

	protected CachedVaroLogger(String name, Class<T> type) {
		super(name);
		this.type = type;
	}

	@Override
	protected void load() throws IOException {
		Scanner scanner = new Scanner(this.getFile());
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(!line.isEmpty() && !line.substring(0, 64).equals(VaroLoggerManager.RESTART_SIGNATURE))
				this.logs.add(GSON.fromJson(line.substring(64), this.type));
		}
		scanner.close();
		super.load();
	}

	@Override
	protected void queLog(T log) {
		if(log != null) {
			this.logs.add(log);
			super.queLog(log);
		}
	}

	public List<T> getLogs() {
		return this.logs;
	}
}
