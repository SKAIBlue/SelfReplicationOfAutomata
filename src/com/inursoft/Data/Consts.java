package com.inursoft.Data;

/**
 * Created by Anonymous on 2017. 3. 6..
 */
public class Consts {

    public static Object locker = new Object();

    public static final int CMR_SIZE = 40;


    public static final int WINDOWS_WIDTH = 800;


    public static final int WINDOWS_HEIGHT = 800;


    public static final int WINDOWS_BAR_HEIGHT = 22;


    public static final String FORM_TITLE = "Automata";


    private static int iteration = 0;


    public static int getIteration()
    {
        synchronized (locker)
        {
            int i = iteration++;
            return i;
        }
    }

}
