package com.GO.frame.util;

public class Vector2f {

    public float x;
    public float y;

    // Used mainly for tile positions
    public static float worldX;
    public static float worldY;

    public Vector2f(){
        x = 0;
        y = 0;
    }

    public Vector2f(Vector2f vec){
        new Vector2f(vec.x, vec.y);
    }

    public Vector2f(float X, float Y){
        x = X;
        y = Y;
    }

    public void addX(float f){
        x += f;
    }
    public void addY(float f){
        y += f;
    }
    public void setX(float f){
        x = f;
    }
    public void setY(float f){
        y = f;
    }

    public void setVector(Vector2f vec){
        x = vec.x;
        y = vec.y;
    }

    public void setVector(float X, float Y){
        x = X;
        y = Y;
    }

    public static void setWorldVar(float X, float Y){
        worldX = X;
        worldY = Y;
    }

    public Vector2f getWorldVar(){
        return new Vector2f(x - worldX, y - worldY);
    }

    @Override
    public String toString(){
        return x + ", " + y;
    }
}
