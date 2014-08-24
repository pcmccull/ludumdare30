package com.mccullickgames.ld30.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameObject {
	public int rotation, health;
	public Vector2 position, velocity;
	private PhysicsComponent physics;
	private InputComponent input;
	private GraphicsComponent graphics;
	public GameObject(PhysicsComponent physics, InputComponent input, 
						GraphicsComponent graphics) {
		this.physics = physics;
		this.input = input;
		this.graphics = graphics;		
		this.position = new Vector2(0, 0);
		this.velocity = new Vector2(0, 0);
		this.health = 100;
	}
	public void update(GameWorld world, float dt, SpriteBatch batch) {
		input.update(this, dt, world);
		physics.update(this, dt, world);
		graphics.update(this, dt, batch);
	}
	
}
