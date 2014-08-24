package com.mccullickgames.ld30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mccullickgames.ld30.Assets;
import com.mccullickgames.ld30.LD30Game;
import com.mccullickgames.ld30.model.GameWorld;

public class MenuScreen extends AbstractScreen {
	private Sprite title;
	private Sprite starfield;
	private Sprite instructionsMine;
	private Sprite instructionsBuild;
	private Sprite instructionsConnect;
	private Sprite clickToBegin;
	private Sprite credit;
	public MenuScreen(LD30Game game, SpriteBatch batch, GameWorld world) {
		super(game, batch, world);
		title = Assets.getSprite("title");
		starfield = Assets.getSprite("starfield");
		instructionsMine = Assets.getSprite("instructions_buildMines");
		instructionsBuild = Assets.getSprite("instruction_clickfactory");
		instructionsConnect = Assets.getSprite("instruction_connectWorlds");
		clickToBegin = Assets.getSprite("clickToBegin");
		credit = Assets.getSprite("ludumdare_credit");
	}

	@Override
	public void render(float delta) {
		
		batch.begin();
		batch.draw(starfield, 0, 0);
		batch.draw(title, 
					Gdx.graphics.getWidth()/2 - title.getWidth()/2, 
					Gdx.graphics.getHeight() - title.getHeight() - 40);
		
		float top = Gdx.graphics.getHeight() - 250;
		batch.draw(instructionsBuild, 140, top);
		batch.draw(instructionsMine, instructionsBuild.getWidth() + 160, top);
		batch.draw(instructionsConnect,  instructionsBuild.getWidth() * 2 + 180, top);		
		batch.draw(clickToBegin,  Gdx.graphics.getWidth()/2 - clickToBegin.getWidth()/2, top - 150);
		batch.draw(credit,  Gdx.graphics.getWidth() - credit.getWidth() - 20, 20);
		batch.end();
		
		if (Gdx.input.justTouched()) {
			world.startGame();
			game.showPlayScreen();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
