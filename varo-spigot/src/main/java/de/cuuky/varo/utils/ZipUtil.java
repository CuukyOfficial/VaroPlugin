/* 
 * VaroPlugin
 * Copyright (C) 2022 Cuuky, Almighty-Satan
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package de.cuuky.varo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static boolean zip(File zipFile, File source) {
        return zip(zipFile, source, __ -> true);
    }

    public static boolean zip(File zipFile, File source, Predicate<String> includeCallback) {
        try {
            if (!zipFile.exists()) {
                if (!zipFile.getParentFile().exists())
                    zipFile.getParentFile().mkdirs();
                zipFile.createNewFile();
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
                    ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream, StandardCharsets.UTF_8)) {
                zip(zipOutputStream, null, source, includeCallback);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void zip(ZipOutputStream zipOutputStream, String path, File file, Predicate<String> includeCallback) throws IOException {
        if (file.isDirectory())
            for (File child : file.listFiles())
                zip(zipOutputStream, path == null ? "" : path + file.getName() + "/", child, includeCallback);
        else if (file.isFile()) {
            String filePath = path == null ? file.getName() : path + file.getName();
            if (!includeCallback.test(filePath))
                return;

            ZipEntry zipEntry = new ZipEntry(filePath);
            zipOutputStream.putNextEntry(zipEntry);
            Files.copy(file.toPath(), zipOutputStream);
            zipOutputStream.closeEntry();
        }
    }

    public static boolean unzip(File zipFile, File destination) {
        return unzip(zipFile, destination, __ -> true);
    }

    public static boolean unzip(File zipFile, File destination, Predicate<String> includeCallback) {
        try {
            Path destinationPath = Paths.get(destination.getPath()).normalize();
            try (FileInputStream fileInputStream = new FileInputStream(zipFile);
                    ZipInputStream zipInputStream = new ZipInputStream(fileInputStream, StandardCharsets.UTF_8)) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    if (!includeCallback.test(entry.getName()))
                        continue;

                    File file = new File(destination, entry.getName());
                    if (!Paths.get(file.getPath()).normalize().startsWith(destinationPath))
                        return false;

                    if (entry.isDirectory()) {
                        if (!file.exists())
                            file.mkdirs();
                    } else {
                        File parent = file.getParentFile();
                        if (!parent.exists())
                            parent.mkdirs();
                        if (!file.exists())
                            file.createNewFile();
                        Files.copy(zipInputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
