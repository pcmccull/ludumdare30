package com.mccullickgames.ld30.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mccullickgames.ld30.Assets;
import com.mccullickgames.ld30.LD30Game;
import com.mccullickgames.ld30.model.GameSettings;
import com.mccullickgames.ld30.model.GameWorld;
import com.mccullickgames.ld30.model.GameSettings.MINERAL;
import com.mccullickgames.ld30.model.objects.Planet;

public class PlayScreen extends AbstractScreen {
	private Sprite starfield;
	private Sprite resourcesHud;
	private Sprite waveNotice;
	private Sprite mineButton;
	private Sprite bridgeButton;
	private Sprite factoryButton;
	private Planet selectedPlanet;
	private Sprite planetTypeSc;
	private Sprite planetTypePm;
	private Sprite planetTypeUb;
	private Sprite planetTypeUnconnected;
	private Sprite instructionsBuild;
	private Sprite level50;
	
	public PlayScreen(LD30Game game, SpriteBatch batch, GameWorld world) {
		super(game, batch, world);
		
		starfield = Assets.getSprite("starfield");
		resourcesHud = Assets.getSprite("resourcesHud");
		mineButton = Assets.getSprite("button_mine");
		waveNotice = Assets.getSprite("waveNotice");	
		factoryButton = Assets.getSprite("build_factory");
		bridgeButton = Assets.getSprite("button_bridge");
		instructionsBuild = Assets.getSprite("instruction_clickfactory");
		
		planetTypeSc = Assets.getSprite("planet_type_sc");
		planetTypePm = Assets.getSprite("planet_type_pm");
		planetTypeUb = Assets.getSprite("planet_type_ub");
		planetTypeUnconnected = Assets.getSprite("planet_type_no_connection");
		level50 = Assets.getSprite("level50");
	}

	@Override
	public void render(float delta) {
		batch.begin();
		batch.draw(starfield, 0, 0);
		batch.end();
		world.update(delta, batch);
		
		float topOfScreen = Gdx.graphics.getHeight() - resourcesHud.getHeight();
		float topOfText = topOfScreen + 24.5f;
		batch.begin();
		batch.draw(waveNotice, Gdx.graphics.getWidth() - waveNotice.getWidth(), Gdx.graphics.getHeight() - waveNotice.getHeight());
		Assets.numberFont.draw(batch, "" + world.waveCount, Gdx.graphics.getWidth() - 175, topOfText);	
		Assets.numberFont.draw(batch, "" + Math.floor(world.getTimeTillNextWave())/10, Gdx.graphics.getWidth() - 60, topOfText);
		batch.draw(resourcesHud, 0, topOfScreen);
		
		Assets.numberFont.draw(batch, "" + world.scandium, 40, topOfText);	
		Assets.numberFont.draw(batch, "" + world.promethium, 131, topOfText);
		Assets.numberFont.draw(batch, "" + world.unobtanium, 217, topOfText);
		
		if (selectedPlanet != null) {
			if (selectedPlanet.connected) {
				if (!selectedPlanet.hasFactory) {
					batch.draw(factoryButton, selectedPlanet.position.x - factoryButton.getWidth() / 2, selectedPlanet.position.y);
				}
				
				if (selectedPlanet.mineCount < 3) {
					batch.draw(mineButton, selectedPlanet.position.x + mineButton.getWidth() / 2, selectedPlanet.position.y);
				}
				
				if (selectedPlanet.mineral.equals(MINERAL.SCANDIUM)) {
					batch.draw(planetTypeSc, selectedPlanet.position.x - planetTypeSc.getWidth()/3, selectedPlanet.position.y - planetTypeSc.getHeight());
				} else if (selectedPlanet.mineral.equals(MINERAL.PROMETHIUM)) {
					batch.draw(planetTypePm, selectedPlanet.position.x - planetTypePm.getWidth()/3, selectedPlanet.position.y - planetTypePm.getHeight());
				} else if (selectedPlanet.mineral.equals(MINERAL.UNOBTAINIUM)) {
					batch.draw(planetTypeUb, selectedPlanet.position.x - planetTypePm.getWidth()/3, selectedPlanet.position.y - planetTypeUb.getHeight());
				}
			} else if (selectedPlanet.connectingPlanet.connected) {
				batch.draw(bridgeButton, selectedPlanet.position.x - bridgeButton.getWidth() / 2, selectedPlanet.position.y);
			} else {
				batch.draw(planetTypeUnconnected, selectedPlanet.position.x - bridgeButton.getWidth() / 2, selectedPlanet.position.y);
			}			
		} else {
			//if user is just starting and they have not built fighters then show hint
			if (world.waveCount < 5 && world.myFighters.size == 0 && world.enemyFighters.size > 0) {
				
				batch.draw(instructionsBuild, Gdx.graphics.getWidth()/2 - instructionsBuild.getWidth()/2, 20);
			}
		}
		
		if (world.waveCount >= 50 && world.waveCount <= 52) {
			batch.draw(level50, Gdx.graphics.getWidth()/2 - level50.getWidth()/2, 200);
		}
		batch.end();
		
		if (Gdx.input.justTouched()) {
			
			Vector2 touchPoint = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
			Boolean touchHandled = false;
			if (selectedPlanet != null) {
				if (selectedPlanet.connected) {
					if (!selectedPlanet.hasFactory) {
						Rectangle factoryButtonRect = new Rectangle(selectedPlanet.position.x - factoryButton.getWidth() / 2,
																	selectedPlanet.position.y,
																	64, 70);
						if (factoryButtonRect.contains(touchPoint)) {
							world.purchaseFactory(selectedPlanet);
							touchHandled = true;
							selectedPlanet = null;
						}
						
					}
					if (!touchHandled && selectedPlanet.mineCount < 3) {
						Rectangle mineButtonRect = new Rectangle(selectedPlanet.position.x + mineButton.getWidth() / 2,
																selectedPlanet.position.y,
																64, 70);
						if (mineButtonRect.contains(touchPoint)) {
							world.purchaseMine(selectedPlanet);
							touchHandled = true;
							selectedPlanet = null;
						}
					}
				} else if (selectedPlanet.connectingPlanet.connected) {
					Rectangle bridgeButtonRect = new Rectangle(selectedPlanet.position.x - bridgeButton.getWidth() / 2,
															selectedPlanet.position.y,
															64, 70);
					if (bridgeButtonRect.contains(touchPoint)) {
						world.purchaseBridge(selectedPlanet);
						touchHandled = true;
						selectedPlanet = null;
					}
				}
			}
			
			if (!touchHandled) {
				selectedPlanet = world.getPlanet(touchPoint);
				
				if (selectedPlanet != null) {
					if (selectedPlanet.hasFactory) {
						Rectangle factoryRect = new Rectangle(selectedPlanet.position.x - 20,
																selectedPlanet.position.y + GameSettings.PLANET_HEIGHT/2,
																GameSettings.PLANET_WIDTH + 40, 50);
						if (factoryRect.contains(touchPoint)) {
							world.purchaseFighter(selectedPlanet);
							selectedPlanet = null;
							
						}
					}					
				}
			}
		}
		
		int connections = 0;
		for (Planet planet: world.planets) {
			if (planet.connected) {
				connections++;
			}
		}
		
		if (connections == 0) {
			game.showGameOverScreen();
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
