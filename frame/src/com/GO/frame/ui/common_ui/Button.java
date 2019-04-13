package com.GO.frame.ui.common_ui;

import com.GO.frame.util.MouseHandler;

import java.awt.*;

public class Button extends ClickEvent{

    private String text;
    private int textPosX;
    private int textPosY;

    private Font font = new Font("Osaka", Font.BOLD, 12);

    private Color color = Color.BLACK;

    public Button(int leftExtremity, int topExtremity, int width, int height,
                  String text, int textPosX, int textPosY){
        super(leftExtremity, topExtremity, width, height);

        this.text = text;
        this.textPosX = leftExtremity + textPosX;
        this.textPosY = topExtremity + textPosY;

    }

    public void setColor(Color color){
        this.color = color;
    }

    public void input(MouseHandler mouse){
        if (isHovering(mouse)){
//            setColor(new Color(105, 18, 15));
            setColor(Color.WHITE);
        } else {
            setColor(Color.BLACK);
        }
    }

    public void render(Graphics2D g){
        g.setColor(color);
        g.drawRect(leftExtremity, topExtremity, width, height);

        StringWriter.writeString(g, text, font, color, textPosX, textPosY);
    }



}
