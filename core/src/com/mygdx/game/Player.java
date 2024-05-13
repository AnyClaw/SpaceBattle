package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Player {
    String name;
    int score;
    int hp;
    float x, y;

    Texture image;
    int width, height;

    public Player(String name){
        image = new Texture("ship.png");
        width = image.getWidth() / 2;
        height = image.getHeight() / 2;

        x = MyGame.SCREEN_WIDTH / 2 - width / 2;
        y = 0;

        hp = 100;
        score = 0;

        this.name = name;
    }

    public Player(String name, int score){
        this.name = name;
        this.score = score;
    }

    void move(float touchX){
        if (touchX >= MyGame.SCREEN_WIDTH / 2 && x < MyGame.SCREEN_WIDTH - width) x += 10;
        else if (touchX < MyGame.SCREEN_WIDTH / 2 && x > 0) x -= 10;
    }
}
