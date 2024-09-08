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
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static boolean zip(File zipFile, File folder, Predicate<String> includeCallback) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream, StandardCharsets.UTF_8)) {
            if (!zipFile.exists()) {
                if (!zipFile.getParentFile().exists())
                    zipFile.getParentFile().mkdirs();
                zipFile.createNewFile();
            }

            zip(zipOutputStream, null, folder, includeCallback);

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void zip(ZipOutputStream zipOutputStream, String path, File file, Predicate<String> includeCallback) throws IOException {
        String newPath = path == null ? file.getName() : path + "/" + file.getName();
        if (!includeCallback.test(newPath))
            return;

        if (file.isDirectory())
            for (File child : file.listFiles())
                zip(zipOutputStream, newPath, child, includeCallback);
        else if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(newPath);
            zipOutputStream.putNextEntry(zipEntry);
            Files.copy(file.toPath(), zipOutputStream);
            zipOutputStream.closeEntry();
        }
    }

}
