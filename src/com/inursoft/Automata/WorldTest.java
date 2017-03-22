package com.inursoft.Automata;

import org.junit.jupiter.api.Test;

/**
 * Created by Anonymous on 2017. 3. 22..
 */
class WorldTest {
    @Test
    void getPatternCount() {
        int[][] worldPattern =
                {
                        {0, 0, 0, 0, 0, 0, 0, 0}, //8
                        {0, 1, 1, 0, 0, 0, 0, 0}, //6
                        {0, 1, 1, 0, 0, 1, 0, 0}, //7
                        {0, 0, 0, 0, 0, 0, 0, 0}, //8
                        {0, 0, 1, 0, 0, 0, 0, 0}  //7
                };
        int[][] pattern =
                {
                        {0, 0, 0, 0},
                        {0, 1, 1, 0},
                        {0, 1, 1, 0},
                        {0, 0, 0, 0},
                };
        World world = new World(null);
        world.put(worldPattern);
        System.out.println(world.getPatternFitness(pattern));
        assert world.getPatternFitness(pattern) == 29;
    }

}