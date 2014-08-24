package com.mccullickgames.ld30;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;

public class Assets {
	public static Map<String, Sprite> images = new HashMap<String, Sprite>();
	public static TextureAtlas spriteSheet;	
	public static SpritePool spritePool;
	public static ParticleEffectPool bombEffectPool;
	public static Array<PooledEffect> effects = new Array<PooledEffect>();
	public static BitmapFont numberFont;
	
	public static void load() {	
		spriteSheet = new TextureAtlas(Gdx.files.internal("connectedWords.pack"));
		for (AtlasRegion region: spriteSheet.getRegions()) {	
			images.put(region.name, spriteSheet.createSprite(region.name));
		}
		
		spritePool = new SpritePool();
		
		numberFont = new BitmapFont(Gdx.files.internal("NumberFont.fnt"), false);
		ParticleEffect bombEffect = new ParticleEffect();
		bombEffect.load(Gdx.files.internal("particles/smallExplosion.p"), spriteSheet, "explosion_");
		bombEffectPool = new ParticleEffectPool(bombEffect, 1, 2);
	}
	public static Sprite getSprite(String spriteId) {
		return spritePool.obtain(spriteId);
	}
	public static void freeSprite(String spriteId, Sprite sprite) {
		spritePool.free(spriteId, sprite);
	}
	public static void dispose() {
		spriteSheet.dispose();
	}
	
	public static void createParticleEffect(float x, float y) {
		// Create effect:
		PooledEffect effect = bombEffectPool.obtain();
		effect.setPosition(x, y);
		effects.add(effect);
	}
	
	public static void updateParticleEffect(SpriteBatch batch, float dt) {
		// Update and draw effects:
		for (int i = effects.size - 1; i >= 0; i--) {
		    PooledEffect effect = effects.get(i);
		    effect.draw(batch, dt);
		    if (effect.isComplete()) {
		        effect.free();
		        effects.removeIndex(i);
		    }
		}
	}
	
	public static void cleanupParticleEffects() {
		// Reset all effects:
		for (int i = effects.size - 1; i >= 0; i--)
		    effects.get(i).free();
		effects.clear();
	}
}
