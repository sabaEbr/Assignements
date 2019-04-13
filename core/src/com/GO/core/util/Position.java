package com.GO.core.util;

/*
* Basic Position class to be use throughout project for Stones and GoEngine
* i.e. position indicates invalid positions
*
* */

import java.util.ArrayList;
import java.util.List;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Position(Position position){
        // Copy Constructor
        this.x = position.getX();
        this.y = position.getY();
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isEqual(Position position){
        return (x == position.getX() && y == position.getY());
    }

    public static boolean listContains(ArrayList<Position> positions, Position pcsToCompare){
        for(Position position : positions){
            if(position.isEqual(pcsToCompare)){
                return true;
            }
        }
        return false;
    }

}
