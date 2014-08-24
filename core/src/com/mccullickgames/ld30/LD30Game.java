package com.mccullickgames.ld30;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mccullickgames.ld30.model.GameWorld;
import com.mccullickgames.ld30.screens.GameOverScreen;
import com.mccullickgames.ld30.screens.InstructionsScreen;
import com.mccullickgames.ld30.screens.MenuScreen;
import com.mccullickgames.ld30.screens.PlayScreen;

public class LD30Game extends Game {
	private SpriteBatch batch;
	private Screen menuScreen;
	private Screen playScreen;
	private Screen gameOverScreen;
	private Screen instructionsScreen;
	private GameWorld world;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		if (Gdx.app.getType().equals(ApplicationType.Android)) {
			float scrw = 800;
			float scrh = 600;

			Camera camera = new OrthographicCamera();
			camera.viewportHeight = scrh;
			camera.viewportWidth = scrw;

			camera.position.set(camera.viewportWidth * .5f,camera.viewportHeight * .5f, 0f);
			camera.update();
			batch.setProjectionMatrix(camera.combined);
		}
		
		Assets.load();
		
		world = new GameWorld();
		
		menuScreen = new MenuScreen(this, batch, world);
		playScreen = new PlayScreen(this, batch, world);
		gameOverScreen = new GameOverScreen(this, batch, world);
		instructionsScreen = new InstructionsScreen(this, batch, world);
		
		showMenuScreen();
	}

	public void showMenuScreen() {
		setScreen(menuScreen);		
	}
	public void showPlayScreen() {
		setScreen(playScreen);		
	}
	public void showGameOverScreen() {
		setScreen(gameOverScreen);		
	}
	public void showInstructionScreen() {
		setScreen(instructionsScreen);		
	}

}
