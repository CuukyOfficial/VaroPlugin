package de.cuuky.varo.logger;

import de.cuuky.varo.Main;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class VaroLogger {

    private final File file;
    private FileWriter fw;
    private final List<String> logs;
    private final List<String> queue;
    private PrintWriter pw;
    private Scanner scanner;

    public VaroLogger(String name, boolean loadPrevious) {
        this.file = new File("plugins/Varo/logs/", name + ".yml");
        this.logs = new ArrayList<>();
        this.queue = new CopyOnWriteArrayList<>();
        this.loadFile(loadPrevious);

        this.startQueue();
    }

    private void startQueue() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            String[] copy = this.queue.toArray(new String[0]);
            queue.clear();

            for (String s : copy)
                pw.println(s);

            pw.flush();
        }, 20L, 20L);
    }

    private void loadFile(boolean loadPrevious) {
        try {
            if (!file.exists()) {
                new File(file.getParent()).mkdirs();
                file.createNewFile();
            }

            fw = new FileWriter(file, true);
            pw = new PrintWriter(fw);
            scanner = new Scanner(file);

            if (loadPrevious)
                while (scanner.hasNextLine())
                    logs.add(scanner.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    protected void queLog(String log) {
        logs.add(log);
        queue.add(log);
    }

    public File getFile() {
        return file;
    }

    public List<String> getLogs() {
        return logs;
    }
}