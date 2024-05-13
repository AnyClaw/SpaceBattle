package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {
    MyGame myGame;

    public static Texture menuBackground;

    public static BitmapFont menuFont;

    public static Texture buttonBG;
    MyButton playButton;
    MyButton settingsButton;
    MyButton exitButton;

    public static Music song;
    public static boolean isSongPlaying;

    public MenuScreen(MyGame myGame){
        this.myGame = myGame;
    }

    @Override
    public void show() {
        menuBackground = new Texture("menuBackground.png");

        menuFont = new BitmapFont();
        fontGenerate();

        buttonBG = new Texture("buttonBG.png");
        playButton = new MyButton("PLAY", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("PLAY", menuFont) / 2, MyGame.SCREEN_HEIGHT / 5 * 4 + 50, menuFont);
        settingsButton = new MyButton("SETTINGS", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("SETTINGS", menuFont) / 2, MyGame.SCREEN_HEIGHT / 2 + MyButton.textHeight("SETTINGS", menuFont) / 2, menuFont);
        exitButton = new MyButton("EXIT GAME", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("EXIT GAME", menuFont) / 2, MyGame.SCREEN_HEIGHT - playButton.y + MyButton.textHeight("EXIT GAME", menuFont), menuFont);
    }

    @Override
    public void render(float delta) {
        if(!isSongPlaying){
            song = Gdx.audio.newMusic(Gdx.files.internal("menuSong.mp3"));
            song.setVolume(MyGame.menuSongVolume / 100);
            song.play();
            song.setLooping(true);
            isSongPlaying = true;
        }

        if(Gdx.input.justTouched()){
            MyGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            MyGame.camera.unproject(MyGame.touch);

            if (playButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)){
                song.stop();
                myGame.setScreen(myGame.gameScreen);
                isSongPlaying = false;
            }

            if (settingsButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)){
                myGame.setScreen(myGame.settingsScreen);
            }

            if (exitButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)){
                Gdx.app.exit();
            }
        }

        MyGame.camera.update();
        MyGame.batch.setProjectionMatrix(MyGame.camera.combined);

        ScreenUtils.clear(1, 0, 0, 1);
        MyGame.batch.begin();
        MyGame.batch.draw(menuBackground, 0, 0, MyGame.SCREEN_WIDTH, MyGame.SCREEN_HEIGHT);
        MyGame.batch.draw(buttonBG, playButton.x - 35, playButton.y - MyButton.textHeight(playButton.text, menuFont) - 30, MyButton.textWidth(playButton.text, menuFont) + 70, MyButton.textHeight(playButton.text, menuFont) + 60);
        menuFont.draw(MyGame.batch, playButton.text, playButton.x, playButton.y);
        MyGame.batch.draw(buttonBG, settingsButton.x - 55, settingsButton.y - MyButton.textHeight(settingsButton.text, menuFont) - 30, MyButton.textWidth(settingsButton.text, menuFont) + 115, MyButton.textHeight(settingsButton.text, menuFont) + 60);
        menuFont.draw(MyGame.batch, settingsButton.text, settingsButton.x, settingsButton.y);
        MyGame.batch.draw(buttonBG, exitButton.x - 70, exitButton.y - MyButton.textHeight(exitButton.text, menuFont) - 30, MyButton.textWidth(exitButton.text, menuFont) + 145, MyButton.textHeight(exitButton.text, menuFont) + 60);
        menuFont.draw(MyGame.batch, exitButton.text, exitButton.x, exitButton.y);
        MyGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        menuBackground.dispose();
        menuFont.dispose();
        buttonBG.dispose();
        song.dispose();
    }

    void fontGenerate() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Righteous-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.color = Color.valueOf("#067087");
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        menuFont = generator.generateFont(parameter);
    }
}