package com.mccullickgames.ld30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mccullickgames.ld30.Assets;
import com.mccullickgames.ld30.LD30Game;
import com.mccullickgames.ld30.model.GameWorld;

public class CreditsScreen extends AbstractScreen {
	private Sprite credits;
	public CreditsScreen(LD30Game game, SpriteBatch batch, GameWorld world) {
		super(game, batch, world);
		credits = Assets.getSprite("credits");
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(credits, 0, 0);
		batch.end();		
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
