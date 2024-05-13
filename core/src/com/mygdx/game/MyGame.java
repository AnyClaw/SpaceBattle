package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class MyGame extends Game {
	public static final float SCREEN_WIDTH = 1280, SCREEN_HEIGHT = 720;

	public static SpriteBatch batch;
	public static OrthographicCamera camera;
	public static Vector3 touch;

	MenuScreen menuScreen;
	GameScreen gameScreen;
	SettingsScreen settingsScreen;

	public static float menuSongVolume, gameSongVolume, soundVolume;
	public static boolean isArrowsShow;

	public static String name;

	@Override
	public void create () {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		touch = new Vector3();

		menuSongVolume = 50;
		gameSongVolume = 50;
		soundVolume = 50;

		isArrowsShow = true;

		name = "player";

		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingsScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}