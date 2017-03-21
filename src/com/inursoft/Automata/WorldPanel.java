package com.inursoft.Automata;

import com.inursoft.Data.Consts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Created by Anonymous on 2017. 3. 6..
 */
public class WorldPanel extends JPanel implements GeneticCMR.OnGenerateListener{


    private Object sync = new Object();



    private ColorBinder binder;



    private World world;



    private World best;



    private GeneticCMR gcmr = null;



    private ArrayList<World> worlds = new ArrayList<>();

    /**
     * 셀이 활동하는 월드 입니다.
     */
    public WorldPanel()
    {
        gcmr = GeneticCMR.load("test");
        if(gcmr == null)
        {
            gcmr = new GeneticCMR(1000, 30, 10);
        }
        gcmr.setOnGenerateListener(this);
        gcmr.generatedCMR();
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
            int boxSize = Consts.WINDOWS_WIDTH / world.getWidth();
            int heightCount = Consts.WINDOWS_HEIGHT / boxSize + 1;
            for(int i = 0 ; i < heightCount; i+=1)
            {
                for(int j = 0 ; j < world.getWidth(); j+=1)
                {
                    int value = world.get(j, i);
                    g2d.setColor(Color.WHITE);
                    g2d.drawRect(boxSize * j, boxSize* i, boxSize* j + boxSize, boxSize* i + boxSize);

                    if(binder != null)
                    {
                        g2d.setColor(binder.bindColor(value));
                    }
                    else
                    {
                        g2d.setColor(Color.LIGHT_GRAY);
                    }

                    g2d.fillRect(boxSize * j, boxSize* i, boxSize* j + boxSize, boxSize* i + boxSize);
                }
            }
        }
    }


    public void drawCells(World world)
    {
        this.world = world;
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


    int a = 0;

    @Override
    public void onGenerate(CMR generatedCMR) {
        worlds.add(new World(generatedCMR));
    }




    /**
     * 값에 따라 색상을 바인드 합니다.
     */
    public interface ColorBinder
    {

        Color bindColor(int value);

    }




}
