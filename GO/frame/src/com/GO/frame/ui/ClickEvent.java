package com.GO.frame.ui;

import com.GO.frame.util.MouseHandler;

public class ClickEvent {
    protected int bottomExtremity;
    protected int topExtremity;
    protected int leftExtremity;
    protected int rightExtremity;

    protected int width;  // Relative to leftExtremity
    protected int height; // Relative to topExtremity

    public ClickEvent(int leftExtremity, int topExtremity, int width, int height){
        this.width = width;
        this.height = height;
        this.topExtremity = topExtremity;
        this.leftExtremity = leftExtremity;
        this.rightExtremity = width + leftExtremity;
        this.bottomExtremity = height + topExtremity;
    }

    public boolean isClicked(MouseHandler mouse){
        return (mouse.getX() >= leftExtremity && mouse.getX() <= rightExtremity &&
                mouse.getY() >= topExtremity && mouse.getY() <= bottomExtremity &&
                mouse.getButton() == 1);
    }
}

