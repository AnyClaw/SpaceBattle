package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Asteroid {
    Texture image;

    float x, y;
    int width, height;

    public Asteroid(){
        image = new Texture("asteroid.png");
        width = GameScreen.player.width;
        height = image.getHeight() / 3;
        respawn();
    }

    void respawn(){
        x = GameScreen.player.x;
        y = MyGame.SCREEN_HEIGHT + 20;
    }

    void move(){
        y -= 5;
        if (y < -height) {
            respawn();
            GameScreen.isAsteroidMoving = false;
        }
    }

    boolean isHit(){
        return (y <= GameScreen.player.height) && ((x >= GameScreen.player.x && x <= GameScreen.player.x + GameScreen.player.width) ||
                (x + width >= GameScreen.player.x && x + width <= GameScreen.player.x + GameScreen.player.width));
    }
}
