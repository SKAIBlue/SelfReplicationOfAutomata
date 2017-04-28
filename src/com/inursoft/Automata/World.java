package com.inursoft.Automata;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Anonymous on 2017. 3. 20..
 */
public class World {



    public CMR cmr;



    private Map<Point, Integer> worldMap = new HashMap<>();



    private int width = 0;



    private int height = 0;


    public World(CMR cmr)
    {
        this.cmr = cmr;
    }


    /**
     * 월드에 데이터를 삽입합니다.
     * 0이 입력되면 맵에서 삭제됩니다.
     * @param p 좌표
     * @param value 값
     */
    public void put(Point p, int value)
    {
        if(value == 0)
        {
            return;
        }
        if(p.getX() > width - 2)
        {
            width = p.getX() + 2;
        }

        if(p.getY() > height - 2)
        {
            height = p.getY() + 2;
        }

        worldMap.put(p, value);
    }


    /**
     * 월드에 데이터를 삽입합니다.
     * 0이 입력되면 맵에서 삭제됩니다.
     * @param x x 좌표
     * @param y y 좌표
     * @param value 값
     */
    public void put(int x, int y, int value)
    {
        put(new Point(x, y), value);
    }


    /**
     * 월드에 데이터를 삽입합니다.
     * @param array 삽입할 데이터 패턴
     */
    public void put(int[][] array)
    {
        for(int i = 0 ; i < array.length ; i+=1)
        {
            int[] inner = array[i];
            for(int j = 0; j < inner.length ; j+=1)
            {
                int value = inner[j];
                put(j, i, value);
            }
        }
    }


    public void putPattern(int[][] array)
    {
        for(int i = 0 ; i < array.length ; i+=1)
        {
            int[] inner = array[i];
            for(int j = 0; j < inner.length ; j+=1)
            {
                int value = inner[j];
                put(j + 1, i + 1, value);
            }
        }
    }


    /**
     * 맵의 데이터를 모두 지웁니다.
     */
    public void clear()
    {
        width = 0;
        height = 0;
        worldMap.clear();
    }





    /**
     * 지도에서 셀값을 가져옵니다.
     * @param p 좌표값
     * @return 해당 좌표의 셀값
     */
    public int get(Point p)
    {
        if(worldMap.containsKey(p))
        {
            return worldMap.get(p);
        }
        return 0;
    }




    /**
     * 지도에서 셀값을 가져옵니다.
     * @param x x좌표 (좌측이 작음)
     * @param y y좌표 (상단이 작음)
     * @return 해당 좌표의 셀값
     */
    public int get(int x, int y)
    {
        return get(new Point(x, y));
    }





    /**
     * 지도에서 셀 패턴을 찾습니다.
     * @param pattern 패턴
     * @return 찾은 패턴의 수
     */
    public Fitness getPatternFitness(int[][] pattern)
    {
        int[][] world = toIntArray();
        int patternWidth = pattern[0].length;
        int patternHeight = pattern.length;
        int patternAreaSize = patternWidth * patternHeight;
        Fitness fitness = new Fitness();
        for(int i = 0 ; i <= height ; i+=patternHeight)
        {
            for(int j = 0; j <= width ; j+=patternWidth)
            {
                int incount = 0;
                for(int k = 0 ; k < patternHeight; k+=1)
                {

                    for(int l = 0 ; l < patternWidth; l+=1)
                    {
                        int x = j + l;
                        int y = i + k;
                        if(y < height && x < width && world[y][x] == pattern[k][l])
                        {
                            incount += 1;
                        }
                    }
                }
                if(incount == patternAreaSize)
                {
                    fitness.match += 1;
                }
                else
                {
                    fitness.part += incount;
                }
            }
        }
        return fitness;
    }



    /**
     * 너비를 가져옵니다.
     * @return
     */
    public int getWidth() {
        return width;
    }



    /**
     * 높이를 가져옵니다.
     * @return
     */
    public int getHeight() {
        return height;
    }


    public int getMax()
    {
        return width > height ? width : height;
    }


    /**
     * 맵의 데이터를 배열로 변환합니다.
     * @return
     */
    public int[][] toIntArray()
    {
        int[][] res = new int[height][];

        for(int i = 0; i < height; i+=1)
        {
            res[i] = new int[width];
        }

        for(int i = 0 ; i < height; i+=1)
        {
            for(int j = 0 ; j <width; j+=1)
            {
                res[i][j] = get(j, i);
            }
        }
        return res;
    }




    public Map<Point, Integer> toMap(int[][] worldArray)
    {
        Map<Point, Integer> map = new HashMap<>();

        for(int i = 0 ; i < height; i+=1)
        {
            for(int j = 0 ; j < width; j+=1)
            {
                int value = worldArray[i][j];

                map.put(new Point(j, i), value);

            }
        }
        return map;
    }



    public void nextState()
    {
        int[][] sources = toIntArray();
        int[][] res = toIntArray();
        for(int i = 0 ; i < height ; i +=1)
        {
            for(int j = 0 ; j < width; j+=1)
            {
                res[i][j] = cmr.conditionMatch(
                        getEastValue(sources, j, i),
                        getWestValue(sources, j, i),
                        sources[i][j],
                        getNorthValue(sources, j, i),
                        getSouthValue(sources, j, i)
                );
            }
        }
        clear();
        put(res);
    }



    private int getWestValue(int[][] world, int x, int y)
    {
        if(x == 0)
        {
            return 0;
        }
        return world[y][x - 1];
    }



    private int getEastValue(int[][] world, int x, int y)
    {
        if(world[y].length - 1 == x)
        {
            return 0;
        }
        return world[y][x + 1];
    }



    private int getNorthValue(int[][] world, int x, int y)
    {
        if(y == 0)
        {
            return 0;
        }
        return world[y - 1][x];
    }



    private int getSouthValue(int[][] world, int x, int y)
    {
        if(world.length - 1 == y)
        {
            return 0;
        }
        return world[y + 1][x];
    }


}
