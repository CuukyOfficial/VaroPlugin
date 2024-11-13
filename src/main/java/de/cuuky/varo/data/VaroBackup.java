package de.cuuky.varo.data;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import de.cuuky.varo.utils.ZipUtil;

public class VaroBackup {
    
    static final String BACKUP_DIRECTORY = "plugins/Varo/backups";
    
    private static final DateFormat DATE_FROMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    private static final String[] INCLUDED_PATHS = new String[] {
            "config/",
            "languages/",
            "logs/",
            "stats/",
            "presets/"
    };

    private final File file;
    private final String displayName;
    private final long size;
    
	VaroBackup(File file) {
		this.file = file;
        this.displayName = file.getName().substring(0, file.getName().length() - 4);
		this.size = file.length();
	}
	
	void restore() {
	    ZipUtil.unzip(this.file, new File("plugins/Varo").getAbsoluteFile(), VaroBackup::includeCallback);
	}
	
	void delete() {
        this.file.delete();
    }
	
	public String getName() {
	    return this.file.getName();
	}
	
	public String getDisplayName() {
        return this.displayName;
    }
	
	public long getSize() {
        return this.size;
    }

	public static VaroBackup createBackup() {
        File zipFile = new File(BACKUP_DIRECTORY + "/backup-" + DATE_FROMAT.format(new Date()) + ".zip");
        boolean success = ZipUtil.zip(zipFile, new File("plugins/Varo").getAbsoluteFile(), VaroBackup::includeCallback);

        return success ? new VaroBackup(zipFile) : null;
    }
	
	private static boolean includeCallback(String path) {
	    return Arrays.stream(INCLUDED_PATHS).anyMatch(path::startsWith);
	}
}