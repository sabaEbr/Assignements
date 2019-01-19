package com.GO.game.entity;

import com.GO.game.states.PlayState;

import java.awt.*;
import java.util.ArrayList;

public class Stone {

    private Position position; //Indicates position of token

    // Surrounding Positions
    private Position posUp;
    private Position posDown;
    private Position posLeft;
    private Position posRight;

    private Entity owner; // Owner of the token

    private PlayState playStateOwner; // Indicating which game is belongs to

    public Stone(int x, int y, Entity player, PlayState game){
        position = new Position(x, y);

        owner = player;

        playStateOwner = game;

        setSurroundingPosition();
    }

    public Entity getOwner() {
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

    public void setSurroundingPosition(){
        posUp = new Position(getPositionX(), getPositionY() - 1);
        posDown = new Position(getPositionX(), getPositionY() + 1);
        posLeft = new Position(getPositionX() - 1, getPositionY());
        posRight = new Position(getPositionX() + 1, getPositionY());
    }

    public boolean isEqual(Stone stone){
        return (getPositionX() == stone.getPositionX() && getPositionY() == stone.getPositionY());
    }

    public boolean isFree(){

        if(!playStateOwner.isOccupied(posUp) || !playStateOwner.isOccupied(posDown) ||
                !playStateOwner.isOccupied(posLeft) || !playStateOwner.isOccupied(posRight)){
            return true;
        } else {
            return false;
        }
    }

    public boolean defend(ArrayList<Stone> verifiedStones){
        verifiedStones.add(this); // Add itself to the verified list

        if (isFree()){
            return true;
        } else {

            if(playStateOwner.getStone(posUp).getOwner() == owner &&
                    !verifiedStones.contains(playStateOwner.getStone(posUp))){
                if(playStateOwner.getStone(posUp).defend(verifiedStones)){
                    return true;
                }
            }

            if(playStateOwner.getStone(posLeft).getOwner() == owner &&
                    !verifiedStones.contains(playStateOwner.getStone(posLeft))){
                if(playStateOwner.getStone(posLeft).defend(verifiedStones)){
                    return true;
                }
            }

            if(playStateOwner.getStone(posDown).getOwner() == owner &&
                    !verifiedStones.contains(playStateOwner.getStone(posDown))){
                if(playStateOwner.getStone(posDown).defend(verifiedStones)){
                    return true;
                }
            }

            if(playStateOwner.getStone(posRight).getOwner() == owner &&
                    !verifiedStones.contains(playStateOwner.getStone(posRight))){
                if(playStateOwner.getStone(posRight).defend(verifiedStones)){
                    return true;
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

        if(!playStateOwner.getStone(x, y).defend(verifiedStones)){
            playStateOwner.cleanUp(verifiedStones);
        }
    }

    public void verifySurrounding(){

        if(playStateOwner.isOccupied(posUp)){
            if(playStateOwner.getStone(posUp).getOwner() != owner){
                attack(posUp);
            }
        }

        if(playStateOwner.isOccupied(posDown)){
            if(playStateOwner.getStone(posDown).getOwner() != owner){
                attack(posDown);
            }
        }

        if(playStateOwner.isOccupied(posLeft)){
            if(playStateOwner.getStone(posLeft).getOwner() != owner){
                attack(posLeft);
            }
        }

        if(playStateOwner.isOccupied(posRight)){
            if(playStateOwner.getStone(posRight).getOwner() != owner){
                attack(posRight);
            }
        }
    }

    public void render(Graphics2D g){
        g.setColor(owner.getColor());
        g.fillOval(PlayState.leftTokenExtremity + position.getX() * PlayState.tokenWidth,
                PlayState.topTokenExtremity + position.getY() * PlayState.tokenHeight,
                PlayState.tokenWidth, PlayState.tokenHeight);
    }
}
