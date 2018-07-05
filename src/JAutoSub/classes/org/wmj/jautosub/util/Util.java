/*
   (C) 2018 Raryel C. Souza
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.wmj.jautosub.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Util
{

    public static Path PATH_APP_TMP;

    public static boolean netIsAvailable()
    {
        try
        {
            final URL url = new URL("https://speech.googleapis.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            return false;
        }
    }

    public static void initTMP() throws IOException
    {
        PATH_APP_TMP = Files.createTempDirectory("JAudio2Video");
    }

    public static String getFileNameWithoutExtension(File f)
    {
        String fileNameWithoutExtension = "";
        int i = f.getName().lastIndexOf('.');

        if (i >= 0)
        {
            fileNameWithoutExtension = f.getName().substring(0, i);
        }

        return fileNameWithoutExtension;
    }

    public static void clearTMP()
    {
        for (File f : PATH_APP_TMP.toFile().listFiles())
        {
            f.delete();
        }
        PATH_APP_TMP.toFile().delete();
    }

    /*
    if the file should be extracted 
        returns the filename (excluding the path)
    if the file should not be extracted 
        returns empty string
     */
    private static String isFile2Extract(String entryName, String[] arrayNameFiles2Extract)
    {
        for (String filename : arrayNameFiles2Extract)
        {
            if (entryName.endsWith(filename))
            {
                return filename;
            }
        }
        return "";
    }

    /**
     * Gets running jar file path.
     *
     * @return running jar file path.
     */
    private static File getCurrentJarFilePath()
    {
        return new File(Util.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    }

    /**
     * Extracts all exe files to the destination directory.
     *
     * @param PATH_APP_TMP destination directory.
     * @throws IOException if there's an i/o problem.
     */
    public static void extractFileFromJAR2TMP(String[] arrayNameFile2Extract) throws IOException
    {
        String nameFile2Extract;

        java.util.jar.JarFile jar = new java.util.jar.JarFile(getCurrentJarFilePath());
        java.util.Enumeration enumEntries = jar.entries();
        String entryName;
        while (enumEntries.hasMoreElements())
        {
            java.util.jar.JarEntry file = (java.util.jar.JarEntry) enumEntries.nextElement();
            entryName = file.getName();

            //if the file should be extracted returns the actual filename
            //if not returns empty string
            nameFile2Extract = isFile2Extract(entryName, arrayNameFile2Extract);
            if ((entryName != null)
                    && nameFile2Extract.compareTo("") != 0) //if the file is on the list of files 2 be extracted
            {
                java.io.File f = PATH_APP_TMP.resolve(nameFile2Extract).toFile();
                java.io.InputStream is = jar.getInputStream(file); // get the input stream
                Files.copy(is, f.toPath(), StandardCopyOption.REPLACE_EXISTING);
                is.close();
            }
        }
    }
}
