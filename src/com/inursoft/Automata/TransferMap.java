package com.inursoft.Automata;

import java.util.Random;

/**
 * Created by Anonymous on 2017. 3. 6..
 * 전이를 위한 클래스 입니다.
 */
public class TransferMap {



    private int[/*E*/][/*W*/][/*C*/][/*N*/][/*S*/] transfer;



    private Random rand;




    private int max;




    /**
     * 적합
     */
    public float fitness = 0.0f;




    public TransferMap(TransferMap transferMap)
    {
        rand = new Random();
        max = transferMap.max;
        copy(transferMap);
    }




    public TransferMap(int max)
    {
        rand = new Random();
        this.max = max;
        initialize();
    }


    /**
     * 다음 상태로 전이
     * @param cells
     */
    public void nextState(Cells cells)
    {
        int[][] cell = cells.getCells();
        for(int i = 0 ; i < cell.length; i+=1)
        {
            int[] raw = cell[i];
            for(int j = 0 ; j < raw.length ; j+=1)
            {
                raw[j] = transfer[getEastValue(cell, j, i)][getWestValue(cell, j, i)][getCellValue(cell, j, i)][getNorthValue(cell, j, i)][getSouthValue(cell, j, i)];
            }
        }
    }




    private int getEastValue(int[][] cells, int x, int y)
    {
        if(x == 0)
        {
            return 0;
        }
        return cells[y][x - 1];
    }



    private int getWestValue(int[][] cells, int x, int y)
    {
        if(cells[y].length - 1 == x)
        {
            return 0;
        }
        return cells[y][x + 1];
    }



    private int getCellValue(int[][] cells, int x, int y)
    {
        return cells[y][x];
    }



    private int getNorthValue(int[][] cells, int x, int y)
    {
        if(y == 0)
        {
            return 0;
        }
        return cells[y - 1][x];
    }


    private int getSouthValue(int[][] cells, int x, int y)
    {
        if(cells.length - 1 == y)
        {
            return 0;
        }
        return cells[y + 1][x];
    }


    private void initialize()
    {
        transfer = new int[max][][][][];

        for(int i = 0 ; i < max ; i+=1)
        {

            transfer[i] = new int[max][][][];
            for(int j = 0 ; j < max ; j+=1)
            {

                transfer[i][j] = new int[max][][];

                for(int k = 0 ; k < max ; k+=1)
                {

                    transfer[i][j][k] = new int[max][];

                    for(int l = 0 ; l < max ; l+=1)
                    {

                        transfer[i][j][k][l] = new int[max];

                        for(int m = 0 ; m < max ; m+=1)
                        {
                            transfer[i][j][k][l][m] = rand.nextInt() % max;
                        }
                    }
                }
            }
        }
    }




    private void copy(TransferMap dest)
    {
        transfer = new int[max][][][][];

        for(int i = 0 ; i < max ; i+=1)
        {

            transfer[i] = new int[max][][][];
            for(int j = 0 ; j < max ; j+=1)
            {

                transfer[i][j] = new int[max][][];

                for(int k = 0 ; k < max ; k+=1)
                {

                    transfer[i][j][k] = new int[max][];

                    for(int l = 0 ; l < max ; l+=1)
                    {

                        transfer[i][j][k][l] = new int[max];

                        for(int m = 0 ; m < max ; m+=1)
                        {
                            transfer[i][j][k][l][m] = dest.transfer[i][j][k][l][m];
                        }
                    }
                }
            }
        }
    }


}
