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
