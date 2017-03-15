package com.inursoft.Automata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anonymous on 2017. 3. 6..
 */
public class GeneticAutomata {



    /**
     * 인구
     */
    public int population = 50;



    /**
     * 인구중 살아남을 비율
     */
    public int staleSpecies = 15;



    /**
     * 돌연변이 확률
     */
    public float mutateRate = 0.035f;



    /**
     * 최대 적합
     */
    public float maxFitness = 0f;




    /**
     * 전이
     */
    public List<TransferMap> genome = new ArrayList<>();




    public void newGeneration()
    {
        TransferMap max = null;


        for(int i = 0 ; i < genome.size(); i+=1)
        {
            TransferMap map = genome.get(i);

        }

    }



    public void generate()
    {

        for(int i = 0 ; i < population; i+=1)
        {

        }
        genome.clear();

        for(int i = 0 ; i < population; i+=1)
        {

        }
    }





}
