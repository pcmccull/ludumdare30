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
import com.mccullickgames.ld30.model.objects.Planet;

public class EnemyFighterInput extends InputComponent {
	private static EnemyFighterInput instance = new EnemyFighterInput();
	
	private EnemyFighterInput() {
		
	}
	public static EnemyFighterInput getInstance() {
		return instance;
	}
	@Override
	public void update(GameObject gameObject, float dt, GameWorld world) {
		final EnemyFighter fighter = (EnemyFighter)gameObject;
		if (fighter.targetFighter == null && fighter.aimTarget == null) {
			findNextTarget(fighter, world);
		}
		if (fighter.targetFighter != null && fighter.targetFighter.health <= 0) {
			findNextTarget(fighter, world);
		}
			
		//search for fighters getting close
		if (fighter.targetFighter == null) {
			for (MyFighter myFighter: world.myFighters) {
				if (gameObject.position.dst2(myFighter.position) < 8000) {
					fighter.setFighterTarget(myFighter);
					if (myFighter.health <= 0) {
						world.removeFighter(myFighter);
					}
				}
			}
		}
		
		if ((fighter.reachedTarget || fighter.continuousFiring) 
				&& world.timeAccumulator - fighter.lastFired > GameSettings.ENEMY_FIRING_SPEED) {
			
			GameObject bullet = new GameObject(new PhysicsComponent() {
				public void update(GameObject gameObject, float dt, GameWorld world) {
					gameObject.position.x += gameObject.velocity.x * dt;
					gameObject.position.y += gameObject.velocity.y * dt;
					gameObject.rotation = (int) (Math.atan(gameObject.velocity.y / gameObject.velocity.x) * MathUtils.radiansToDegrees);
					Boolean alreadyHit = false;
					for (MyFighter myFighter: world.myFighters) {
						if (gameObject.position.dst2(myFighter.position) < 20) {
							myFighter.takeHealth();
							Assets.createParticleEffect(gameObject.position.x, gameObject.position.y);
							world.removeBullet(gameObject);
							alreadyHit = true;
							if (myFighter.health <= 0) {
								world.removeFighter(myFighter);
								findNextTarget(fighter, world);
							}
						}
					}
					if (!alreadyHit) {
						for (int i = 0; i < world.planets.size; i++) {
							Planet planet = world.planets.get(i);
							if (gameObject.position.dst2(planet.position) < 300) {
								Boolean connectedBefore = planet.connected;
								planet.takeHealth();
								if (connectedBefore && planet.connected == false) {
									world.removeSpaceBridge(planet);
								}
								Assets.createParticleEffect(gameObject.position.x, gameObject.position.y);
								world.removeBullet(gameObject);
								if (planet.health <= 0 || planet.connected == false) {
									findNextTarget(fighter, world);
								}
							}
						}
					}
				}
			}, new InputComponent(), new GraphicsComponent() {
				private Sprite sprite = Assets.getSprite("enemyBullet");
				
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
	
	private void findNextTarget(EnemyFighter fighter, GameWorld world) {
		float bestEase = Float.MAX_VALUE;
		Planet target = null;
		
		for (Planet planet : world.planets) {
			if (planet.connected) {
				float distance = fighter.position.dst(planet.position);
				float attackEase = distance * GameSettings.ATTACK_EASE_DISTANCE_MODIFIER 
									* (planet.fighterCount + 1) * GameSettings.ATTACK_EASE_DEFENSE_MODIFIER;
				if (attackEase < bestEase) {
					bestEase = attackEase;
					target = planet;
				}
			}
		}
		if (target == null) {
			target = world.planets.get(0);
		}
		fighter.setTarget(target.position.x, target.position.y);
	}
}

