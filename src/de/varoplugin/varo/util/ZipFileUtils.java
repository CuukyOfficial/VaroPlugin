package de.varoplugin.varo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author CuukyOfficial
 * @version v0.1
 */
// TODO: Move to CFW?
public final class ZipFileUtils {

    private ZipFileUtils() {
    }

    public static String readFileFromZip(File file, String path) throws IOException {
        ZipInputStream zip = new ZipInputStream(new FileInputStream(file));

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