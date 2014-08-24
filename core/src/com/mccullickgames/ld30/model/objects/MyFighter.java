package com.mccullickgames.ld30.model.objects;

import com.badlogic.gdx.math.Vector2;
import com.mccullickgames.ld30.model.GameObject;
import com.mccullickgames.ld30.model.GameSettings;
import com.mccullickgames.ld30.model.GraphicsComponent;
import com.mccullickgames.ld30.model.InputComponent;
import com.mccullickgames.ld30.model.input.MyFighterInput;
import com.mccullickgames.ld30.model.physics.FighterPhysics;

public class MyFighter extends GameObject {
	public Vector2 target, orbitTarget;
	public Boolean inOrbit = true, reachedTarget = false, continuousFiring = false;
	public float orbitTimeOffset;
	
	public EnemyFighter targetFighter;
	public float lastFired;
	public Planet planet;
	
	
	public MyFighter(InputComponent input, GraphicsComponent graphics) {
		super(FighterPhysics.getInstance(), MyFighterInput.getInstance(), graphics);
		orbitTarget = new Vector2();
		target = new Vector2();
		lastFired = 0;
	}
	
	public void setOrbitTarget(float x, float y) {
		target.x = x;
		target.y = y;
		orbitTarget.x = target.x;
	    orbitTarget.y = target.y + GameSettings.FIGHTER_ORBIT_HEIGHT;
	  
	    continuousFiring = false;
	    targetFighter = null;
	    inOrbit = false;	
	}
	public void returnToOrbit() {
		target.x = orbitTarget.x;
		target.y = orbitTarget.y - GameSettings.FIGHTER_ORBIT_HEIGHT;
		velocity.set(orbitTarget.x - position.x, orbitTarget.y - position.y)
			.nor().scl(GameSettings.MY_FIGHTER_VELOCITY);
		continuousFiring = false;
	    targetFighter = null;
	    inOrbit = false;	
	}
	public void startOrbit(float currentTime) {
		inOrbit = true;
		orbitTimeOffset = (float) (currentTime % (Math.PI * 2));
		target.y = orbitTarget.y - GameSettings.FIGHTER_ORBIT_HEIGHT;
	}

	public void takeHealth() {
		this.health -= GameSettings.MY_FIGHTER_HEALTH_LOSS;
	}

	public void setFighterTarget(EnemyFighter enemyFighter) {		
		targetFighter = enemyFighter;
		reachedTarget = false;
		continuousFiring = true;
	}
}

