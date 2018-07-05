/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wmj.jautosub.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.JTextArea;

/*
Auxiliary threads to read the process output
*/
public class StreamGobbler extends Thread
{

    InputStream is;
    String type;
    JTextArea jtaOutput;

    public StreamGobbler(InputStream is, String type, JTextArea pJtaOutput)
    {
        this.is = is;
        this.type = type;
        this.jtaOutput = pJtaOutput;
    }

    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
            {
                jtaOutput.append(line + "\n");
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
