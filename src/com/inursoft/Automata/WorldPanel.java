package com.inursoft.Automata;

import javax.swing.*;
import java.awt.*;

import static com.inursoft.Data.Consts.WINDOWS_HEIGHT;
import static com.inursoft.Data.Consts.WINDOWS_WIDTH;


/**
 * Created by Anonymous on 2017. 3. 6..
 */
public class WorldPanel extends JPanel {


    private Object sync = new Object();


    private int boxWidth;



    private int boxHeight;



    private ColorBinder binder;


    private int[][] world;


    /**
     * 셀이 활동하는 월드 입니다.
     * @param width 월드의 최대 너비
     * @param height 월드의 최대 높이
     */
    public WorldPanel(int width, int height)
    {

        boxWidth = WINDOWS_WIDTH / width;
        boxHeight = WINDOWS_HEIGHT / height;

        repaint();
    }




    @Override
    public void paint(Graphics g) {

        if(world == null)
        {
            return;
        }

        synchronized (sync)
        {
            Graphics2D g2d = (Graphics2D)g;

            for(int i = 0 ; i < world.length; i+=1)
            {
                int[] row = world[i];
                for(int j = 0 ; j < row.length; j+=1)
                {
                    int value = row[j];
                    g2d.setColor(Color.WHITE);
                    g2d.drawRect(boxWidth * j, boxHeight * i, boxWidth * j + boxWidth, boxHeight * i + boxHeight);

                    if(binder != null)
                    {
                        g2d.setColor(binder.bindColor(value));
                    }
                    else
                    {
                        g2d.setColor(Color.LIGHT_GRAY);
                    }

                    g2d.fillRect(boxWidth * j, boxHeight * i, boxWidth * j + boxWidth, boxHeight * i + boxHeight);
                }
            }
        }
    }


    public void drawCells(Cells cells)
    {
        world = cells.getCells();
        repaint();
    }


    /**
     * 색상 바인더를 설정합니다.
     * @param binder
     */
    public void setColorBinder(ColorBinder binder)
    {
        this.binder = binder;
    }


    /**
     * 값에 따라 색상을 바인드 합니다.
     */
    public interface ColorBinder
    {

        Color bindColor(int value);

    }




}
