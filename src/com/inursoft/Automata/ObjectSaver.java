package com.inursoft.Automata;

import java.io.*;

/**
 * Created by jwmtp on 2017-03-23.
 */
public class ObjectSaver {

    public static void save(Object obj,String name)
    {
        try {
            FileOutputStream fos = new FileOutputStream(name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static Object load(String name)
    {
        try {
            FileInputStream fis = new FileInputStream(name);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object res = ois.readObject();
            ois.close();
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



}
