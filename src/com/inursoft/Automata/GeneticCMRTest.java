package com.inursoft.Automata;

import com.inursoft.Data.Consts;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Anonymous on 2017. 3. 15..
 */
class GeneticCMRTest {

    @org.junit.jupiter.api.Test
    void sortByFitness() {

        Random r = new Random();
        List<CMR> cmr = new ArrayList<>();
        for(int i = 0 ; i < 1000; i +=1)
        {
            //CMR c = new CMR(null);
            //c.fitness = r.nextFloat();
            //cmr.add(c);
        }
        GeneticCMR geneticCMR = new GeneticCMR(Consts.CMR_SIZE, 0);
        long time = System.currentTimeMillis();

        List<CMR> sorted = geneticCMR.sortByFitness(cmr);

        time = System.currentTimeMillis() - time;

        for(int i = 0 ; i < sorted.size() ; i+=1)
        {
            CMR c = sorted.get(i);
            System.out.println(c.fitness);
        }
        

        System.out.println(String.format("정렬에 걸린 시간: %d", time));
    }

    enum Test {ACE, DEUCE, THREE}


    int indexOf(String input)
    {/*
        for(int i = 0 ; i < faces.length ; i +=1)
        {
            if(faces[i].equals(input))
            {
                return i;
            }
        }
        Random rand = new Random();
        faces[Math.abs(rand.nextInt()) % faces.length];
        return -1;
        */
        return 0;
    }



    @org.junit.jupiter.api.Test
    void randomTest()
    {


        Test test = Test.ACE;
        System.out.println(test);
        String[] str;

        Random r= new Random();

        for(int i = 0 ; i < 100 ; i += 1)
        {
            System.out.println(r.nextFloat());
        }
    }

}