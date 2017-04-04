package com.inursoft.Automata;

/**
 * Created by Anonymous on 2017. 4. 5..
 */

public class Fitness {

    int match = 0;

    int part = 0;

    public Fitness() {
    }

    public Fitness(int match, int part) {
        this.match = match;
        this.part = part;
    }


    public boolean gt(Fitness fitness)
    {
        if(match > fitness.match)
        {
            return true;
        }
        else if(match == fitness.match)
        {
            return part > fitness.part;
        }
        return false;
    }


    @Override
    public String toString() {
        return String.format("Match=%d, part=%d", match, part);
    }
}
