package com.mccullickgames.ld30.model.objects;

import com.mccullickgames.ld30.model.GameObject;
import com.mccullickgames.ld30.model.GraphicsComponent;
import com.mccullickgames.ld30.model.InputComponent;
import com.mccullickgames.ld30.model.PhysicsComponent;

public class SpaceBridge extends GameObject {
	public int endX, endY, length;
	public float amountBuilt;
	public Planet planet1;
	public Planet planet2;
	
	public SpaceBridge(PhysicsComponent physics, InputComponent input,
			GraphicsComponent graphics) {
		super(physics, input, graphics);
	}

	public void updateBridge() {
		float dx = endX - position.x;
		float dy = endY - position.y;
		length = (int) Math.sqrt(dx * dx + dy * dy);
		rotation = (int) (Math.atan(dy/dx) * 180 / Math.PI);
		if (dx < 0) {
			rotation += 180;
		}
	}
}
