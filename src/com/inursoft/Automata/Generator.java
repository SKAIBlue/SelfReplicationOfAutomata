package com.inursoft.Automata;

import com.inursoft.Data.Consts;
import com.inursoft.Data.Logger;

import java.util.ArrayList;
import java.util.List;

import static com.inursoft.Data.Consts.CMR_SIZE;

/**
 * Created by Anonymous on 2017. 6. 5..
 * 버그 가능성..?
 * 상태에서 패턴 찾기에서 버그 X
 * 전이에서 버그 X
 * 진화에서 버그 ?
 */
public class Generator extends Thread implements GeneticCMR.OnGenerateListener {




    private GeneticCMR gcmr = null;



    private List<World> worlds = new ArrayList<>();



    private int[][] pattern = null;



    private int coreId;



    public Generator(int coreId, int[][] pattern) {
        this.pattern = pattern;
        this.coreId = coreId;
        initGeneticCMR();
    }



    @Override
    public void run() {
        int iteration = Consts.getIteration();
        while (iteration < 10) {
            Logger.println(String.format("CPU ID: %d Iteration: %d", coreId, iteration));
            while (gcmr.getGeneration() < 3000000) {
                translation();
                detectReplication(iteration);
                if (gcmr.getGeneration() % 10000 == 0) {
                    Logger.println(String.format("CPU ID:[%d] Generation: %d", coreId, gcmr.getGeneration()));
                    gcmr.save("test");
                }
                resetGeneration();
            }
            gcmr = null;
            initGeneticCMR();
            iteration = Consts.getIteration();
        }
        Logger.println(String.format("CPU ID:[%d] End Thread", coreId));
    }



    private void resetGeneration() {
        gcmr.newGeneration();
        worlds.clear();
        gcmr.generatedCMR();
    }




    private void detectReplication(int iteration) {
        for (int i = 0; i < worlds.size(); i += 1) {
            CMR cmr = worlds.get(i).cmr;
            if (cmr.success) {
                String fileName = String.format("%d_%d_%04d_%d_%d", iteration, gcmr.getGeneration(), i, System.currentTimeMillis());
                Logger.println(String.format("Find conditions! save to [%s]", fileName));
                ObjectSaver.save(cmr.toString(), fileName);
            }
        }
    }





    private void translation() {
        for (int iterate = 0; iterate < 30; iterate += 1) {
            for (int i = 0; i < worlds.size(); i += 1) {
                World world = worlds.get(i);
                world.nextState();
                Fitness fitness = world.getPatternFitness(pattern);
                world.cmr.fitness.add(fitness.fitness);
                if (fitness.perfectCount >= 4) {
                    world.cmr.success = true;
                }
            }
        }
    }




    private void initGeneticCMR() {
        gcmr = new GeneticCMR(CMR_SIZE, 8, 4, 8);
        gcmr.setOnGenerateListener(this);
        gcmr.generatedCMR();

    }




    @Override
    public void onGenerate(CMR generatedCMR) {
        World world = new World(generatedCMR);
        world.putPattern(pattern);
        worlds.add(world);
    }



}
