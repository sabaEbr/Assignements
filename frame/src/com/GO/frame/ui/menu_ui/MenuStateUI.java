package com.GO.frame.ui.menu_ui;

import com.GO.frame.states.MenuState;
import com.GO.frame.ui.common_ui.UI;
import com.GO.frame.util.KeyHandler;
import com.GO.frame.util.MouseHandler;
import com.GO.frame.util.Time;

import java.awt.*;
import java.util.ArrayList;

public class MenuStateUI {
    private MenuState menuStateInterface;

    private int mainMenuState = 0;
    private int boardMenuState = 1;
    private int orderMenuState = 2;

    private int gameModeSelection;
    private int boardSelection;

    private ArrayList<UI> menuUIs;
    private int currentMenu;

    public MenuStateUI(MenuState menuStateInterface) {
        this.menuStateInterface = menuStateInterface;

        menuUIs = new ArrayList<>();
        menuUIs.add(new MainMenuUI(this));
        menuUIs.add(new BoardMenuUI(this));
    }

    public void setCurrentMenu(int menu){
        Time.delay(); // Buttons in menu often overlaps each other
        this.currentMenu = menu;
    }

    public void gotoMainMenu(){
        Time.delay(); // Buttons in menu often overlaps each other
        this.currentMenu = mainMenuState;
    }

    public void gotoBoardMenu(){
        Time.delay(); // Buttons in menu often overlaps each other
        this.currentMenu = boardMenuState;
    }

    public void signalGameSelection(){
        Time.delay(); // Buttons in menu often overlaps each other
        menuStateInterface.signalGameSelection(gameModeSelection, boardSelection);
    }

    public void setGameModeSelection(int gameModeSelection) {
        this.gameModeSelection = gameModeSelection;
    }

    public void setBoardSelection(int boardSelection) {
        this.boardSelection = boardSelection;
    }

    public void update() {
        menuUIs.get(currentMenu).update();
    }


    public void input(MouseHandler mouse, KeyHandler key) {
        menuUIs.get(currentMenu).input(mouse, key);
    }


    public void render(Graphics2D g) {
        menuUIs.get(currentMenu).render(g);
    }

}
