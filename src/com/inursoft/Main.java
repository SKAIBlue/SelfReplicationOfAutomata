package com.inursoft;

import com.inursoft.Automata.Generator;

public class Main {

    public static void main(String[] args) {
        int[][] pattern =
                {
                        {0, 1, 1, 3, 0},
                        {3, 2, 2, 2, 1},
                        {1, 2, 1, 2, 1},
                        {1, 2, 2, 2, 3},
                        {0, 3, 1, 1, 0},
                };
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Core count = " + cores);
        for(int i = 0 ; i < cores ; i +=1)
        {
            Generator generator = new Generator(i, pattern);
            generator.start();
        }
        //Scanner scan = new Scanner(System.in);

        //System.out.print("1. 찾기\n2. 베스트 변화 보기\n3. 복제 성공사례 보기> ");
        //int input = scan.nextInt();
        /*
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
        */
    }
}
