package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class Meteorite {
    Texture image;
    int width, height;

    float x, y;
    float x0, y0;
    float vx, vy;
    float time, targetTime;

    boolean isChanged;

    public Meteorite(){
        targetTime = 300;
        isChanged = false;
        respawn();
    }

    void respawn(){
        x = MathUtils.random(0, MyGame.SCREEN_WIDTH);
        y = MathUtils.random(MyGame.SCREEN_HEIGHT, MyGame.SCREEN_HEIGHT * 2);
        x0 = x;
        y0 = y;
        vx = (GameScreen.player.x + GameScreen.player.width / 2 - width / 2 - x0) / targetTime;
        vy = (GameScreen.player.height / 2 - y0) / targetTime;
        time = 0;

        width = height = MathUtils.random(80, GameScreen.player.width);

        image = new Texture("meteorite.png");
    }

    boolean isTouched(float touchedX, float touchedY){
        return (touchedX > x & touchedX < x + width) & (touchedY > y & touchedY < y + height);
    }

    void move() {
        x = x0 + vx * time;
        y = y0 + vy * time;
        time++;

        if (y < -height) respawn();
    }

    boolean isHit(){
        return (y <= GameScreen.player.height) && ((x >= GameScreen.player.x && x <= GameScreen.player.x + GameScreen.player.width) ||
                (x + width >= GameScreen.player.x && x + width <= GameScreen.player.x + GameScreen.player.width));
    }
}
