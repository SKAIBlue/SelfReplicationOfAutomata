package com.inursoft.Automata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Anonymous on 2017. 3. 15..
 */
public class GeneticCMR implements Serializable
{
    private int generation = 0;
    /**
     * 개수
     */
    private int population = 1000;


    /**
     *
     */
    private int randomSelect = 30;


    /**
     * 조건 최대값
     */
    private int conditionMaxValue = 10;


    /**
     * 새로운 조건이 생길 돌연변이 확률
     */
    private float newMutation = 0.5f;


    /**
     * 조건이 변경될 돌연변이 확률
     */
    private float conditionMutation = 0.5f;



    /**
     * 전이값이 변경될 돌연변이 확률
     */
    private float transferValueMutation = 0.5f;



    /**
     * 조건 값이 변경될 돌연변이 확률
     */
    private float valueMutation = 0.5f;


    private transient OnGenerateListener generateListener;


    private List<CMR> cmrs = new ArrayList<>();


    private Random rand = new Random();



    public GeneticCMR(int conditionMaxValue)
    {
        this.conditionMaxValue = conditionMaxValue;
        initialize();
    }




    public GeneticCMR(int population, int staleSpecies, int conditionMaxValue) {
        this.population = population;
        this.randomSelect = staleSpecies;
        this.conditionMaxValue = conditionMaxValue;
        initialize();
    }




    public GeneticCMR(int population, int staleSpecies, int conditionMaxValue, float newMutation, float conditionMutation, float transferValueMutation, float valueMutation, List<CMR> cmrs) {
        this.population = population;
        this.randomSelect = staleSpecies;
        this.conditionMaxValue = conditionMaxValue;
        this.newMutation = newMutation;
        this.conditionMutation = conditionMutation;
        this.transferValueMutation = transferValueMutation;
        this.valueMutation = valueMutation;
        this.cmrs = cmrs;
        initialize();
    }




    private void initialize()
    {
        for(int i = 0 ; i < population; i+=1)
        {
            CMR cmr = new CMR(this);
            cmrs.add(cmr);
        }
    }



    /**
     * 다음 세대로 진화합니다.
     */
    public void newGeneration()
    {
        generation += 1;

        List<CMR> selected = new ArrayList<>();


        for(int i = 0 ; i < randomSelect ; i+=1)
        {
            int randIdx = Math.abs(rand.nextInt() % cmrs.size());
            selected.add(cmrs.get(randIdx));
            cmrs.remove(randIdx);
        }
        cmrs.clear();
        CMR best = sortByFitness(selected).get(0);

        for(int i = 0 ; i < population; i += 1)
        {
            CMR newCMR = best.clone();
            newCMR.mutate();
            cmrs.add(newCMR);
        }

    }





    /**
     * 생성된 CMR을 가져옵니다.
     */
    public void generatedCMR()
    {
        if(generateListener != null)
        {
            for(int i = 0 ; i < cmrs.size(); i+=1)
            {
                CMR cmr = cmrs.get(i);
                generateListener.onGenerate(cmr);
            }
        }
    }






    /**
     * fitness 로 정렬합니다.
     * @return
     */
    public List<CMR> sortByFitness(List<CMR> original)
    {
        int pivotIdx = original.size() / 2;
        if(pivotIdx == 0)
        {
            return new ArrayList<>();
        }
        CMR pivot = original.get(pivotIdx);
        List<CMR> same = new ArrayList<>();
        List<CMR> right = new ArrayList<>();
        List<CMR> left = new ArrayList<>();
        for(int i = 0 ; i < original.size(); i+=1)
        {
            CMR n = original.get(i);
            if(n.fitness > pivot.fitness)
            {
                right.add(n);
            }
            else if(n.fitness < pivot.fitness)
            {
                left.add(n);
            }
            else
            {
                same.add(n);
            }
        }
        List<CMR> result = new ArrayList<>();
        result.addAll(sortByFitness(right));
        result.addAll(same);
        result.addAll(sortByFitness(left));
        return result;
    }





    /**
     * 현재 데이터를 저장합니다.
     * @param fileName 파일 이름
     */
    public void save(String fileName)
    {
        ObjectSaver.save(this, String.format("%s.gcmr", fileName));
    }




    /**
     * 저장된 데이터를 로드합니다.
     * 오류가 발생할 경우 null 을 반환합니다.
     * @param fileName 파일 이름
     * @return
     */
    public static GeneticCMR load(String fileName)
    {
        return (GeneticCMR)ObjectSaver.load(String.format("%s.gcmr", fileName));
    }


    public int getPopulation() {
        return population;
    }

    public int getRandomSelect() {
        return randomSelect;
    }

    public int getConditionMaxValue() {
        return conditionMaxValue;
    }

    public float getNewMutation() {
        return newMutation;
    }

    public float getConditionMutation() {
        return conditionMutation;
    }

    public float getTransferValueMutation() {
        return transferValueMutation;
    }

    public float getValueMutation() {
        return valueMutation;
    }


    public int getGeneration() {
        return generation;
    }

    public void setOnGenerateListener(OnGenerateListener generateListener) {
        this.generateListener = generateListener;
    }


    public interface OnGenerateListener
    {
        void onGenerate(CMR generatedCMR);
    }


}
