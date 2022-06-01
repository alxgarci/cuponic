package org.example.tools;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.example.mainMenu;

public class WriteToLog {

    private static final String LOG_CHARSET = "UTF-8";
    public static File f;

    public static void writeLogFile(String log) {
        f = mainMenu.createLocalFile("log","txt");
        try {

            FileOutputStream fos = new FileOutputStream(f, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos, LOG_CHARSET);
            BufferedWriter bw = new BufferedWriter(osw);
            PrintWriter pw = new PrintWriter(bw, true);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String tiempo = simpleDateFormat.format(date);
            String toWrite = "[" + tiempo + "] " + log;

            pw.write(toWrite);
            pw.write("\n");
            pw.close();

        } catch (IOException e) {
            System.out.println("[ERROR] Error actualizando el archivo " + f.getPath());
            e.printStackTrace();
        }
    }

    public static File getFile(){
        return f;
    }
}
