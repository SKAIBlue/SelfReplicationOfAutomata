package com.inursoft;

import com.inursoft.Automata.WorldPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

import static com.inursoft.Data.Consts.*;

public class Main {






    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.print("1. 찾기\n2. 베스트 변화 보기\n3. 복제 성공사례 보기> ");
        int input = scan.nextInt();
        WorldPanel world = new WorldPanel(input);

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
