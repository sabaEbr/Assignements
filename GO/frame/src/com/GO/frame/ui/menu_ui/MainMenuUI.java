package com.GO.frame.ui.menu_ui;

import com.GO.frame.states.PlayState;
import com.GO.frame.ui.Button;
import com.GO.frame.ui.UI;
import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;

import java.awt.*;

public class MainMenuUI extends UI {

    private MenuStateUI menuStateUI;

    private Button pvpButton;
    private Button pvcButton;
    private Button onlineButton;

    public MainMenuUI(MenuStateUI menuStateUI){
        super();

        this.menuStateUI = menuStateUI;

        pvpButton = new Button(1280/2-80, 150, 160, 40,
                "Player vs Player", 35, 25);
        pvcButton = new Button(1280/2-80, 230, 160, 40,
                "Player vs CPU", 35, 25);
        onlineButton = new Button(1280/2-80, 310, 160, 40,
                "Play Online", 35, 25);

        buttonsList.add(pvpButton);
        buttonsList.add(pvcButton);
        buttonsList.add(onlineButton);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        super.input(mouse, key);

        if (pvpButton.isClicked(mouse)) {
            menuStateUI.setGameModeSelection(PlayState.PvP);
            menuStateUI.gotoBoardMenu();
        } else if (pvcButton.isClicked(mouse)) {
//            menuStateUI.setGameModeSelection(PlayState.PvC);
//            menuStateUI.gotoBoardMenu();
        } else if(onlineButton.isClicked(mouse)){
//            menuStateUI.setGameModeSelection(PlayState.ONL);
//            menuStateUI.gotoBoardMenu();
        }
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
    }
}
