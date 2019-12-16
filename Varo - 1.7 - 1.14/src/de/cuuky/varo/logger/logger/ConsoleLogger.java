package de.cuuky.varo.logger.logger;

import java.io.PrintStream;

import de.cuuky.varo.logger.Logger;
import de.cuuky.varo.utils.JavaUtils;

public class ConsoleLogger extends Logger {

	public ConsoleLogger() {
		super("consolelogs", false);

		startListening();
	}

	private void startListening() {
		System.setOut(new PrintStream(System.out) {

			@Override
			public void println(String x) {
				super.println(x);

				ConsoleLogger.this.println(x);
			}

			@Override
			public void println(Object x) {
				super.println(x);

				if (x == null)
					ConsoleLogger.this.println("null");
				else
					ConsoleLogger.this.println(x.toString());
			}
		});

		System.setErr(new PrintStream(System.err) {

			@Override
			public void println(String x) {
				super.println(x);

				ConsoleLogger.this.println(x);
			}

			@Override
			public void println(Object x) {
				super.println(x);

				ConsoleLogger.this.println(x.toString());
			}
		});
	}

	public void println(String line) {
		line = "[" + getCurrentDate() + "] " + JavaUtils.replaceAllColors(line);

		pw.println(line);
		logs.add(line);

		pw.flush();
	}
}