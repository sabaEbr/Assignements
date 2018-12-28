package com.jungle.game.graphics;

import com.jungle.game.util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Sprite {

    private BufferedImage SPRITESHEET = null;
    private BufferedImage[][] spriteArray;
    private final int TILE_SIZE = 32;
    public int width;
    public int height;
    private int wSprite;
    private int hSprite;

    public Sprite(String file){
        width = TILE_SIZE;
        height = TILE_SIZE;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / width;
        hSprite = SPRITESHEET.getHeight() / height;
        loadSpriteArray();
    }

    public Sprite(String file, int w, int h){
        width = w;
        height = h;

        System.out.println("Loading: " + file + "...");
        SPRITESHEET = loadSprite(file);

        wSprite = SPRITESHEET.getWidth() / width;
        hSprite = SPRITESHEET.getHeight() / height;
        loadSpriteArray();
    }

    public void setSize(int w, int h){
        setWidth(w);
        setHeight(h);
    }

    public void setWidth(int w){
        width = w;
        wSprite = SPRITESHEET.getWidth() / w;
    }

    public void setHeight(int h){
        height = h;
        wSprite = SPRITESHEET.getHeight() / h;
    }

    public int getWidth(){ return width; }
    public int getHeight(){ return height; }

    private BufferedImage loadSprite(String file){
        BufferedImage sprite = null;
        try{
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e){
            System.out.println("Error: could not load file:" + file);
        }

        return sprite;
    }

    public void loadSpriteArray(){
        spriteArray = new BufferedImage[wSprite][hSprite];

        for(int x = 0; x < wSprite; x++){
            for (int y = 0; y < hSprite; y++){
                spriteArray[x][y] = getSprite(x, y);
            }
        }
    }

    public BufferedImage getSpriteSheet(){ return SPRITESHEET;};

    public BufferedImage getSprite(int x, int y){
        return SPRITESHEET.getSubimage(x * width, y * height, width, height);
    }

    public BufferedImage[] getSpriteArray(int i){
        return spriteArray[i];
    }

    public  BufferedImage[][] getSpriteArray2(int i){
        return spriteArray;
    }

    public static void drawArray_image(Graphics2D g, ArrayList<BufferedImage> img, Vector2f pos, int w, int h, int xOffset, int yOffset){
        float x = pos.x;
        float y = pos.y;

        for ( int i = 0; i < img.size(); i++){
            if (img.get(i) != null){
                g.drawImage(img.get(i), (int) x, (int) y, w, h, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }

    public static void drayArray_string(Graphics2D g, Font font, String word, Vector2f pos, int w, int h, int xOffset, int yOffset){
        float x = pos.x;
        float y = pos.y;

        for(int i = 0; i< word.length(); i++) {
            if (word.charAt(i) != 32) {
                g.drawImage(font.getFont(word.charAt(i)), (int) x, (int) y, w, h, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }
}
