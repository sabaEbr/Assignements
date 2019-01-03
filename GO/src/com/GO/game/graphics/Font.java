package com.GO.game.graphics;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Font {

    private BufferedImage FONTSHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32;
    public int width;
    public int height;
    private int wLetter; //width letter
    private int hLetter; // width letter

    public Font(String file){
        width = TILE_SIZE;
        height = TILE_SIZE;

        System.out.println("Loading: " + file + "...");
        FONTSHEET = loadFont(file);

        wLetter = FONTSHEET.getWidth() / width;
        hLetter = FONTSHEET.getHeight() / height;
        loadSpriteArray();
    }

    public Font(String file, int w, int h){
        width = w;
        height = h;

        System.out.println("Loading: " + file + "...");
        FONTSHEET = loadFont(file);

        wLetter = FONTSHEET.getWidth() / width;
        hLetter = FONTSHEET.getHeight() / height;
        loadSpriteArray();
    }

    public void setSize(int w, int h){
        setWidth(w);
        setHeight(h);
    }

    public void setWidth(int w){
        width = w;
        wLetter = FONTSHEET.getWidth() / w;
    }

    public void setHeight(int h){
        height = h;
        wLetter = FONTSHEET.getHeight() / h;
    }

    public int getWidth(){ return width; }
    public int getHeight(){ return height; }

    private BufferedImage loadFont(String file){
        BufferedImage sprite = null;
        try{
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e){
            System.out.println("Error: could not load file:" + file);
        }

        return sprite;
    }

    public void loadSpriteArray(){
        spriteArray = new BufferedImage[wLetter][hLetter];

        for(int x = 0; x < wLetter; x++){
            for (int y = 0; y < hLetter; y++){
                spriteArray[x][y] = getLetter(x, y);
            }
        }
    }

    public BufferedImage getFontSheet(){ return FONTSHEET;}

    public BufferedImage getLetter(int x, int y){
        return FONTSHEET.getSubimage(x * width, y * height, width, height);
    }

    public BufferedImage getFont(char letter){
        int value = letter - 65;

        int x = value % wLetter;
        int y = value / hLetter;
        //System.out.println((x+", "+ y));
        return getLetter(x, y);
    }
}
