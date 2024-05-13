package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Arrays;

public class GameScreen implements Screen {
    MyGame myGame;

    public static Texture backgroundImage;
    Texture hpBar;

    MyButton pauseButton;
    Texture pauseImage;
    MyButton leftButton;
    Texture leftImage;
    MyButton rightButton;
    Texture rightImage;

    MyButton continueButton;
    MyButton settingsButton;
    MyButton restartButton;
    MyButton leaderboardButton;
    MyButton mainMenuButton, mainMenuButton2;
    MyButton gameSongVolumeButton;
    MyButton gameSoundsVolumeButton;
    MyButton isArrowsShowButton;
    MyButton backButton;

    MyButton plusGameSongVolumeButton, plusGameSoundsVolumeButton;
    MyButton minusGameSongVolumeButton, minusGameSoundsVolumeButton;
    Texture plusImage, minusImage;
    MyButton confirmButton, refuteButton;

    public static BitmapFont font;
    BitmapFont settingsFont;
    BitmapFont largeFont;

    Music song;
    Sound exploreSound;
    Sound collisionSound;

    public static Player player;
    Meteorite[] meteorite = new Meteorite[5];
    Asteroid asteroid;

    public static boolean isAsteroidMoving;
    boolean isPaused;
    boolean isGameOver;
    boolean isSettings;
    boolean isLeaderboardOpened;
    boolean isChanged;

    Player[] bot = new Player[8];
    int[] botRecord = new int[] {600, 110, 50, 40, 20, 10, 0, 0};

    public GameScreen(MyGame myGame){
        this.myGame = myGame;
    }

    @Override
    public void show() {
        MyGame.batch = new SpriteBatch();
        backgroundImage = new Texture("background.png");
        hpBar = new Texture("100_.png");

        pauseImage = new Texture("pauseButton.png");
        pauseButton = new MyButton(pauseImage, MyGame.SCREEN_WIDTH - pauseImage.getWidth(), MyGame.SCREEN_HEIGHT - pauseImage.getHeight());
        leftImage = new Texture("leftButton.png");
        leftButton = new MyButton(5, 5, leftImage.getWidth() / 2, leftImage.getHeight() / 2);
        rightImage = new Texture("rightButton.png");
        rightButton = new MyButton(MyGame.SCREEN_WIDTH - rightImage.getWidth() / 2 - 5, 5, rightImage.getWidth() / 2, rightImage.getHeight() / 2);

        font = new BitmapFont();
        fontGenerate();
        settingsFont = new BitmapFont();
        settingsFontGenerate();
        largeFont = new BitmapFont();
        largeFontGenerate();


        continueButton = new MyButton("CONTINUE", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("CONTINUE", font) / 2, MyGame.SCREEN_HEIGHT / 4 * 3, font);
        settingsButton = new MyButton("SETTINGS", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("SETTINGS", font) / 2, continueButton.y - MyButton.textHeight("SETTINGS", font) - 60, font);
        mainMenuButton = new MyButton("MAIN MENU", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("MAIN MENU", font) / 2, settingsButton.y - MyButton.textHeight("MAIN MENU", font) - 60, font);
        mainMenuButton2 = new MyButton("MAIN MENU", mainMenuButton.x, MyButton.textHeight("mainMenuButton.text", font) + 50, font);
        leaderboardButton = new MyButton("LEADERBOARD", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("LEADERBOARD", font) / 2, MyGame.SCREEN_HEIGHT / 2 + MyButton.textHeight("LEADERBOARD", font) / 2, font);
        restartButton = new MyButton("RESTART", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("RESTART", font) / 2, mainMenuButton2.y * 2 + MyButton.textHeight("RESTART", font)*2, font);

        gameSoundsVolumeButton = new MyButton("GAME SOUNDS VOLUME:", 70, MyGame.SCREEN_HEIGHT / 2 + MyButton.textHeight("GAME SOUNDS VOLUME", settingsFont) / 2, settingsFont);
        isArrowsShowButton = new MyButton("SHOW ARROWS:",  70, (MyGame.SCREEN_HEIGHT - gameSoundsVolumeButton.y) / 2 + MyButton.textHeight("MENU", settingsFont)*3, settingsFont);
        backButton = new MyButton("BACK", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("BACK", font) / 2, MyButton.textHeight("BACK", font) + 20, font);
        gameSongVolumeButton = new MyButton("GAME SONG VOLUME:", 70,  MyGame.SCREEN_HEIGHT / 2 + gameSoundsVolumeButton.y / 2 - MyButton.textHeight("MENU", settingsFont) * 2, settingsFont);

        plusImage = new Texture("plus.png");
        minusImage = new Texture("minus.png");

        minusGameSoundsVolumeButton = new MyButton(MyGame.SCREEN_WIDTH / 2 - minusImage.getWidth() / 6, gameSoundsVolumeButton.y - MyButton.textHeight(gameSoundsVolumeButton.text, settingsFont) / 2 - minusImage.getWidth() / 6, plusImage.getWidth() / 3, plusImage.getHeight() / 3);
        plusGameSoundsVolumeButton = new MyButton(minusGameSoundsVolumeButton.x + 390 + plusImage.getWidth() / 3, minusGameSoundsVolumeButton.y, plusImage.getWidth() / 3, plusImage.getHeight() / 3);

        minusGameSongVolumeButton = new MyButton(minusGameSoundsVolumeButton.x,  gameSongVolumeButton.y - MyButton.textHeight(gameSongVolumeButton.text, settingsFont) / 2 - minusImage.getWidth() / 6, minusImage.getWidth() / 3, minusImage.getHeight() / 3);
        plusGameSongVolumeButton = new MyButton(minusGameSongVolumeButton.x + 390 + plusImage.getWidth() / 3, minusGameSongVolumeButton.y, plusImage.getWidth() / 3, plusImage.getHeight() / 3);

        confirmButton = new MyButton(new Texture("confirm.png"), isArrowsShowButton.x + MyButton.textWidth(isArrowsShowButton.text, settingsFont) + 20, isArrowsShowButton.y - MyButton.textHeight(isArrowsShowButton.text, settingsFont) - 10);
        refuteButton = new MyButton(new Texture("refute.png"), confirmButton.x, confirmButton.y);

        song = Gdx.audio.newMusic(Gdx.files.internal("GameSong.mp3"));
        song.setVolume(MyGame.gameSongVolume / 100);
        song.play();
        song.setLooping(true);

        exploreSound = Gdx.audio.newSound(Gdx.files.internal("exploreSound.mp3"));
        collisionSound = Gdx.audio.newSound(Gdx.files.internal("collision.mp3"));

        player = new Player(MyGame.name);
        for (int i = 0; i < 5; i++) {
            meteorite[i] = new Meteorite();
        }
        asteroid = new Asteroid();
        isAsteroidMoving = false;

        isPaused = false;
        isGameOver = false;
        isSettings = false;
        isLeaderboardOpened = false;
        isChanged = false;

        for (int i = 0; i < bot.length; i++) {
            bot[i] = new Player("Noname_" + (i + 1), botRecord[i]);
        }
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()) {
            MyGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            MyGame.camera.unproject(MyGame.touch);

            if (pauseButton.isTouched(MyGame.touch.x, MyGame.touch.y, false) && !isGameOver) {
                isPaused = true;
            }

            if (isPaused){
                if (continueButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)) {
                    isPaused = false;
                }
                if (settingsButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)){
                    isSettings = true;
                }
            }
            if (!isSettings && !isLeaderboardOpened) {
                if (isPaused) {
                    if (mainMenuButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)) {
                        song.stop();
                        myGame.setScreen(myGame.menuScreen);
                    }
                }
                if (isGameOver){
                    if (mainMenuButton2.isTouched(MyGame.touch.x, MyGame.touch.y, true)) {
                        song.stop();
                        myGame.setScreen(myGame.menuScreen);
                    }
                }
            }

            if (isSettings){
                if (backButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)){
                    isSettings = false;
                }

                if (plusGameSongVolumeButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                    if (MyGame.gameSongVolume < 100) {
                        MyGame.gameSongVolume += 10;
                        song.setVolume(MyGame.gameSongVolume / 100);
                    }
                }
                if (minusGameSongVolumeButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                    if (MyGame.gameSongVolume > 0) {
                        MyGame.gameSongVolume -= 10;
                        song.setVolume(MyGame.gameSongVolume / 100);
                    }
                }

                if (plusGameSoundsVolumeButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                    if (MyGame.soundVolume < 100) {
                        MyGame.soundVolume += 10;
                    }
                }
                if (minusGameSoundsVolumeButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                    if (MyGame.soundVolume > 0) {
                        MyGame.soundVolume -= 10;
                    }
                }
                if (confirmButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)){
                    MyGame.isArrowsShow = !MyGame.isArrowsShow;
                }
            }

            if (isLeaderboardOpened){
                if (backButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)){
                    isLeaderboardOpened = false;
                }
            }

            if (isGameOver){
                if (restartButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)){
                    song.stop();
                    myGame.setScreen(myGame.gameScreen);
                }
                if (leaderboardButton.isTouched(MyGame.touch.x, MyGame.touch.y, true)){
                    isLeaderboardOpened = true;
                }
            }

            if(!isPaused && !isGameOver) {
                for (int i = 0; i < 5; i++) {
                    if (meteorite[i].isTouched(MyGame.touch.x, MyGame.touch.y)) {
                        //meteorite[i].image = new Texture("meteoriteExplore.png");
                        exploreSound.play(MyGame.soundVolume / 100);
                        player.score++;
                        meteorite[i].respawn();
                    }
                }
            }
        }

        if(!isPaused && !isGameOver) {
            if (Gdx.input.isTouched()) {
                MyGame.touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                MyGame.camera.unproject(MyGame.touch);
                if (leftButton.isTouched(MyGame.touch.x, MyGame.touch.y, false) || rightButton.isTouched(MyGame.touch.x, MyGame.touch.y, false)) player.move(MyGame.touch.x);
            }

            for (int i = 0; i < 5; i++) {
                if (meteorite[i].isHit()) {
                    meteorite[i].respawn();
                    collisionSound.play(MyGame.soundVolume / 100 * 2);
                    player.hp -= 10;
                    if (player.hp >= 0) hpBar = new Texture(player.hp + "_.png");
                }
            }

            if (player.score != 0 && player.score % 20 == 0) {
                asteroid.respawn();
                isAsteroidMoving = true;
            }
            if (isAsteroidMoving) {
                asteroid.move();
                if (asteroid.isHit()) {
                    asteroid.respawn();
                    collisionSound.play(MyGame.soundVolume / 100 * 3);
                    if (player.hp >= 50) player.hp -= 50;
                    else player.hp = 0;
                    hpBar = new Texture(player.hp + "_.png");
                    isAsteroidMoving = false;
                }
            }
        }

        MyGame.camera.update();
        MyGame.batch.setProjectionMatrix(MyGame.camera.combined);

        ScreenUtils.clear(1, 0, 0, 1);
        MyGame.batch.begin();
        MyGame.batch.draw(backgroundImage, 0, 0, MyGame.SCREEN_WIDTH, MyGame.SCREEN_HEIGHT);

        if (!isPaused && !isGameOver) {
            for (Meteorite value : meteorite) {
                MyGame.batch.draw(value.image, value.x, value.y, value.width, value.height);
            }
        }

        if (!isPaused && !isGameOver) {
            if (player.score < 50) {
                for (int i = 0; i < player.score / 10 + 1; i++) {
                    meteorite[i].move();
                }
            } else {
                for (Meteorite meteorite : meteorite) {
                    if (player.score % 10 == 0 && (player.score / 10) % 2 == 0 && meteorite.targetTime >= 60) {
                        if (!meteorite.isChanged) {
                            if (meteorite.targetTime >= 180) meteorite.targetTime -= 20;
                            else meteorite.targetTime -= 5;
                            meteorite.isChanged = true;
                        }
                    }
                    else meteorite.isChanged = false;
                    meteorite.move();
                }
            }
        }

        if (player.hp == 0) isGameOver = true;

        if (isPaused && !isSettings) {
            font.draw(MyGame.batch, continueButton.text, continueButton.x, continueButton.y);
            font.draw(MyGame.batch, settingsButton.text, settingsButton.x, settingsButton.y);
            font.draw(MyGame.batch, mainMenuButton.text, mainMenuButton.x, mainMenuButton.y);
        }

        if (isGameOver && !isLeaderboardOpened){
            largeFont.draw(MyGame.batch, "GAME OVER", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("GAME OVER", largeFont) / 2, MyGame.SCREEN_HEIGHT - 100);
            font.draw(MyGame.batch, "YOUR SCORE: " + player.score, MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("YOUR SCORE: " + player.score, font) / 2, MyGame.SCREEN_HEIGHT / 2 + MyButton.textHeight("YOUR SCORE: ", font)*4);
            font.draw(MyGame.batch, leaderboardButton.text, leaderboardButton.x, leaderboardButton.y);
            font.draw(MyGame.batch, restartButton.text, restartButton.x, restartButton.y);
            font.draw(MyGame.batch, mainMenuButton2.text, mainMenuButton2.x, mainMenuButton2.y);

            if (!isChanged) {
                saveRecords();
                loadRecords();
                sortRecords();
                isChanged = true;
            }
            //
        }

        if (isLeaderboardOpened){
            largeFont.draw(MyGame.batch, "LEADERBOARD", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("LEADERBOARD", largeFont) / 2, MyGame.SCREEN_HEIGHT - 50);
            font.draw(MyGame.batch, backButton.text, backButton.x, backButton.y);

            for (int i = 0; i < bot.length - 1; i++) {
                font.draw(MyGame.batch, bot[i].name + ": " + bot[i].score, MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth(bot[i].name + ": " + bot[i].score, font) / 2, MyGame.SCREEN_HEIGHT - 150 - MyButton.textHeight("A", font)*i*2);
            }
        }

        if (!isPaused && !isGameOver) {
            MyGame.batch.draw(asteroid.image, asteroid.x, asteroid.y, asteroid.width, asteroid.height);
            MyGame.batch.draw(hpBar, 0, MyGame.SCREEN_HEIGHT - hpBar.getHeight() / 2, hpBar.getWidth() / 2, hpBar.getHeight() / 2);
            font.draw(MyGame.batch, "SCORE: " + player.score, 5, MyGame.SCREEN_HEIGHT - hpBar.getHeight() + 20);
            MyGame.batch.draw(player.image, player.x, player.y, player.width, player.height);
            MyGame.batch.draw(pauseImage, pauseButton.x, pauseButton.y);

            if (MyGame.isArrowsShow) {
                MyGame.batch.draw(leftImage, leftButton.x, leftButton.y, leftButton.width, leftButton.height);
                MyGame.batch.draw(rightImage, rightButton.x, rightButton.y, rightButton.width, rightButton.height);
            }
        }

        if (isSettings){
            largeFont.draw(MyGame.batch, "SETTINGS", MyGame.SCREEN_WIDTH / 2 - MyButton.textWidth("SETTINGS", largeFont) / 2, MyGame.SCREEN_HEIGHT - (MyGame.SCREEN_HEIGHT - gameSoundsVolumeButton.y) / 4 + MyButton.textHeight("SETTINGS", largeFont) / 2);

            settingsFont.draw(MyGame.batch, gameSoundsVolumeButton.text, gameSoundsVolumeButton.x, gameSoundsVolumeButton.y);
            settingsFont.draw(MyGame.batch, isArrowsShowButton.text, isArrowsShowButton.x, isArrowsShowButton.y);
            font.draw(MyGame.batch, backButton.text, backButton.x, backButton.y);
            settingsFont.draw(MyGame.batch, gameSongVolumeButton.text, gameSongVolumeButton.x, gameSongVolumeButton.y);

            MyGame.batch.draw(minusImage, minusGameSoundsVolumeButton.x, minusGameSoundsVolumeButton.y, minusGameSoundsVolumeButton.width, minusGameSoundsVolumeButton.height);
            MyGame.batch.draw(new Texture(Math.round(MyGame.soundVolume) + "_gameSound.png"), minusGameSoundsVolumeButton.x + minusImage.getWidth() / 3 + 10, minusGameSoundsVolumeButton.y + 7, 370, 40);

            MyGame.batch.draw(minusImage, minusGameSongVolumeButton.x, minusGameSongVolumeButton.y, minusGameSongVolumeButton.width, minusGameSongVolumeButton.height);
            MyGame.batch.draw(new Texture(Math.round(MyGame.gameSongVolume) + "_gameSound.png"), minusGameSongVolumeButton.x + minusImage.getWidth() / 3 + 10, minusGameSongVolumeButton.y + 7, 370, 40);

            MyGame.batch.draw(plusImage, plusGameSoundsVolumeButton.x, plusGameSoundsVolumeButton.y, plusGameSoundsVolumeButton.width, plusGameSoundsVolumeButton.height);
            MyGame.batch.draw(plusImage, plusGameSongVolumeButton.x, plusGameSongVolumeButton.y, plusGameSongVolumeButton.width, plusGameSongVolumeButton.height);

            MyGame.batch.draw(MyGame.isArrowsShow ? confirmButton.image : refuteButton.image, confirmButton.x, confirmButton.y, confirmButton.width / 3, confirmButton.height / 3);
        }

        MyGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundImage.dispose();

        for (int i = 0; i < 5; i++) {
            meteorite[i].image.dispose();
        }

        hpBar.dispose();
        pauseImage.dispose();
        leftImage.dispose();
        rightImage.dispose();
        plusImage.dispose();
        minusImage.dispose();
        song.dispose();
        exploreSound.dispose();
        collisionSound.dispose();
        asteroid.image.dispose();
        settingsFont.dispose();
        largeFont.dispose();
    }

    void fontGenerate() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Righteous-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        parameter.color = Color.valueOf("#6563FF");
        font = generator.generateFont(parameter);
    }
    void settingsFontGenerate() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Righteous-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.valueOf("#6563FF");
        settingsFont = generator.generateFont(parameter);
    }
    void largeFontGenerate() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Righteous-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 80;
        parameter.color = Color.valueOf("#6563FF");
        largeFont = generator.generateFont(parameter);
    }

    void saveRecords() {
        Preferences preferences = Gdx.app.getPreferences("SpaceBattleRecords");
        for (int i = 0; i < bot.length; i++) {
            preferences.putString("Bot"+i, bot[i].name);
            preferences.putInteger("score"+i, bot[i].score);
        }
        preferences.flush();
    }
    void loadRecords() {
        Preferences preferences = Gdx.app.getPreferences("SpaceBattleRecords");
        for (int i = 0; i < bot.length; i++) {
            if (preferences.contains("bot" + i)) bot[i].name = preferences.getString("bot" + i, "Noname");
            if (preferences.contains("score" + i)) bot[i].score = preferences.getInteger("score" + i, 0);
        }
    }
    void sortRecords() {
        bot[bot.length - 1].name = player.name;
        bot[bot.length - 1].score = player.score;
        boolean flag = true;

        while (flag) {
            flag = false;
            for (int i = 0; i < bot.length - 1; i++) {
                if (bot[i].score < bot[i + 1].score) {
                    Player a = bot[i];
                    bot[i] = bot[i + 1];
                    bot[i + 1] = a;
                    flag = true;
                }
            }
        }
    }
}