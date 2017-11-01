package com.inursoft.Automata;

import org.junit.jupiter.api.Test;

/**
 * Created by Anonymous on 2017. 3. 22..
 */
public class WorldTest {
    @Test
    void getPatternCount() {

        int[][] pattern1 =
                {
                        {0, 1, 1, 3, 0},
                        {3, 2, 2, 2, 1},
                        {1, 2, 1, 2, 1},
                        {1, 2, 2, 2, 3},
                        {0, 3, 1, 1, 0},
                };

        int[][] pattern2 =
        {
                {0, 1, 1, 3, 0, 0, 0, 1, 1, 3, 0},
                {3, 2, 2, 2, 1, 0, 3, 2, 2, 2, 1},
                {1, 2, 1, 2, 1, 0, 1, 2, 1, 2, 1},
                {1, 2, 2, 2, 3, 0, 1, 2, 2, 2, 3},
                {0, 3, 1, 1, 0, 0, 0, 3, 1, 1, 0},
        };

        World world = new World(null);
        world.putPattern(pattern2);
        Fitness fitness = world.getPatternFitness(pattern1);
        System.out.println(fitness);
    }


    private int getSmallerAxisValue(int[][] v)
    {
        int y = v.length;
        int x = v[0].length;
        return x < y ? x : y;
    }



    @Test
    public int solution(int[][] board) {
        int width = getSmallerAxisValue(board);
        while(width > 0)
        {
            for(int i = 0 ; i < board.length - width + 1; i +=1)
            {
                for(int j = 0 ; j <board[i].length - width + 1; j+=1)
                {
                    if(isQuad(board, width, i, j))
                    {
                        return width * width;
                    }
                }
            }
            width -= 1;
        }
        return 0;
    }

    private boolean isQuad(int[][] v, int width, int i, int j)
    {
        int maxHeightPoint = i + width;
        int maxWidthPoint = j + width;
        for(int y = i ; y < maxHeightPoint; y +=1)
        {
            for(int x = j ; x < maxWidthPoint; x +=1)
            {
                if(v[y][x] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }


    @Test
    public void start()
    {
        System.out.println(solution(new int[][]{{0, 1, 1, 1},{1, 1, 1, 1},{1, 1, 1, 1},{0, 0, 1, 0}}));
        System.out.println(solution(new int[][]{{0, 0, 1, 1},{1, 1, 1, 1}}));
    }

}