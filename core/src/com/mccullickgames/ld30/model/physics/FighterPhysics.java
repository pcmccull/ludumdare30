package com.mccullickgames.ld30.model.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mccullickgames.ld30.model.GameObject;
import com.mccullickgames.ld30.model.GameSettings;
import com.mccullickgames.ld30.model.GameWorld;
import com.mccullickgames.ld30.model.PhysicsComponent;
import com.mccullickgames.ld30.model.objects.EnemyFighter;
import com.mccullickgames.ld30.model.objects.MyFighter;

public class FighterPhysics extends PhysicsComponent {
	private static FighterPhysics instance = new FighterPhysics();
	
	private FighterPhysics() {
		
	}
	public static FighterPhysics getInstance() {
		return instance;
	}
	
	@Override
	public void update(GameObject gameObject, float dt, GameWorld world) {
		MyFighter fighter = (MyFighter)gameObject;
		//separate fighters
		Vector2 moveDir;
		Boolean alreadyMoved = false;
		for (MyFighter otherFighter: world.myFighters) {
			if (otherFighter != fighter
					&& fighter.position.dst2(otherFighter.position) < 200) {
				moveDir = new Vector2(fighter.position.x - otherFighter.position.x, fighter.position.y - otherFighter.position.y).nor().scl(0.5f);
				fighter.position.add(moveDir);
				alreadyMoved = true;
			}
		}
		
		for (EnemyFighter otherFighter: world.enemyFighters) {
			if (fighter.position.dst2(otherFighter.position) < 600) {
				moveDir = new Vector2(fighter.position.x - otherFighter.position.x, fighter.position.y - otherFighter.position.y).nor().scl(1);
				fighter.position.add(moveDir);
				otherFighter.position.x -= otherFighter.velocity.x * dt;
				otherFighter.position.y -= otherFighter.velocity.y * dt;
				return;
			}
		}
		if (alreadyMoved) {
			
			return;
		}
	
		if (!fighter.inOrbit || fighter.continuousFiring) {
			if (fighter.continuousFiring ) {
				fighter.target.set(fighter.targetFighter.position.x, fighter.targetFighter.position.y);
				fighter.velocity.set(fighter.target.x - fighter.position.x, fighter.target.y - fighter.position.y)
						.nor().scl(GameSettings.MY_FIGHTER_VELOCITY);
			} else {
				fighter.target.set(fighter.orbitTarget.x, fighter.orbitTarget.y);
				fighter.velocity.set(fighter.target.x - fighter.position.x, fighter.target.y - fighter.position.y)
						.nor().scl(GameSettings.MY_FIGHTER_VELOCITY);
			}
			float angle = fighter.velocity.angle();
			
			fighter.position.x += fighter.velocity.x * dt;
			fighter.position.y += fighter.velocity.y * dt;
			fighter.rotation =  (int) angle; 
			
			if (fighter.position.dst2(fighter.target) < 37) {
				fighter.startOrbit(world.timeAccumulator);
			}
		} else {
			float dx = fighter.position.x - fighter.target.x;
			float dy = fighter.position.y - fighter.target.y;
			float angle = (float) (Math.atan(dy/dx) -  Math.PI / 2);
			if (dx < 0) {
				angle += Math.PI ;
			}
			
			fighter.position.x = (float) (fighter.target.x + Math.sin((world.timeAccumulator - fighter.orbitTimeOffset) * GameSettings.MY_FIGHTER_ORBITAL_VELOCITY) * GameSettings.FIGHTER_ORBIT_HEIGHT);
			fighter.position.y = (float) (fighter.target.y + Math.cos((world.timeAccumulator - fighter.orbitTimeOffset) * GameSettings.MY_FIGHTER_ORBITAL_VELOCITY) * GameSettings.FIGHTER_ORBIT_HEIGHT);
			fighter.rotation = (int) (angle *  MathUtils.radiansToDegrees);
			
			if (fighter.health < 100) {
				fighter.health += 1;
			}
		}
	}
}
