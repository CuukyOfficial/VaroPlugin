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