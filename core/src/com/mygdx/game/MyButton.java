package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MyButton {
    float x, y;
    float width, height;
    String text;
    Texture image;

    public MyButton(Texture image, float x, float y) {
        this.x = x;
        this.y = y;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.image = image;
    }

    public MyButton(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public MyButton(String text, float x, float y, BitmapFont font){
        this.text = text;
        this.x = x;
        this.y = y;
        width = textWidth(text, font);
        height = textHeight(text, font);
    }

    boolean isTouched(float touchX, float touchY, boolean isTextButton){
        if (!isTextButton) return (touchX >= x && touchX <= x + width) && (touchY >= y && touchY <= y + height);
        else return (touchX >= x && touchX <= x + width) && (touchY >= y - height && touchY <= y);
    }

    static float textWidth(String text, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return layout.width;
    }
    static float textHeight(String text, BitmapFont font){
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, text);
        return layout.height;
    }
}
