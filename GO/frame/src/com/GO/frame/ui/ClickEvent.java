package com.GO.frame.ui;

import com.GO.frame.util.MouseHandler;

public class ClickEvent extends HoverEvent{

    public ClickEvent(int leftExtremity, int topExtremity, int width, int height){
        super(leftExtremity, topExtremity, width, height);
    }

    public boolean isClicked(MouseHandler mouse){
        return (super.isHovering(mouse) && mouse.getButton() == 1);
    }
}

