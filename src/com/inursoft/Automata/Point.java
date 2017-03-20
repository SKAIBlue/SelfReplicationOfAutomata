package com.inursoft.Automata;

/**
 * Created by Anonymous on 2017. 3. 20..
 */
public class Point {


    private int x;


    private int y;



    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;
    }


    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }


}
