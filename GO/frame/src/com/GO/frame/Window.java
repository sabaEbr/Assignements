package com.GO.frame;

import javax.swing.JFrame;
import java.awt.*;

public class Window extends JFrame{

    static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    public static int panelWidth = 1280;
    public static int panelHeight = 720;
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();

    public Window(){
        setTitle("GO v0.3.0");

        setContentPane(new GamePanel(panelWidth, panelHeight));

        setResizable(false);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
