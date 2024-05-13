package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

public class SettingsScreen implements Screen {
    MyGame myGame;

    BitmapFont settingsFont;
    BitmapFont volumeFont;

    MyButton backButton;
    MyButton nickNameButton;
    MyButton menuSongVolumeButton;
    MyButton gameSongVolumeButton;
    MyButton nameField;

    MyButton plusMenuButton, minusMenuButton;
    MyButton plusGameButton, minusGameButton;
    Texture plusImage;
    Texture minusImage;

    SettingsScreen(MyGame myGame){
        this.myGame = myGame;
    }

    @Override
    public void show() {
        settingsFont = new BitmapFont();
        settingsFontGenerate();
        volumeFont = new BitmapFont();
        volumeFontGenerate();

        backButton = new MyButton("BACK", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("BACK", settingsFont) / 2, MyButton.textHeight("BACK", settingsFont) + 30, settingsFont);
        menuSongVolumeButton = new MyButton("MENU SONG VOLUME:", 70, (MyGame.SCREEN_HEIGHT - backButton.y) / 2 + MyButton.textHeight("MENU", volumeFont)*3, volumeFont);
        gameSongVolumeButton = new MyButton("GAME SONG VOLUME:", 70, (MyGame.SCREEN_HEIGHT - menuSongVolumeButton.y) / 2 + MyButton.textHeight("MENU", volumeFont)*3, volumeFont);
        nickNameButton = new MyButton("NICKNAME:", 70, MyGame.SCREEN_HEIGHT / 2 + menuSongVolumeButton.y / 2 + MyButton.textHeight("MENU", settingsFont), settingsFont);
        nameField = new MyButton( MyGame.SCREEN_WIDTH / 2, nickNameButton.y - MyButton.textHeight(nickNameButton.text, settingsFont) - 25, MyButton.textWidth(nickNameButton.text, settingsFont) + 100, MyButton.textHeight(nickNameButton.text, settingsFont) + 50);

        plusImage = new Texture("plus.png");
        plusMenuButton = new MyButton(menuSongVolumeButton.x + MyButton.textWidth(menuSongVolumeButton.text, volumeFont) + 725, menuSongVolumeButton.y - MyButton.textHeight(menuSongVolumeButton.text, volumeFont) / 2 - plusImage.getWidth() / 4, plusImage.getWidth() / 2, plusImage.getHeight() / 2);
        plusGameButton = new MyButton(plusMenuButton.x, gameSongVolumeButton.y - MyButton.textHeight(gameSongVolumeButton.text, volumeFont) / 2 - plusImage.getWidth() / 4, plusImage.getWidth() / 2, plusImage.getHeight() / 2);

        minusImage = new Texture("minus.png");
        minusMenuButton = new MyButton(menuSongVolumeButton.x + MyButton.textWidth(menuSongVolumeButton.text, volumeFont) + 70, menuSongVolumeButton.y - MyButton.textHeight(menuSongVolumeButton.text, volumeFont) / 2 - minusImage.getWidth() / 4, minusImage.getWidth() / 2, minusImage.getHeight() / 2);
        minusGameButton = new MyButton(gameSongVolumeButton.x + MyButton.textWidth(gameSongVolumeButton.text, volumeFont) + 70, gameSongVolumeButton.y - MyButton.textHeight(gameSongVolumeButton.text, volumeFont) / 2 - minusImage.getWidth() / 4, minusImage.getWidth() / 2, minusImage.getHeight() / 2);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            MyGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            MyGame.camera.unproject(MyGame.touch);

            if (backButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)){
                myGame.setScreen(myGame.menuScreen);
            }

            if (plusMenuButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                if (MyGame.menuSongVolume < 100) {
                    MyGame.menuSongVolume += 10;
                    MenuScreen.song.setVolume(MyGame.menuSongVolume / 100);
                }
            }
            if (plusGameButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                if (MyGame.gameSongVolume < 100) MyGame.gameSongVolume += 10;
            }
            if (minusMenuButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                if (MyGame.menuSongVolume > 0) {
                    MyGame.menuSongVolume -= 10;
                    MenuScreen.song.setVolume(MyGame.menuSongVolume / 100);
                }
            }
            if(minusGameButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                if (MyGame.gameSongVolume > 0) MyGame.gameSongVolume -= 10;
            }

            if (nameField.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String text) {
                        MyGame.name = text;
                    }

                    @Override
                    public void canceled() {

                    }
                }, "ENTER YOUR NICKNAME", "", "player");
            }
        }

        MyGame.camera.update();
        MyGame.batch.setProjectionMatrix(MyGame.camera.combined);

        ScreenUtils.clear(1, 0, 0, 1);
        MyGame.batch.begin();
        MyGame.batch.draw(MenuScreen.menuBackground, 0, 0, MyGame.SCREEN_WIDTH, MyGame.SCREEN_HEIGHT);
        MyGame.batch.draw(MenuScreen.buttonBG, backButton.x - 20, backButton.y - MyButton.textHeight(backButton.text, settingsFont) - 25, MyButton.textWidth(backButton.text, settingsFont) + 45, MyButton.textHeight(backButton.text, settingsFont) + 50);
        settingsFont.draw(MyGame.batch, backButton.text, backButton.x, backButton.y);
        MyGame.batch.draw(MenuScreen.buttonBG, menuSongVolumeButton.x - 50, menuSongVolumeButton.y - MyButton.textHeight(menuSongVolumeButton.text, volumeFont) - 25, MyButton.textWidth(menuSongVolumeButton.text, volumeFont) + 100, MyButton.textHeight(menuSongVolumeButton.text, volumeFont) + 50);
        volumeFont.draw(MyGame.batch, menuSongVolumeButton.text, menuSongVolumeButton.x, menuSongVolumeButton.y);
        MyGame.batch.draw(MenuScreen.buttonBG, gameSongVolumeButton.x - 50, gameSongVolumeButton.y - MyButton.textHeight(gameSongVolumeButton.text, volumeFont) - 25, MyButton.textWidth(gameSongVolumeButton.text, volumeFont) + 100, MyButton.textHeight(gameSongVolumeButton.text, volumeFont) + 50);
        volumeFont.draw(MyGame.batch, gameSongVolumeButton.text, gameSongVolumeButton.x, gameSongVolumeButton.y);
        MyGame.batch.draw(MenuScreen.buttonBG, nickNameButton.x - 50, nickNameButton.y - MyButton.textHeight(nickNameButton.text, settingsFont) - 25, MyButton.textWidth(nickNameButton.text, settingsFont) + 100, MyButton.textHeight(nickNameButton.text, settingsFont) + 50);
        settingsFont.draw(MyGame.batch, nickNameButton.text, nickNameButton.x, nickNameButton.y);

        MyGame.batch.draw(minusImage, minusMenuButton.x, minusMenuButton.y, minusMenuButton.width, minusMenuButton.height);
        MyGame.batch.draw(minusImage, minusGameButton.x, minusGameButton.y, minusGameButton.width, minusGameButton.height);
        MyGame.batch.draw(new Texture(Math.round(MyGame.menuSongVolume) + "_sound.png"), menuSongVolumeButton.x + MyButton.textWidth(menuSongVolumeButton.text, volumeFont) + 80 + plusImage.getWidth() / 2, menuSongVolumeButton.y - MyButton.textHeight(menuSongVolumeButton.text, volumeFont) / 2 - 30);
        MyGame.batch.draw(new Texture(Math.round(MyGame.gameSongVolume) + "_sound.png"), gameSongVolumeButton.x + MyButton.textWidth(gameSongVolumeButton.text, volumeFont) + 80 + plusImage.getWidth() / 2, gameSongVolumeButton.y - MyButton.textHeight(gameSongVolumeButton.text, volumeFont) / 2 - 30);
        MyGame.batch.draw(plusImage, plusMenuButton.x, plusMenuButton.y, plusMenuButton.width, plusMenuButton.height);
        MyGame.batch.draw(plusImage, plusGameButton.x, plusGameButton.y, plusGameButton.width, plusGameButton.height);

        MyGame.batch.draw(MenuScreen.buttonBG, nameField.x, nameField.y, nameField.width, nameField.height);
        settingsFont.draw(MyGame.batch, MyGame.name, nameField.x + nameField.width / 2 - MyButton.textWidth(MyGame.name, settingsFont) / 2, nameField.y + MyButton.textHeight("A", settingsFont) * 2 - 5);
        MyGame.batch.end();
        /*
        Gdx.input.getTextInput(new Input.TextInputListener() {
            @Override
            public void input(String text) {
                MyGame.name = text;
            }

            @Override
            public void canceled() {

            }
        }, "ENTER YOUR NICKNAME", "", "player");
        */
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
        settingsFont.dispose();
        volumeFont.dispose();
        plusImage.dispose();
        minusImage.dispose();
    }

    void settingsFontGenerate() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Righteous-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.valueOf("#067087");
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        settingsFont = generator.generateFont(parameter);
    }
    void volumeFontGenerate(){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Righteous-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        parameter.color = Color.valueOf("#067087");
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 5;
        volumeFont = generator.generateFont(parameter);
    }
}