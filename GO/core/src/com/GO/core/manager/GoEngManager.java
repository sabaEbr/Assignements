package com.GO.core.manager;


import com.GO.core.engine.GoBot;
import com.GO.core.engine.GoEngine;
import com.GO.core.engine.GoMultiplayer;


public class GoEngManager {

    public static final byte PvP = 0; //player vs player
    public static final byte PvC = 1; //player vs cpu
    public static final byte CvP = 2; // cpu vs player
    public static final byte ONL = 3; // online

    public static GoEngine startEngine(byte mode, byte nCells){
        switch (mode){
            case PvP: return new GoEngine(mode, nCells);
            case PvC: return new GoBot(mode, nCells);
            case CvP: return new GoBot(mode, nCells);
            case ONL: return new GoMultiplayer(mode, nCells);
            default: return null;
        }
    }
}
