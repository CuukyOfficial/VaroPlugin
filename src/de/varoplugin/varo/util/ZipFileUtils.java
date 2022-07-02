package de.varoplugin.varo.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

// TODO: Move to CFW?
public final class ZipFileUtils {

    private ZipFileUtils() {
    }

    public static String readFileFromZip(File file, String path) throws IOException {
        ZipInputStream zip = new ZipInputStream(Files.newInputStream(file.toPath()));

        ZipEntry e;
        while ((e = zip.getNextEntry()) != null) {
            if (!e.getName().equals(path)) continue;

            StringBuilder builder = new StringBuilder();
            byte[] byteBuff = new byte[1024];
            int bytesRead;
            while ((bytesRead = zip.read(byteBuff, 0, byteBuff.length)) >= 0) {
                builder.append(new String(byteBuff, 0, bytesRead));
            }
            return builder.toString();
        }

        zip.close();
        return null;
    }
}