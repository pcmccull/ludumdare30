package com.mccullickgames.ld30.model.objects;

import com.mccullickgames.ld30.model.GameObject;
import com.mccullickgames.ld30.model.GameSettings.MINERAL;
import com.mccullickgames.ld30.model.GameSettings;
import com.mccullickgames.ld30.model.GraphicsComponent;
import com.mccullickgames.ld30.model.InputComponent;
import com.mccullickgames.ld30.model.PhysicsComponent;

public class Planet extends GameObject {
	public int fighterCount = 0;
	public Boolean hasFactory = false;
	public Boolean connected = false;
	public MINERAL mineral;
	public int mineCount;
	public Planet connectingPlanet;
	
	public Planet(PhysicsComponent physics, InputComponent input,
			GraphicsComponent graphics) {
		super(physics, input, graphics);
		mineCount = 0;
		fighterCount = 0;
	}
	
	public void takeHealth() {
		if (this.mineCount == 0 && !this.hasFactory && !this.connected) return;
		this.health -= GameSettings.PLANET_HEALTH_LOSS;
		if (this.health < 0) {
			if (this.mineCount > 0) {
				this.mineCount--;
			} else if (this.hasFactory) {
				this.hasFactory = false;
			} else if (this.connected) {
				this.connected = false;
			}
			this.health = 100;
		}
	}
}
