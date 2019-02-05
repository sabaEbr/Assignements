package com.GO.frame.util;

import com.GO.frame.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    // -1 = not being pressed
    private static int mouseX = -1; // pos X
    private static int mouseY = -1; // pos Y
    private static int mouseB = -1; ///< button leftButton=1, middleButton=2, rightButton=3

    public MouseHandler(GamePanel game){
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
    }

    public int getX(){
        return mouseX;
    }
    public int getY(){
        return mouseY;
    }
    public int getButton(){
        return mouseB;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseB = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}