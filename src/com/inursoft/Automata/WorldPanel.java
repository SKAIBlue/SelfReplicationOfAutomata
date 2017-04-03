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
    public WorldPanel(int input)
    {
        switch (input)
        {
            case 1:
                generation();
                break;
            case 2:
                showBest();
                break;
            case 3:
                showSuccess();
        }

    }

    private void generation()
    {
        gcmr = GeneticCMR.load("test");
        initGeneticCMR();
        setColorBinder(value -> {
            int color = 220 - (int)(220f * value / 10f);
            return new Color(color, color, color);
        });
        repaint();
        GenerateThread generateThread = new GenerateThread();
        generateThread.start();
    }

    private void initGeneticCMR()
    {
        if(gcmr == null)
        {
            gcmr = new GeneticCMR(8, 4, 10);
        }
        gcmr.setOnGenerateListener(this);
        gcmr.generatedCMR();

    }

    private void showBest()
    {
        CMR cmr = (CMR)ObjectSaver.load("best.cmr");
        runAutomata(cmr);
    }

    private void showSuccess()
    {
        CMR cmr = new CMR("success.txt");
        runAutomata(cmr);
    }

    private void runAutomata(CMR cmr) {
        if(cmr != null)
        {
            world = new World(cmr);
            world.put(pattern);
            setColorBinder(value -> {
                int color = 220 - (int)(220f * value / 10f);
                return new Color(color, color, color);
            });
            repaint();
            AutoNextStateThread thread = new AutoNextStateThread();
            thread.start();
        }
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

/*
    private int[][] pattern =
            {
                    {0, 1, 1, 3, 0},
                    {3, 2, 2, 2, 1},
                    {1, 2, 1, 2, 1},
                    {1, 2, 2, 2, 3},
                    {0, 3, 1, 1, 0},
            };
            */
/*
    private int[][] pattern =
            {
                    {4, 1, 1, 3},
                    {1, 0, 0, 1},
                    {1, 0, 0, 1},
                    {5, 1, 1, 2},
            };

*/

    private int[][] pattern =
            {
                    {0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 1, 1, 3, 0, 0},
                    {0, 3, 2, 2, 2, 1, 0},
                    {0, 1, 2, 1, 2, 1, 0},
                    {0, 1, 2, 2, 2, 3, 0},
                    {0, 0, 3, 1, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0}
            };

/*
    private int[][] pattern =
            {
                    {0, 0, 0, 0, 0, 0},
                    {0, 4, 1, 1, 3, 0},
                    {0, 1, 0, 0, 1, 0},
                    {0, 1, 0, 0, 1, 0},
                    {0, 5, 1, 1, 2, 0},
                    {0, 0, 0, 0, 0, 0},
            };
*/
/*
    private int[][] pattern =
            {
                    {0, 0, 0, 0, 0, 0, 0, 0 ,0},
                    {0, 0, 1, 1, 1, 1, 1, 0 ,0},
                    {0, 1, 5, 2, 2, 2, 4, 1 ,0},
                    {0, 1, 2, 1, 1, 1, 2, 1 ,0},
                    {0, 1, 2, 1, 1, 1, 2, 1 ,0},
                    {0, 1, 2, 1, 1, 1, 2, 1 ,0},
                    {0, 1, 6, 2, 2, 2, 3, 1 ,0},
                    {0, 0, 1, 1, 1, 1, 1, 0 ,0},
                    {0, 0, 0, 0, 0, 0, 0, 0 ,0},
            };
*/
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
            long stateCount = 0;
            while(true)
            {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //printWorld(world);
                world.nextState();
                System.out.println("Count: " + stateCount);
                //printWorld(world);
                repaint();
                stateCount+=1;
            }
        }
    }


    private class GenerateThread extends Thread
    {

        @Override
        public void run() {
            while(true)
            {
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
                            maxfitness = world.cmr.fitness;
                            world = worlds.get(bestIndex);
                            System.out.println("max fitness change: " + maxfitness);
                            ObjectSaver.save(world.cmr, "best.cmr");
                            System.out.println("wj");
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
                        System.out.println("Generation: " + gcmr.getGeneration() );
                        gcmr.save("test");
                    }

                    gcmr.newGeneration();
                    worlds.clear();
                    gcmr.generatedCMR();
                }
                gcmr = null;
                initGeneticCMR();
            }

        }

    }

}
