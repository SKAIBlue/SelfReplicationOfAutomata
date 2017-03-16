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

    /**
     * 개수
     */
    private int population = 1000;


    /**
     *
     */
    private int staleSpecies = 30;


    /**
     * 조건 최대값
     */
    private int conditionMaxValue = 10;


    /**
     * 새로운 조건이 생길 돌연변이 확률
     */
    private float newMutation = 0.3f;


    /**
     * 조건이 변경될 돌연변이 확률
     */
    private float conditionMutation = 0.15f;



    /**
     * 전이값이 변경될 돌연변이 확률
     */
    private float transferValueMutation = 0.15f;



    /**
     * 조건 값이 변경될 돌연변이 확률
     */
    private float valueMutation = 0.15f;


    private OnGenerateListener generateListener;


    private List<CMR> cmrs = new ArrayList<>();


    private Random rand = new Random();



    public GeneticCMR()
    {

    }




    public GeneticCMR(int population, int staleSpecies, int conditionMaxValue) {
        this.population = population;
        this.staleSpecies = staleSpecies;
        this.conditionMaxValue = conditionMaxValue;
        initialize();
    }




    public GeneticCMR(int population, int staleSpecies, int conditionMaxValue, float newMutation, float conditionMutation, float transferValueMutation, float valueMutation, List<CMR> cmrs) {
        this.population = population;
        this.staleSpecies = staleSpecies;
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
        List<CMR> orderedCMR = sortByFitness(cmrs);
        CMR highest = orderedCMR.get(0);

        cmrs.clear();

        for(int i = 0 ; i < population - staleSpecies; i += 1)
        {
            CMR newCMR = highest.clone();
            newCMR.mutate();
            cmrs.add(newCMR);
        }

        for(int i = population - staleSpecies; i < population ; i+=1)
        {
            CMR newCMR = orderedCMR.get(rand.nextInt() % population).clone();
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
        try {
            FileOutputStream fos = new FileOutputStream(String.format("%s.gcmr", fileName), false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            System.out.println("입출력 오류가 발생하였습니다.");
        }

    }




    /**
     * 저장된 데이터를 로드합니다.
     * 오류가 발생할 경우 null 을 반환합니다.
     * @param fileName 파일 이름
     * @return
     */
    public static GeneticCMR load(String fileName)
    {
        try {
            FileInputStream fis = new FileInputStream(String.format("%s.gcmr", fileName));
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (GeneticCMR) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("저장된 파일을 찾을 수 없습니다.");
        } catch (IOException e) {
            System.out.println("입출력 오류가 발생하였습니다.");
        } catch (ClassNotFoundException e) {
            System.out.println("일치하는 클래스가 존재하지 않습니다.");
        } catch (ClassCastException e) {
            System.out.println("클래스를 변환할 수 없습니다. 잘못된 데이터 같습니다.");
        }
        return null;
    }


    public int getPopulation() {
        return population;
    }

    public int getStaleSpecies() {
        return staleSpecies;
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


    public void setOnGenerateListener(OnGenerateListener generateListener) {
        this.generateListener = generateListener;
    }


    public interface OnGenerateListener
    {
        void onGenerate(CMR generatedCMR);
    }


}
