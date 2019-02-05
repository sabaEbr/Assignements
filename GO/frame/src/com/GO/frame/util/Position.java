package com.GO.frame.util;

/*
* Basic Position class to be use throughout project for Stones and PlayState
* i.e. position indicates invalid positions
*
* */

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

}
