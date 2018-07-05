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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author KroobaHariel
 */
public class SRTParser
{

    public static String extractTextFromSRT(File fTXT, File fSRT)
    {
        List<String> listLinesFromSRT;
        ArrayList<String> linesFiltered;
        String strMerged;

        linesFiltered = new ArrayList<>();
        try
        {
            listLinesFromSRT = Files.readAllLines(fSRT.toPath());
            for (String strLine : listLinesFromSRT)
            {
                //removes all lines with the subtitle timing/numberiing and empty lines
                if (!strLine.isEmpty() && !strLine.contains("-->") && !strLine.matches("[0-9]+"))
                {
                    linesFiltered.add(strLine);
                }
            }

            strMerged = mergeArrayListStrings(linesFiltered);

            //writes the txt file
            Files.write(fTXT.toPath(), strMerged.getBytes());
            return strMerged;
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, "Error to save txt file: " + fTXT.toString() + "\n" + ex.getMessage());
            return "";
        }
    }

    //merge all the strings on the arrayList adding space between
    private static String mergeArrayListStrings(ArrayList<String> listString)
    {
        String strMerged;

        strMerged = "";
        for (String s : listString)
        {
            strMerged += s + " ";
        }

        return strMerged;
    }
}
