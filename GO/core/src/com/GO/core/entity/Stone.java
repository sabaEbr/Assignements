package com.GO.core.entity;

import com.GO.core.engine.GoEngine;
import com.GO.core.util.Position;

import java.util.ArrayList;

public class Stone {

    private Position position; //Indicates position of token

    // Surrounding Positions
    private ArrayList<Position> neighbors;

    private Player owner; // Owner of the token

    private GoEngine goEngineOwner; // Indicating which game is belongs to

    public Stone(int x, int y, Player player, GoEngine game){
        position = new Position(x, y);

        owner = player;

        goEngineOwner = game;

        neighbors = new ArrayList<>();
        initSurroundingPosition();
    }

    public Stone(Position position, Player player, GoEngine game){
        this(position.getX(), position.getY(), player, game);
    }

    public ArrayList<Position> getNeighbors() {
        return neighbors;
    }

    public Player getOwner() {
        return owner;
    }

    public Position getPosition() {
        return position;
    }

    public int getPositionX(){
        return position.getX();
    }

    public int getPositionY(){
        return position.getY();
    }

    public void initSurroundingPosition(){
        // List Surrounding Positions
        Position[] literalSurrounding = new Position[] {
                new Position(getPositionX() - 1, getPositionY()),
                new Position(getPositionX(), getPositionY() - 1),
                new Position(getPositionX() + 1, getPositionY()),
                new Position(getPositionX(), getPositionY() + 1)
        };

        for(Position position : literalSurrounding){
            if(goEngineOwner.positionValidity(position)){
                neighbors.add(new Position(position));
            }
        }
    }

    public boolean isEqual(Stone stone){
        // Only verifies if positions are the same (instead of == operator)
        return (getPositionX() == stone.getPositionX() && getPositionY() == stone.getPositionY());
    }

    public boolean isFree(){
        for (Position posNext: neighbors){
            if(!goEngineOwner.isOccupied(posNext)){
                return true;
            }
        }
        return false;
    }

    public boolean defend(ArrayList<Stone> verifiedStones){
        verifiedStones.add(this); // Add itself to the verified list

        if (isFree()){
            return true;
        } else {
            for (Position posNext: neighbors){
                if(goEngineOwner.getStone(posNext).getOwner() == owner &&
                        !verifiedStones.contains(goEngineOwner.getStone(posNext))){
                    if(goEngineOwner.getStone(posNext).defend(verifiedStones)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void attack(Position position){
        attack(position.getX(), position.getY());
    }

    public void attack(int x, int y){
        ArrayList<Stone> verifiedStones = new ArrayList<>();

        if(!goEngineOwner.getStone(x, y).defend(verifiedStones)){
            goEngineOwner.cleanUp(verifiedStones);
        }
    }

    public void verifySurrounding(){
        for (Position posNext: neighbors){
            if(goEngineOwner.isOccupied(posNext)){
                if(goEngineOwner.getStone(posNext).getOwner() != owner){
                    attack(posNext);
                }
            }
        }
    }

    public void verifySuicideMove(){
        if (!isFree()) {
            attack(position);
        }
    }

    public boolean capture(Position position, ArrayList <Position> capturedPositions){
        capturedPositions.add(position);
        Stone simulatedStone = new Stone(position, owner, goEngineOwner);

        for(Position simulatedNeighbor : simulatedStone.getNeighbors()){
            if(goEngineOwner.isOccupied(simulatedNeighbor)){
                if(goEngineOwner.getStone(simulatedNeighbor).getOwner() != owner){
                    return false;
                }
            }
            // Deprecated
            if(!goEngineOwner.isOccupied(simulatedNeighbor) &&
                    !Position.listContains(capturedPositions, simulatedNeighbor)){
                if(!capture(simulatedNeighbor, capturedPositions)){
                    return false;
                }
            }
        }
        return true;
    }

    public void verifyPossession(){
        if(isFree()){
            for(Position neighbor : neighbors) {
                if (!goEngineOwner.isOccupied(neighbor)) {
                    ArrayList<Position> capturedPositions = new ArrayList<>();
                    if(capture(neighbor, capturedPositions)){
                        goEngineOwner.capitulate(capturedPositions, owner);
                    }
                }
            }
        }
    }
}
