package com.GO.game;

import javax.swing.JFrame;

public class Window extends JFrame{

    public static int panelWidth = 1280;
    public static int panelHeight = 820;

    public Window(){
        setTitle("Jungle");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(new GamePanel(panelWidth, panelHeight));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
