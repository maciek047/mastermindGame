package com.kodilla;

import java.util.Random;

public class WinningSet {
    private Integer[] currentSet;

    public WinningSet() {
        Random random = new Random();
        this.currentSet = new Integer[6];
        for(int i=0;i<6;i++){
            this.currentSet[i] = random.nextInt(6);
        }
    }

    public Integer getCurrentSet(int index) {
        return currentSet[index];
    }
}

