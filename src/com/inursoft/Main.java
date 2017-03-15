package com.inursoft;

import com.inursoft.Automata.WorldPanel;

import javax.swing.*;
import java.awt.*;

import static com.inursoft.Data.Consts.*;

public class Main {






    public static void main(String[] args) {

        WorldPanel world = new WorldPanel(20, 20);

        JFrame frame = new JFrame();
        frame.setResizable(false);
        frame.setTitle(FORM_TITLE);
        frame.setPreferredSize(new Dimension(WINDOWS_WIDTH, WINDOWS_HEIGHT + WINDOWS_BAR_HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(world);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
