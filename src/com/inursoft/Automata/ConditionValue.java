package com.inursoft.Automata;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by Anonymous on 2017. 3. 10..
 * Condition and value for Condition Matching Rule
 */
public class ConditionValue implements Serializable{


    private static ICondition EQ = new Equals();


    private static ICondition NE = new NotEquals();


    private static ICondition LTE = new LowerOrEqual();


    private static ICondition GTE = new GreaterOrEqual();



    private GeneticCMR geneticCMR;




    private Random rand = new Random();


    /**
     * 조건
     */
    private ICondition condition = EQ;



    /**
     * 값2
     */
    private int value2 = 0;





    public ConditionValue(GeneticCMR geneticCMR) {
        this.geneticCMR = geneticCMR;
        changeCondition(rand.nextInt());
        setValue2(rand.nextInt());
    }




    public void setValue2(int value2) {
        this.value2 = Math.abs(value2) % geneticCMR.getConditionMaxValue();
    }




    public int getValue2() {
        return value2;
    }




    /**
     * 이 인스턴스가 가지고 있는 조건으로 결과를 반환합니다.
     * @param value
     * @return
     */
    public boolean matching(int value)
    {
        return condition.condition(value, value2);
    }



    public boolean mutate()
    {
        if(rand.nextFloat() < geneticCMR.getConditionMutation())
        {
            changeCondition(rand.nextInt());
            return true;
        }
        else if(rand.nextFloat() < geneticCMR.getValueMutation())
        {
            setValue2(rand.nextInt());
            return true;
        }
        return false;
    }


    /**
     * 이 조건을 복제합니다.
     * @return 복제된 조건

     */
    @Override
    protected ConditionValue clone() {
        ConditionValue conditionValue = new ConditionValue(geneticCMR);
        conditionValue.condition = condition;
        conditionValue.value2 = value2;
        return conditionValue;
    }






    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionValue that = (ConditionValue) o;

        if (value2 != that.value2) return false;
        return condition.equals(that.condition);
    }





    @Override
    public int hashCode() {
        int result = condition.hashCode();
        result = 31 * result + value2;
        return result;
    }


    @Override
    public String toString() {
        return String.format("%s %d", condition.toString(), value2);
    }

    /**
     * 이 인스턴스가 가지고 있는 조건을 변경합니다.
     * 0 ~ 5 사이의 정수를 입력해야 하며
     * 만약 범위 외의 정수가 입력된다면 bindValue % 5로 조건을 설정합니다.
     * 0: Equals
     * 1: Lower
     * 2: Lower or Equals
     * 3: Greater
     * 4: Greater or Equals
     * @param bindValue 0 ~ 4의 정수
     */
    public void changeCondition(int bindValue)
    {
        switch (Math.abs(bindValue % 4))
        {
            case 0:
                condition = EQ;
            case 1:
                condition = NE;
            case 2:
                condition = LTE;
            case 3:
                condition = GTE;
        }

    }



    /**
     * interface for condition
     */
    private interface ICondition
    {

        boolean condition(int value1, int value2);

    }


    /**
     * Value 1 and value2 are Not Equals
     */
    private static class NotEquals implements ICondition, Serializable
    {

        @Override
        public boolean condition(int value1, int value2) {
            return value1 != value2;
        }


        @Override
        public String toString() {
            return String.format("!=");
        }
    }


    /**
     * value1 and value2 are Equals
     */
    private static class Equals implements ICondition, Serializable
    {

        @Override
        public boolean condition(int value1, int value2) {
            return value1 == value2;
        }


        @Override
        public String toString() {
            return String.format("==");
        }
    }




    /**
     * value1 is lower then value2 or equals
     */
    private static class LowerOrEqual implements ICondition, Serializable
    {

        @Override
        public boolean condition(int value1, int value2) {
            return value1 <= value2;
        }

        @Override
        public String toString() {
            return String.format("<=");
        }
    }






    /**
     * value1 is greater then value2 or equals.
     */
    private static class GreaterOrEqual implements ICondition, Serializable
    {

        @Override
        public boolean condition(int value1, int value2) {
            return value1 >= value2;
        }

        @Override
        public String toString() {
            return String.format(">=");
        }
    }








}
