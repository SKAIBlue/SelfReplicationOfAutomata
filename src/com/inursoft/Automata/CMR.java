package com.inursoft.Automata;

import com.sun.deploy.util.OrderedHashSet;

import java.io.Serializable;

/**
 * Created by Anonymous on 2017. 3. 10..
 * Condition Matching Rule
 */
public class CMR implements Serializable{




    private OrderedHashSet conditions = new OrderedHashSet();



    public int conditionMatch(int east, int west, int center, int north, int south)
    {
        for(int i = 0 ; i < conditions.size(); i+=1)
        {
            CellConditions condition = (CellConditions) conditions.get(i);
            if(isMatchCondition(condition, east, west, center, north, south))
            {
                return condition.transValue;
            }
        }
        return 0;
    }


    private boolean isMatchCondition(CellConditions conditions, int east, int west, int center, int north, int south)
    {
        return conditions.east.matching(east) &&
                conditions.west.matching(west) &&
                conditions.center.matching(center) &&
                conditions.north.matching(north) &&
                conditions.south.matching(south);
    }


    /**
     * 값이 전이되는 조건입니다.
     */
    public class CellConditions
    {


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


        private CellConditions(CellConditions cellConditions)
        {
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
        protected CellConditions clone(){
            return new CellConditions(this);
        }


    }



}