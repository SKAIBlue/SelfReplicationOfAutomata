package com.inursoft.Automata;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by Anonymous on 2017. 3. 10..
 * Condition Matching Rule
 */
public class CMR implements Serializable {



    public List<Integer> fitness = new ArrayList<>();



    public boolean success = false;



    private Random rand = new Random();



    private CellConditions[] conditions;



    private GeneticCMR geneticCMR;



    public CMR(GeneticCMR geneticCMR) {
        if (geneticCMR != null) {
            conditions = new CellConditions[geneticCMR.getCmrSize()];

            for(int i = 0 ; i < geneticCMR.getCmrSize() ; i+=1)
            {
                conditions[i] = new CellConditions(geneticCMR);
            }

            this.geneticCMR = geneticCMR;
        }
    }


    private CMR(CMR original) {
        this.geneticCMR = original.geneticCMR;
        conditions = new CellConditions[geneticCMR.getCmrSize()];
        for (int i = 0; i < original.conditions.length; i += 1) {
            CellConditions conditions = original.conditions[i];
            CellConditions newCondition = conditions.clone();
            this.conditions[i] = newCondition;
        }
    }


    public CMR(String path) {
        try {
            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);

            String line;
            List<CellConditions> conditionsList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if(line.length() == 0 )
                {
                    continue;
                }
                conditionsList.add(new CellConditions(line));
            }

            toArray(conditionsList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public HashSet<Integer> randomIntegers(int m, int n)
    {
        HashSet<Integer> integers = new HashSet<>();
        if(m == 0)
            return integers;
        integers.addAll(randomIntegers(m - 1, n - 1));
        int i = Math.abs(rand.nextInt() % n);
        if(integers.contains(i))
            integers.add(n);
        else
            integers.add(i);
        return integers;
    }


    public void mutate() {
        int mutateValue = Math.abs(rand.nextInt()) % 3;
        HashSet<Integer> integerSet = randomIntegers(mutateValue, geneticCMR.getCmrSize() * 11);
        /*
        while( integerSet.size() < mutateValue )
        {
            int newValue = Math.abs(rand.nextInt() % (geneticCMR.getCmrSize() * 11));
            if(!integerSet.contains(newValue))
            {
                integerSet.add(newValue);
            }
        }
        */

        for(Integer integer : integerSet)
        {
            int index = integer / 11;
            int inIndex = integer % 11;
            conditions[index].mutate(inIndex);
        }
    }




    public int conditionMatch(int east, int west, int center, int north, int south) {
        for (int i = 0; i < conditions.length; i += 1) {
            CellConditions condition = conditions[i];

            if (isMatchCondition(condition, east, west, center, north, south)) {
                return condition.transValue;
            }
        }
        return center;
    }




    private void toArray(List<CellConditions> conditionsList) {
        conditions = new CellConditions[conditionsList.size()];
        for(int i = 0 ; i < conditionsList.size(); i+=1)
        {
            conditions[i] = conditionsList.get(i);
        }
    }





    private boolean isMatchCondition(CellConditions conditions, int east, int west, int center, int north, int south) {
        return conditions.east.matching(east) &&
                conditions.west.matching(west) &&
                conditions.center.matching(center) &&
                conditions.north.matching(north) &&
                conditions.south.matching(south);
    }


    @Override
    protected CMR clone() {
        return new CMR(this);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(CellConditions condition : conditions)
        {
            builder.append(String.format("%s\n", condition.toString()));
        }
        return builder.toString();
    }


    public int getBestFitness()
    {
        int max = 0;
        for(int i = 0 ; i < fitness.size(); i+=1)
        {
            int val = fitness.get(i);
            if(val > max)
            {
                max = val;
            }
        }
        return max;
    }

    /**
     * 값이 전이되는 조건입니다.
     */
    public class CellConditions implements Serializable {

        private GeneticCMR geneticCMR;

        /**
         * 동쪽 셀의 조건
         */
        private ConditionValue east;


        /**
         * 서쪽 셀의 조건
         */
        private ConditionValue west;


        /**
         * 자신 셀의 조건
         */
        private ConditionValue center;


        /**
         * 북쪽 셀의 조건
         */
        private ConditionValue north;


        /**
         * 남쪽 셀의 조건
         */
        private ConditionValue south;


        /**
         * 전이될 값
         */
        private int transValue;


        public CellConditions(GeneticCMR geneticCMR) {
            this.geneticCMR = geneticCMR;
            east = new ConditionValue(geneticCMR);
            west = new ConditionValue(geneticCMR);
            center = new ConditionValue(geneticCMR);
            north = new ConditionValue(geneticCMR);
            south = new ConditionValue(geneticCMR);
            transValue = Math.abs(rand.nextInt() % 2);
        }


        public CellConditions(String line) {
            String[] splits = line.split(" ");
            east = new ConditionValue(splits[0], splits[1]);
            west = new ConditionValue(splits[2], splits[3]);
            center = new ConditionValue(splits[4], splits[5]);
            north = new ConditionValue(splits[6], splits[7]);
            south = new ConditionValue(splits[8], splits[9]);
            transValue = Integer.valueOf(splits[10]);
        }


        private CellConditions(CellConditions cellConditions) {
            this.geneticCMR = cellConditions.geneticCMR;
            east = cellConditions.east.clone();
            west = cellConditions.west.clone();
            center = cellConditions.center.clone();
            north = cellConditions.north.clone();
            south = cellConditions.south.clone();
            transValue = cellConditions.transValue;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CellConditions that = (CellConditions) o;

            if (transValue != that.transValue) return false;
            if (!east.equals(that.east)) return false;
            if (!west.equals(that.west)) return false;
            if (!center.equals(that.center)) return false;
            if (!north.equals(that.north)) return false;
            return south.equals(that.south);
        }


        /**
         * 중복되는 조건을 찾기 위해 해시 생성
         *
         * @return
         */
        @Override
        public int hashCode() {
            int result = east.hashCode();
            result = 31 * result + west.hashCode();
            result = 31 * result + center.hashCode();
            result = 31 * result + north.hashCode();
            result = 31 * result + south.hashCode();
            result = 31 * result + transValue;
            return result;
        }


        @Override
        public String toString() {
            return String.format("%s %s %s %s %s %d", east, west, center, north, south, transValue);
        }

        @Override
        protected CellConditions clone() {
            return new CellConditions(this);
        }


        public ConditionValue getEast() {
            return east;
        }


        public ConditionValue getWest() {
            return west;
        }


        public ConditionValue getCenter() {
            return center;
        }


        public ConditionValue getNorth() {
            return north;
        }


        public ConditionValue getSouth() {
            return south;
        }


        public int getTransValue() {
            return transValue;
        }

        public void mutate(int index)
        {
            int direction = index / 2;
            int conditionOrValue = index % 2;
            switch (direction)
            {
                case 0:
                    east.mutate(conditionOrValue);
                    break;
                case 1:
                    west.mutate(conditionOrValue);
                    break;
                case 2:
                    center.mutate(conditionOrValue);
                    break;
                case 3:
                    north.mutate(conditionOrValue);
                    break;
                case 4:
                    south.mutate(conditionOrValue);
                    break;
                case 5:
                    transValue = Math.abs(rand.nextInt()) % geneticCMR.getConditionMaxValue();
                    break;

            }
        }

    }


}
