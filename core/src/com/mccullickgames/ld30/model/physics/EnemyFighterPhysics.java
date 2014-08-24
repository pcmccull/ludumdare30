package com.mccullickgames.ld30.model.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mccullickgames.ld30.model.GameObject;
import com.mccullickgames.ld30.model.GameSettings;
import com.mccullickgames.ld30.model.GameWorld;
import com.mccullickgames.ld30.model.PhysicsComponent;
import com.mccullickgames.ld30.model.objects.EnemyFighter;

public class EnemyFighterPhysics extends PhysicsComponent {
	private static EnemyFighterPhysics instance = new EnemyFighterPhysics();
	
	private EnemyFighterPhysics() {
		
	}
	public static EnemyFighterPhysics getInstance() {
		return instance;
	}
	
	@Override
	public void update(GameObject gameObject, float dt, GameWorld world) {
		EnemyFighter fighter = (EnemyFighter)gameObject;
		//separate fighters
		Vector2 moveDir;
		for (EnemyFighter otherFighter: world.enemyFighters) {
			if (otherFighter != fighter
					&& fighter.position.dst2(otherFighter.position) < 600) {
				moveDir = new Vector2(fighter.position.x - otherFighter.position.x, fighter.position.y - otherFighter.position.y).nor().scl(0.5f);
				fighter.position.add(moveDir);
			}
		}
		
		if (!fighter.reachedTarget || fighter.continuousFiring) {			
			if (fighter.continuousFiring ) {
				fighter.target.set(fighter.targetFighter.position.x, fighter.targetFighter.position.y);
			} 
			fighter.velocity.set(fighter.target.x - fighter.position.x, fighter.target.y - fighter.position.y)
						.nor().scl(GameSettings.ENEMY_FIGHTER_VELOCITY);
			
			fighter.position.x += fighter.velocity.x * dt;
			fighter.position.y += fighter.velocity.y * dt;
			
			if (fighter.position.dst2(fighter.target) < 10) {
				fighter.reachedTarget = true;
			}
		}
		
		//point at target
		float dx, dy;
		if (fighter.aimTarget != null) {
			dx = fighter.position.x - fighter.aimTarget.x;
			dy = fighter.position.y - fighter.aimTarget.y;
		} else {
			dx = fighter.position.x - fighter.target.x;
			dy = fighter.position.y - fighter.target.y;
		}
		
		float angle = (float) (Math.atan(dy/dx) - Math.PI);
		if (dx < 0) {
			angle += Math.PI ;
		}
		
		fighter.rotation = (int) (angle * MathUtils.radiansToDegrees);
	}
}
