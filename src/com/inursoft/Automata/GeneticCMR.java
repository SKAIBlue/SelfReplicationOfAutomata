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

    private Object locker = new Object();



    private int iteration = 0;



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


    private transient OnGenerateListener generateListener;


    private List<CMR> cmrs = new ArrayList<>();



    private int cmrSize = 0;



    private Random rand = new Random();



    public GeneticCMR(int cmrSize, int conditionMaxValue)
    {
        this.cmrSize = cmrSize;
        this.conditionMaxValue = conditionMaxValue;
        initialize();
    }




    public GeneticCMR(int cmrSize, int population, int randomSelect, int conditionMaxValue) {
        this.cmrSize = cmrSize;
        this.population = population;
        this.randomSelect = randomSelect;
        this.conditionMaxValue = conditionMaxValue;
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
        List<CMR> selected = new ArrayList();
        for(int i = 0 ; i < randomSelect ; i +=1)
        {
            int selectIndex = Math.abs(rand.nextInt()) % cmrs.size();
            selected.add(cmrs.get(selectIndex));
            cmrs.remove(selectIndex);
        }

        List<CMR> sorted = sortByFitness(selected);

        CMR best = sorted.get(0);
        sorted.remove(0);
        cmrs.clear();

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
            if(n.getBestFitness() > pivot.getBestFitness())
            {
                right.add(n);
            }
            else if(pivot.getBestFitness() > n.getBestFitness())
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


    public int getCmrSize() {
        return cmrSize;
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


    public int getIteration()
    {
        synchronized (locker)
        {
            return iteration;
        }
    }


    public void increaseIteration()
    {
        synchronized (locker)
        {
            iteration += 1;
        }
    }



}
