package com.mccullickgames.ld30;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Pool;

public class SpritePool {
	private ObjectMap<String, Pool<Sprite>> poolMap; 
	public SpritePool() {
		poolMap = new ObjectMap<String, Pool<Sprite>>();
	}
	public void addSprite(final String spriteId) {
		Pool<Sprite> spritePool = poolMap.get(spriteId);
		if (spritePool == null) {
			spritePool = new Pool<Sprite> () {
				@Override
				protected Sprite newObject() {
					
					return new Sprite(Assets.images.get(spriteId));
				}
			};
			poolMap.put(spriteId, spritePool);
		}
	}
	
	public Sprite obtain(String spriteId) {
		if (poolMap.get(spriteId) == null) {
			addSprite(spriteId);
		}
		return poolMap.get(spriteId).obtain();
	}
	
	public void free(String spriteId, Sprite sprite) {
		poolMap.get(spriteId).free(sprite);
	}
}
