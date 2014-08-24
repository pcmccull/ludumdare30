package com.mccullickgames.ld30.model;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mccullickgames.ld30.Assets;
import com.mccullickgames.ld30.model.GameSettings.MINERAL;
import com.mccullickgames.ld30.model.objects.EnemyFighter;
import com.mccullickgames.ld30.model.objects.MyFighter;
import com.mccullickgames.ld30.model.objects.Planet;
import com.mccullickgames.ld30.model.objects.SpaceBridge;

public class GameWorld {
	public Array<Planet> planets;
	public Array<MyFighter> myFighters;
	public Array<EnemyFighter> enemyFighters;
	public Array<GameObject> bullets;
	public Array<SpaceBridge> spaceBridges;
	private PhysicsComponent nullPhysics = new PhysicsComponent();
	private InputComponent nullInput = new InputComponent();
	GraphicsComponent enemyFighterGraphic = new GraphicsComponent() {
		private Sprite sprite = Assets.getSprite("enemyFighter");
		private float halfWidth = sprite.getWidth() / 2;
		private float halfHeight = sprite.getHeight() / 2;
		
		@Override
		public void update(GameObject object, float dt, SpriteBatch batch) {
			batch.draw(sprite,
					object.position.x - halfWidth,
					object.position.y - halfHeight, 
					halfWidth, halfHeight,
					sprite.getWidth(), sprite.getHeight(), 
					1, 1, 
					object.rotation);
			batch.draw(healthBar, 
					object.position.x - 6, object.position.y + halfHeight + 10,
					0, 0,
					healthBar.getWidth() * object.health / 100, healthBar.getHeight(),
					1, 1,
					0);
		
		}
	};
	GraphicsComponent fighterGraphic = new GraphicsComponent() {
		private Sprite sprite = Assets.getSprite("myFighter");
		private float halfWidth = sprite.getWidth() / 2;
		private float halfHeight = sprite.getHeight() / 2;
		@Override
		public void update(GameObject object, float dt, SpriteBatch batch) {
			
			batch.draw(sprite,
					object.position.x - halfWidth,
					object.position.y - halfHeight, 
					halfWidth, halfHeight,
					sprite.getWidth(), sprite.getHeight(), 
					1, 1, 
					object.rotation);
			batch.draw(healthBar, 
							object.position.x - 6, object.position.y + halfHeight + 10,
							0, 0,
							healthBar.getWidth() * object.health / 100, healthBar.getHeight(),
							1, 1,
							0);
		
		}
	};
	GraphicsComponent spaceBridgeGraphics = new GraphicsComponent() {
		ShapeRenderer shapeRenderer = new ShapeRenderer(); 
		float time = 0;
		float rtime = 0;
		float offset1 = MathUtils.random(0.5f, 0.8f);
		float offset2 = MathUtils.random(0.5f, 0.8f);
		float offset3 = MathUtils.random(0.5f, 0.8f);
		float offset4 = MathUtils.random(0.5f, 0.8f);
		float offset5 = MathUtils.random(0.5f, 0.8f);
		float offset6 = MathUtils.random(0.5f, 0.8f);
		@Override
		public void update(GameObject object, float dt, SpriteBatch batch) {
			time += dt * 8;
			rtime -= dt * 8;
			
			float amplitude = 4;
			double step = Math.PI/100;
			int nextY;
			Gdx.gl.glEnable(GL20.GL_BLEND);
		    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		    shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.identity();
			shapeRenderer.translate(object.position.x,  object.position.y,  0);
			shapeRenderer.rotate(0, 0, 1, object.rotation);
			SpaceBridge spaceObj = (SpaceBridge) object;
		
			for (int i = 0; i < spaceObj.length * spaceObj.amountBuilt; i++) {					
				nextY = (int) (Math.sin(time/offset1 + i * step * offset2) * amplitude);					
				shapeRenderer.setColor(offset1 - 0.2f, offset1 + 0.4f, offset1,  1 - Math.abs(nextY/amplitude));
				shapeRenderer.line(i - 1, nextY + 1, i, nextY);
				nextY = (int) (Math.sin(time/offset2 + i * step / offset3) * amplitude);
				shapeRenderer.setColor(offset2 - 0.2f, offset2 - 0.1f, offset2, 1 - Math.abs(nextY/amplitude));
				shapeRenderer.line(i - 1, nextY + 1, i, nextY);
				nextY = (int) (Math.sin(time/offset3 + i * step  / offset4) * amplitude);
				shapeRenderer.setColor(offset3 - 0.2f, offset3, offset3, 1 - Math.abs(nextY/amplitude));
				shapeRenderer.line(i - 1, nextY + 1, i, nextY);
				nextY = (int) (Math.sin(rtime/offset4 + i * step / offset5) * amplitude);
				shapeRenderer.setColor(offset4 - 0.4f, offset4 - 0.2f, offset4, 1 - Math.abs(nextY/amplitude));
				shapeRenderer.line(i - 1, nextY + 1, i, nextY);
				nextY = (int) (Math.sin(rtime/offset5 + i * step / offset6) * amplitude);
				shapeRenderer.setColor(offset5 - 0.2f, offset5, offset5, 1 - Math.abs(nextY/amplitude));
				shapeRenderer.line(i - 1, nextY + 1, i, nextY);
				nextY = (int) (Math.sin(rtime/offset6 + i * step / offset1) * amplitude);
				shapeRenderer.setColor(offset6 - 0.2f, offset6 + 0.4f, offset6 - 0.4f, 1 - Math.abs(nextY/amplitude));
				shapeRenderer.line(i - 1, nextY + 1, i, nextY);
				
			}
			shapeRenderer.end();
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	};
	private Sprite factory;
	private Sprite mine;
	private Sprite healthBar;
	private Sprite unconnectedWorld;
	
	public int scandium;
	public int promethium;
	public int unobtanium;
	public float timeAccumulator;
	public float lastWave;
	public int waveCount;
	public float lastMiningTime;
	public Rectangle screenRect;
	
	private String showError;
	private Boolean errorVisible;
	private float errorShowStartTime;
	private Sprite errorSprite;
	ShapeRenderer clockRenderer = new ShapeRenderer(); 
	public GameWorld() {
		
	}
	
	public void startGame() {
		this.unconnectedWorld = Assets.getSprite("unconnectedWorld");
		this.factory = Assets.getSprite("factory");
		this.mine = Assets.getSprite("mine");
		this.healthBar = Assets.getSprite("healthbar");
		createPlanets();
		createMyFighters();
		createEnemyFighters();
		bullets = new Array<GameObject>();
		createSpaceBridges();
		
		timeAccumulator = 0;
		lastWave = 0;
		lastMiningTime = 0;
		waveCount = 1;
		errorVisible = false;
		
		scandium = GameSettings.START_SCANDIUM;
		promethium = GameSettings.START_PROMETHIUM;
		unobtanium = GameSettings.START_UNOBTANIUM;
		screenRect = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	}
	public float getTimeTillNextWave() {
		return Math.max(4, GameSettings.WAVE_TIME/(Math.max(1, waveCount - 6))) - (timeAccumulator - lastWave);
	}
	public void update(float dt, SpriteBatch batch) {
		timeAccumulator += dt;
		
		float mineDt = timeAccumulator - lastMiningTime;
		if (timeAccumulator - lastMiningTime > GameSettings.MINING_TIME) {
			for (Planet planet: planets) {
				if (planet.mineCount > 0) {
					if (planet.mineral.equals(MINERAL.SCANDIUM)) {
						scandium += GameSettings.MINING_AMOUNT * planet.mineCount;
					} else if (planet.mineral.equals(MINERAL.PROMETHIUM)) {
						promethium += GameSettings.MINING_AMOUNT * planet.mineCount;
					} else {
						unobtanium += GameSettings.MINING_AMOUNT * planet.mineCount;
					}
				}
			}
			lastMiningTime = timeAccumulator;
		}
		if (getTimeTillNextWave() <= 0) {
			addEnemyFighters();
			lastWave = timeAccumulator;		
			waveCount++;
		}
		
		for (int i = 0; i < spaceBridges.size; i++) {			
			spaceBridges.get(i).update(this, dt, batch);
		}
		
		batch.begin();
		
		for (Planet planet: planets) {
			planet.update(this, dt, batch);
			if (planet.hasFactory) {
				batch.draw(factory, planet.position.x + GameSettings.FACTORY_OFFSET_X, planet.position.y + GameSettings.FACTORY_OFFSET_Y);
			}
			if (planet.mineCount > 0) {
				batch.draw(mine, planet.position.x + GameSettings.MINE_OFFSET_X, planet.position.y + GameSettings.MINE_OFFSET_Y);
				if (planet.mineCount >= 2) {
					batch.draw(mine, planet.position.x - 60, planet.position.y + 20,
								0, 0, mine.getWidth(), mine.getHeight(), 1, 1, -90);
				}
				if (planet.mineCount >= 3) {
					batch.draw(mine, planet.position.x + 60, planet.position.y - 23,
							0, 0, mine.getWidth(), mine.getHeight(), 1, 1, 90);
				}
			}
			
			if (planet.health < 100) {
				float healthBarOffsetX = 0, healthBarOffsetY = 0;
				
				if (planet.mineCount == 3) {
					healthBarOffsetX = 40;
					healthBarOffsetY = 14;
				} else if (planet.mineCount == 2) {
					healthBarOffsetX = -45;
					healthBarOffsetY = 14;
				} else if (planet.mineCount == 1) {
					healthBarOffsetX = -8;
					healthBarOffsetY = -58;
				} else if (planet.hasFactory) {
					healthBarOffsetX = -8;
					healthBarOffsetY = 58;
				}
				batch.draw(healthBar, planet.position.x + healthBarOffsetX, planet.position.y + healthBarOffsetY, 0, 0, 
						healthBar.getWidth()*planet.health/100, healthBar.getHeight(),
						1, 1, 0);
			}
		}
		
		for (int i = 0; i < myFighters.size; i++) {
			
			myFighters.get(i).update(this, dt, batch);
		}
		
		for (int i = 0; i < enemyFighters.size; i++) {
			
			enemyFighters.get(i).update(this, dt, batch);
		}
		
		for (int i = 0; i < bullets.size; i++) {			
			bullets.get(i).update(this, dt, batch);
			
		}
		
		Iterator<GameObject> bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			GameObject bullet = bulletIterator.next();
			if (!screenRect.contains(bullet.position)) {
				removeBullet(bullet);
			}
		}
		
		Assets.updateParticleEffect(batch, dt);
		
		if (showError != null) {
			errorSprite = Assets.getSprite(showError);
			errorShowStartTime = timeAccumulator;
			showError = null;
			errorVisible = true;
		} else if (errorVisible) {
			if (timeAccumulator - errorShowStartTime > 10) {
				errorVisible = false;
			}
			
			batch.draw(errorSprite, Gdx.graphics.getWidth()/2 - errorSprite.getWidth()/2, 20);
		}
		
		batch.end();
		
		Gdx.gl.glEnable(GL20.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		clockRenderer.begin(ShapeType.Filled);
		clockRenderer.setColor(0.9f, 0.4f, 0.16f, 0.8f);
		clockRenderer.arc(288, Gdx.graphics.getHeight() - 18, 9, 0, 360 * mineDt/GameSettings.MINING_TIME);
		clockRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	private void createPlanets() {
		GraphicsComponent planetGraphics1 = new GraphicsComponent() {
			private Sprite sprite = Assets.getSprite("world");
			
			@Override
			public void update(GameObject object, float dt, SpriteBatch batch) {
				Planet planet = (Planet)object;
				Sprite drawSprite;
				if (planet.connected) {
					drawSprite = sprite;
				} else {
					drawSprite = unconnectedWorld;
				}
				batch.draw(drawSprite,
								object.position.x - sprite.getWidth() / 2,
								object.position.y - sprite.getHeight() / 2, 
								0, 0,
								sprite.getWidth(), sprite.getHeight(), 
								1, 1, 
								object.rotation);
				
			}
		};
		GraphicsComponent planetGraphics2 = new GraphicsComponent() {
			private Sprite sprite = Assets.getSprite("world2");
			
			@Override
			public void update(GameObject object, float dt, SpriteBatch batch) {
				Planet planet = (Planet)object;
				Sprite drawSprite;
				if (planet.connected) {
					drawSprite = sprite;
				} else {
					drawSprite = unconnectedWorld;
				}
				batch.draw(drawSprite,
						object.position.x - sprite.getWidth() / 2,
						object.position.y - sprite.getHeight() / 2, 
						0, 0,
						sprite.getWidth(), sprite.getHeight(), 
						1, 1, 
						object.rotation);
			}
		};
		GraphicsComponent planetGraphics3 = new GraphicsComponent() {
			private Sprite sprite = Assets.getSprite("world3");
			
			@Override
			public void update(GameObject object, float dt, SpriteBatch batch) {
				Planet planet = (Planet)object;
				Sprite drawSprite;
				if (planet.connected) {
					drawSprite = sprite;
				} else {
					drawSprite = unconnectedWorld;
				}
				batch.draw(drawSprite,
						object.position.x - sprite.getWidth() / 2,
						object.position.y - sprite.getHeight() / 2, 
						0, 0,
						sprite.getWidth(), sprite.getHeight(), 
						1, 1, 
						object.rotation);
			}
		};
		planets = new Array<Planet>();
		Planet planet;
		
		int centerY = Gdx.graphics.getHeight() / 2 - 32;
		planet = new Planet(nullPhysics, nullInput, planetGraphics2);
		planet.position.set(100, centerY);
		planet.hasFactory = true;
		planet.mineCount = 1;
		planet.connected = true;
		
		planet.mineral = GameSettings.MINERAL.PROMETHIUM;
		planets.add(planet);
		
		planet = new Planet(nullPhysics, nullInput, planetGraphics1);
		planet.position.set(200, 150);
		planet.mineCount = 1;
		planet.hasFactory = true;
		planet.connected = true;
		planet.connectingPlanet = planets.get(0);
		planet.mineral = GameSettings.MINERAL.SCANDIUM;
		planets.add(planet);
		planets.get(0).connectingPlanet = planets.get(1);
		
		planet = new Planet(nullPhysics, nullInput, planetGraphics3);
		planet.position.set(200, 400);
		planet.connected = true;
		planet.connectingPlanet = planets.get(0);
		planet.mineral = GameSettings.MINERAL.UNOBTAINIUM;
		planets.add(planet);
		
		planet = new Planet(nullPhysics, nullInput, planetGraphics2);
		planet.position.set(400, 400);
		planet.mineral = GameSettings.MINERAL.PROMETHIUM;
		planet.connectingPlanet = planets.get(2);
		planets.add(planet);
		
		planet = new Planet(nullPhysics, nullInput, planetGraphics3);
		planet.position.set(400, 150);
		planet.mineral = GameSettings.MINERAL.UNOBTAINIUM;
		planet.connectingPlanet = planets.get(1);
		planets.add(planet);
		
		planet = new Planet(nullPhysics, nullInput, planetGraphics1);
		planet.position.set(600, centerY);
		planet.mineral = GameSettings.MINERAL.SCANDIUM;
		planet.connectingPlanet = planets.get(3);
		planets.add(planet);
		
		planet = new Planet(nullPhysics, nullInput, planetGraphics1);
		planet.position.set(300, 500);
		planet.mineral = GameSettings.MINERAL.SCANDIUM;
		planet.connectingPlanet = planets.get(3);
		planets.add(planet);
		
		planet = new Planet(nullPhysics, nullInput, planetGraphics2);
		planet.position.set(470, 50);
		planet.mineral = GameSettings.MINERAL.PROMETHIUM;
		planet.connectingPlanet = planets.get(4);
		planets.add(planet);
		
		planet = new Planet(nullPhysics, nullInput, planetGraphics1);
		planet.position.set(570, 150);
		planet.mineral = GameSettings.MINERAL.SCANDIUM;
		planet.connectingPlanet = planets.get(7);
		planets.add(planet);
		
		planet = new Planet(nullPhysics, nullInput, planetGraphics2);
		planet.position.set(530, 470);
		planet.mineral = GameSettings.MINERAL.PROMETHIUM;
		planet.connectingPlanet = planets.get(5);
		planets.add(planet);
	}
	
	private void createMyFighters() {
		myFighters = new Array<MyFighter>();
	}
	private void addFighter(Planet planet) {
		planet.fighterCount ++;
		
		MyFighter myFighter = new MyFighter(nullInput, fighterGraphic);
		myFighter.planet = planet;
		myFighter.position.x = planet.position.x;
		myFighter.position.y = planet.position.y + GameSettings.FIGHTER_ORBIT_HEIGHT;
		myFighter.setOrbitTarget(planet.position.x, planet.position.y);
		
		myFighters.add(myFighter);
	}
	
	private void createEnemyFighters() {
		enemyFighters = new Array<EnemyFighter>();		
	}
	
	public void addEnemyFighters() {
		
		float offScreenRight = Gdx.graphics.getWidth() + 20;
		float screenTop = Gdx.graphics.getHeight();	
		EnemyFighter enemyFighter;
	
		int fighters = GameSettings.MAX_WAVE; 
		if (waveCount < GameSettings.waveFighters.length) {
			fighters = GameSettings.waveFighters[waveCount];
		} else {
			fighters += Math.max(0, Math.ceil((waveCount - GameSettings.waveFighters.length)/2) - (waveCount%10));
		}
		if (waveCount < 50) {
			for (int i = 0; i < fighters; i++) {
				enemyFighter = new EnemyFighter(enemyFighterGraphic);
				enemyFighter.position.x = offScreenRight;
				enemyFighter.position.y = (float) (screenTop * Math.random());		
				enemyFighters.add(enemyFighter);
			}
		} else {
			for (int i = 0; i < fighters/2; i++) {
				enemyFighter = new EnemyFighter(enemyFighterGraphic);
				enemyFighter.position.x = offScreenRight;
				enemyFighter.position.y = (float) (screenTop * Math.random());		
				enemyFighters.add(enemyFighter);
			}
			for (int i = 0; i < fighters/2; i++) {
				enemyFighter = new EnemyFighter(enemyFighterGraphic);
				enemyFighter.position.x = -20;
				enemyFighter.position.y = (float) (screenTop * Math.random());		
				enemyFighters.add(enemyFighter);
			}
		}
	}
	private void createSpaceBridges() {
		spaceBridges = new Array<SpaceBridge>();		
		addSpaceBridge(planets.get(0), planets.get(1));		
		addSpaceBridge(planets.get(0), planets.get(2));
	}
	public void addSpaceBridge(Planet planet1, Planet planet2) {
		SpaceBridge spaceBridge = new SpaceBridge(nullPhysics, nullInput, spaceBridgeGraphics);  
		spaceBridge.position.x = planet1.position.x;
		spaceBridge.position.y = planet1.position.y ;
		spaceBridge.planet1 = planet1;
		spaceBridge.amountBuilt = 1f;
		spaceBridge.endX = (int) (planet2.position.x );
		spaceBridge.endY = (int) (planet2.position.y );
		spaceBridge.planet2 = planet2;
		spaceBridge.updateBridge();
		spaceBridges.add(spaceBridge);
	}
	public void addBullet(GameObject gameObject) {
		bullets.add(gameObject);		
	}
	public void removeFighter(MyFighter myFighter) {
		myFighter.planet.fighterCount--;
		int index = myFighters.indexOf(myFighter, true);
		if (index != -1) {
			myFighters.removeIndex(index);
		}
	}
	
	public void removeEnemyFighter(EnemyFighter enemyFighter) {
		int index = enemyFighters.indexOf(enemyFighter, true);
		if (index != -1) {
			enemyFighters.removeIndex(index);
		}
	}
	
	public void removeBullet(GameObject bullet) {
		int index = bullets.indexOf(bullet, true);
		if (index != -1) {
			bullets.removeIndex(index);
		}
	}

	public void removeSpaceBridge(Planet planet) {
		Iterator<SpaceBridge> iterator = spaceBridges.iterator();
		while (iterator.hasNext()) {
			SpaceBridge bridge = iterator.next();
			if (bridge.planet1 == planet || bridge.planet2 == planet) {
				iterator.remove();
			}
		}
	}

	public void purchaseFighter(Planet planet) {
		if (scandium >= GameSettings.COST_FIGHTER.scandium
			 && promethium >= GameSettings.COST_FIGHTER.promethium) {
			addFighter(planet);
			scandium -= GameSettings.COST_FIGHTER.scandium;
			promethium -= GameSettings.COST_FIGHTER.promethium;
		} else {
			this.showError = "error_needmoreminerals";
		}	
	}
	
	public void purchaseFactory(Planet planet) {
		if (scandium >= GameSettings.COST_FACTORY.scandium
				 && unobtanium >= GameSettings.COST_FACTORY.unobtainium) {
			planet.hasFactory = true;
			scandium -= GameSettings.COST_FACTORY.scandium;
			unobtanium -= GameSettings.COST_FACTORY.unobtainium;
			planet.health = 100;
		} else {
			this.showError = "error_needmoreminerals";
		}
	}

	public void purchaseMine(Planet planet) {
		if (scandium >= GameSettings.COST_MINE.scandium
				 && unobtanium >= GameSettings.COST_MINE.unobtainium) {
			planet.mineCount++;
			scandium -= GameSettings.COST_MINE.scandium;
			unobtanium -= GameSettings.COST_MINE.unobtainium;
			planet.health = 100;
		} else {
			this.showError = "error_needmoreminerals";
		}
	}
	public void purchaseBridge(Planet planet) {
		if (unobtanium >= GameSettings.COST_SPACEBRIDGE.unobtainium) {
			addSpaceBridge(planet.connectingPlanet, planet);
			planet.connected = true;
			unobtanium -= GameSettings.COST_SPACEBRIDGE.unobtainium;
			planet.health = 100;
		} else {
			this.showError = "error_needmoreminerals";
			
		}
	}

	public Planet getPlanet(Vector2 touchPoint) {
		for (Planet planet: planets) {
			Rectangle planetHitbox = new Rectangle(planet.position.x - GameSettings.PLANET_HEIGHT/2 - 20, planet.position.y - GameSettings.PLANET_HEIGHT/2 - 20, 
					GameSettings.PLANET_WIDTH + 40 ,GameSettings.PLANET_HEIGHT + 40);
			if (planetHitbox.contains(touchPoint)) {
				return planet;
			}
		}
		return null;
	}


	
}
