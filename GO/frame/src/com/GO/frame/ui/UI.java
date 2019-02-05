package com.GO.frame.ui;

import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;
import com.GO.frame.util.StringWriter;

import java.awt.*;
import java.util.ArrayList;

public class UI {
    protected ArrayList<Button> buttonsList;
    protected String gameTitle = "Go on the GO";

    public UI(){
        buttonsList = new ArrayList<>();
    }

    public void update(){

    }

    public void input(MouseHandler mouse, KeyHandler key){
        buttonsList.forEach(button -> button.input(mouse));
    }

    public void render(Graphics2D g){
        buttonsList.forEach(button -> button.render(g));

        // Print out the page title
        StringWriter.writeString(g, "GO on the GO", new Font("Helvetica",
                        Font.ITALIC | Font.BOLD,22),
                Color.DARK_GRAY,
                1280/2 - 70, 30);

        // Print production
        StringWriter.writeString(g, "New World Prod.", new Font("Helvetica",
                        Font.ITALIC | Font.BOLD,9),
                Color.BLACK,
                10, 715);
    }
}
