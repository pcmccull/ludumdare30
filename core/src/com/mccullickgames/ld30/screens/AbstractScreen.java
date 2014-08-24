package com.mccullickgames.ld30.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mccullickgames.ld30.LD30Game;
import com.mccullickgames.ld30.model.GameWorld;

public abstract class AbstractScreen implements Screen {
	protected GameWorld world;
	protected SpriteBatch batch;
	protected LD30Game game;
	public AbstractScreen(LD30Game game, SpriteBatch batch, GameWorld world) {
		this.game = game;
		this.world = world;
		this.batch = batch;
	}
}
