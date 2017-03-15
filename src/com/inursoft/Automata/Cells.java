package com.inursoft.Automata;

/**
 * Created by Anonymous on 2017. 3. 6..
 */
public class Cells {


    private int[][] cells;



    private int width;



    private int height;



    /**
     * 비어있는 셀들을 생성
     * @param width 셀의 최대 너비
     * @param height 셀의 최대 높이
     */
    public Cells(int width, int height)
    {
        this.width = width;
        this.height = height;

        cells = new int[height][];

        for(int i = 0 ; i < height; i+=1)
        {
            cells[i] = new int[width];
        }
    }



    /**
     * 셀을 복사
     * @param dest 원본 셀
     */
    public Cells(Cells dest)
    {
        width = dest.width;
        height = dest.height;

        cells = new int[height][];

        for(int i = 0 ; i < height; i+=1)
        {
            cells[i] = new int[width];
            for(int j = 0 ; j < width ; j +=1)
            {
                cells[i][j] = dest.cells[i][j];
            }
        }
    }




    /**
     * pattern 의 모양이 있는 셀
     * @param pattern 모양
     * @param width 셀의 최대 너비
     * @param height 셀의 최대 높이
     */
    public Cells(int [][] pattern, int width, int height)
    {

        this.width = width;
        this.height = height;

        cells = new int[height][];

        for(int i = 0 ; i < height; i+=1)
        {
            cells[i] = new int[width];
        }

        for(int i = 0 ; i < pattern.length; i+=1)
        {
            int[] inDest = pattern[i];
            for(int j = 0 ; j < inDest.length ; j+=1)
            {
                cells[i][j] = inDest[j];
            }
        }
    }




    public int[][] getCells()
    {
        return cells;
    }



    /**
     * 패턴의 수를 가져옵니다.
     * @return 패턴의수
     */
    public int getPatternCount(int[][] pattern)
    {

        int count = 0;
        int patternWidth = pattern[0].length;
        int patternHeight = pattern.length;

        for(int i = 0 ; i < height - patternHeight; i+=1)
        {
            for(int j = 0; j < width - patternWidth; j+=1)
            {
                boolean isCount = true;
                for(int k = 0 ; k < patternHeight; k+=1)
                {
                    for(int l = 0 ; l < patternWidth; l+=1)
                    {

                        if(cells[j + k][i + l] != pattern[k][l])
                        {
                            isCount = false;
                            break;
                        }

                    }

                    if(!isCount)
                    {
                        break;
                    }
                }
                if(isCount)
                {
                    count +=1;
                }
            }
        }
        return count;
    }


}
