package com.inursoft.Data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Calendar;

/**
 * Created by Anonymous on 2017. 6. 4..
 */
public class Logger {


    public static String fileName = "logs";



    public static void println(String message)
    {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, true);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(String.format("%s\t\t%s", getNowTime(), message));
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    private static String getNowTime()
    {
        Calendar cal = Calendar.getInstance();
        return String.format("%d-%d-%d %02d:%02d:%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH),
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                cal.get(Calendar.SECOND)
        );
    }


}
