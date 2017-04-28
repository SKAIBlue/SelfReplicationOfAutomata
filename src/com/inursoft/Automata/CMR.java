package com.inursoft.Automata;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Anonymous on 2017. 3. 10..
 * Condition Matching Rule
 */
public class CMR implements Serializable {


    public Fitness fitness = new Fitness();


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
                conditionsList.add(new CellConditions(line));
            }

            toArray(conditionsList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void mutate() {

        for (int i = conditions.length - 1; i >= 0; i -= 1) {
            CellConditions condition = conditions[i];
            condition.mutate();
        }
    }


    public int conditionMatch(int east, int west, int center, int north, int south) {
        for (int i = 0; i < conditions.length; i += 1) {
            CellConditions condition = conditions[i];

            if (isMatchCondition(condition, east, west, center, north, south)) {
                //System.out.println(String.format("E: %d W: %d C: %d N: %d S: %d", east, west,center,north,south));
                //System.out.println(i+": " + condition.toString());
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


        /**
         * 돌연변이
         */
        public void mutate() {
            if (east.mutate()) {
                return;
            }
            if (west.mutate()) {
                return;
            }
            if (center.mutate()) {
                return;
            }
            if (north.mutate()) {
                return;
            }
            if (south.mutate()) {
                return;
            }

            if (rand.nextFloat() < geneticCMR.getTransferValueMutation()) {
                transValue += Math.abs(rand.nextInt() % 2) == 0 ? 1 : -1;
                if (transValue < 0) {
                    transValue = 0;
                } else if (transValue > geneticCMR.getConditionMaxValue()) {
                    transValue = geneticCMR.getConditionMaxValue();
                }
            }
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

    }


}
