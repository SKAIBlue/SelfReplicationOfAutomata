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
            gcmr = new GeneticCMR(8, 4, 10);
        }
        gcmr.setOnGenerateListener(this);
        gcmr.generatedCMR();
        setColorBinder(value -> {
            int color = 220 - (int)(220f * value / 10f);
            return new Color(color, color, color);
        });
        repaint();
        AutoNextStateThread autoNextStateThread = new AutoNextStateThread();
        autoNextStateThread.start();
    }




    @Override
    public void paint(Graphics g) {

        if(world == null)
        {
            return;
        }

        synchronized (sync)
        {

            //printWorld(world);

            Graphics2D g2d = (Graphics2D)g;
            int boxSize = Consts.WINDOWS_WIDTH / world.getWidth();
            int heightCount = Consts.WINDOWS_HEIGHT / boxSize + 1;
            for(int i = 0 ; i <= heightCount; i+=1)
            {
                for(int j = 0 ; j <= world.getWidth(); j+=1)
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




    private int[][] pattern =
            {
                    {0, 0, 0, 0, 0, 0},
                    {0, 2, 2, 2, 2, 0},
                    {0, 2, 1, 1, 2, 0},
                    {0, 2, 1, 1, 2, 0},
                    {0, 2, 2, 2, 2, 0},
                    {0, 0, 0, 0, 0, 0}
            };

    @Override
    public void onGenerate(CMR generatedCMR) {
        World world = new World(generatedCMR);
        world.put(pattern);
        worlds.add(world);
        this.world = world;
    }




    /**
     * 값에 따라 색상을 바인드 합니다.
     */
    public interface ColorBinder
    {

        Color bindColor(int value);

    }

    private void printWorld(World world)
    {
        int[][] p = world.toIntArray();
        StringBuilder build = new StringBuilder();
        for(int i = 0 ; i < p.length; i+=1)
        {
            int[] ip = p[i];
            for(int j = 0 ; j < ip.length;j+=1)
            {
                build.append(String.format("%2d", ip[j]));
            }
            build.append("\n");
        }
        System.out.println(build.toString());
    }

    float maxfitness = 0;

    private class AutoNextStateThread extends Thread
    {

        @Override
        public void run() {
            while(gcmr.getGeneration() < 2000000)
            {
                for(int iterate = 0 ; iterate < 100; iterate +=1)
                {
                    float best = 0;
                    int bestIndex = 0;
                    for(int i = 0 ; i < worlds.size() ; i+=1)
                    {
                        World world = worlds.get(i);
                        world.nextState();
                        float count = world.getPatternFitness(pattern);
                        world.cmr.fitness = count;
                        if(best < count)
                        {
                            best = count;
                            bestIndex = i;
                        }
                    }

                    if(maxfitness < world.cmr.fitness)
                    {
                        maxfitness = world.cmr.fitness / (world.getWidth() * world.getHeight());
                        world = worlds.get(bestIndex);
                        System.out.println("max fitness change: " + maxfitness);
                        gcmr.save("test");
                        repaint();
                    }
                    //System.out.println(world.cmr.fitness);
                    //if(world.cmr.fitness > 1)
                    //{
                        //repaint();
                    //}
                }
                if(gcmr.getGeneration() % 1000 == 0)
                {
                    gcmr.save("test");
                }
                System.out.println("Generation: " + gcmr.getGeneration() );
                gcmr.newGeneration();
                worlds.clear();
                gcmr.generatedCMR();
            }
        }

    }

}
