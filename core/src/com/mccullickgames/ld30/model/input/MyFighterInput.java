package com.mccullickgames.ld30.model.input;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.mccullickgames.ld30.Assets;
import com.mccullickgames.ld30.model.GameObject;
import com.mccullickgames.ld30.model.GameSettings;
import com.mccullickgames.ld30.model.GameWorld;
import com.mccullickgames.ld30.model.GraphicsComponent;
import com.mccullickgames.ld30.model.InputComponent;
import com.mccullickgames.ld30.model.PhysicsComponent;
import com.mccullickgames.ld30.model.objects.EnemyFighter;
import com.mccullickgames.ld30.model.objects.MyFighter;

public class MyFighterInput extends InputComponent {
	private static MyFighterInput instance = new MyFighterInput();
	
	private MyFighterInput() {
		
	}
	public static MyFighterInput getInstance() {
		return instance;
	}
	@Override
	public void update(GameObject gameObject, float dt, GameWorld world) {
		final MyFighter fighter = (MyFighter)gameObject;
		
		if (fighter.targetFighter != null && fighter.targetFighter.health <= 0) {
			fighter.returnToOrbit();
		}
		
		//search for fighters getting close
		if (fighter.targetFighter == null) {
			for (EnemyFighter enemyFighter: world.enemyFighters) {
				if (gameObject.position.dst2(enemyFighter.position) < 65000) {
					fighter.setFighterTarget(enemyFighter);
					if (enemyFighter.health <= 0) {
						world.removeEnemyFighter(enemyFighter);
					}
				}
			}
		}
		
		if ((fighter.reachedTarget || fighter.continuousFiring) 
				&& world.timeAccumulator - fighter.lastFired > GameSettings.MY_FIRING_SPEED) {
			
			GameObject bullet = new GameObject(new PhysicsComponent() {
				public void update(GameObject gameObject, float dt, GameWorld world) {
					gameObject.position.x += gameObject.velocity.x * dt;
					gameObject.position.y += gameObject.velocity.y * dt;
					gameObject.rotation = (int) (Math.atan(gameObject.velocity.y / gameObject.velocity.x) * MathUtils.radiansToDegrees);
					
					for (EnemyFighter enemyFighter: world.enemyFighters) {
						if (gameObject.position.dst2(enemyFighter.position) < 25) {
							enemyFighter.takeHealth();
							Assets.createParticleEffect(gameObject.position.x, gameObject.position.y);
							world.removeBullet(gameObject);
							if (enemyFighter.health <= 0) {
								world.removeEnemyFighter(enemyFighter);
								fighter.returnToOrbit();
							}
						}
					}
				}
			}, new InputComponent(), new GraphicsComponent() {
				private Sprite sprite = Assets.getSprite("myBullet");
				
				@Override
				public void update(GameObject object, float dt, SpriteBatch batch) {
					batch.draw(sprite,
							object.position.x - sprite.getWidth() / 2,
							object.position.y - sprite.getHeight() / 2, 
							sprite.getWidth() / 2, sprite.getHeight() / 2,
							sprite.getWidth(), sprite.getHeight(), 
							1, 1, 
							object.rotation);
				}
			});
			//the randomness in the aiming is to compensate for the other aircraft turning
			bullet.velocity.set((float)Math.cos(fighter.rotation * MathUtils.degreesToRadians + MathUtils.random(-0.3f, 0.3f)),
								(float)Math.sin(fighter.rotation * MathUtils.degreesToRadians + MathUtils.random(-0.3f, 0.3f))).nor().scl(GameSettings.BULLET_SPEED);
			bullet.position.set(fighter.position.x, fighter.position.y);
			world.addBullet(bullet);
			fighter.lastFired = world.timeAccumulator;
		}
		
	}
}

