/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
