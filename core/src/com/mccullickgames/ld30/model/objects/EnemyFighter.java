package com.mccullickgames.ld30.model.objects;

import com.badlogic.gdx.math.Vector2;
import com.mccullickgames.ld30.model.GameObject;
import com.mccullickgames.ld30.model.GameSettings;
import com.mccullickgames.ld30.model.GraphicsComponent;
import com.mccullickgames.ld30.model.input.EnemyFighterInput;
import com.mccullickgames.ld30.model.physics.EnemyFighterPhysics;

public class EnemyFighter extends GameObject {
	public Vector2 target, aimTarget;
	public GameObject targetFighter;
	public Boolean reachedTarget = false, continuousFiring = false;
	public float lastFired;
	
	
	public EnemyFighter(GraphicsComponent graphics) {
		super(EnemyFighterPhysics.getInstance(), EnemyFighterInput.getInstance(), graphics);		
		target = new Vector2();
		lastFired = 0;
	}
	
	public void setTarget(float x, float y) {
		aimTarget = new Vector2(x, y);
		target.x = x + GameSettings.FIGHTER_ORBIT_HEIGHT * 2;
		target.y = y - 20 ;
		reachedTarget = false;
		continuousFiring = false;
		targetFighter = null;
		velocity.set(target.x - position.x, target.y - position.y)
					.nor().scl(GameSettings.ENEMY_FIGHTER_VELOCITY);
	}

	public void setFighterTarget(MyFighter myFighter) {		
		aimTarget = null;
		targetFighter = myFighter;
		reachedTarget = false;
		continuousFiring = true;
	}

	public void takeHealth() {
		this.health -= GameSettings.ENEMY_FIGHTER_HEALTH_LOSS;
	}
}

