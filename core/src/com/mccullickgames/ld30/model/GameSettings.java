package com.mccullickgames.ld30.model;



public class GameSettings {
	public static enum MINERAL {
		SCANDIUM, PROMETHIUM, UNOBTAINIUM
	}
	public static final int START_SCANDIUM = 100;
	public static final int START_PROMETHIUM = 100;
	public static final int START_UNOBTANIUM = 0;
	
	public static final ResourceCost COST_FIGHTER = new ResourceCost(10, 20, 0);
	public static final ResourceCost COST_MINE = new ResourceCost(50, 10, 0 );
	public static final ResourceCost COST_FACTORY = new ResourceCost(50, 0, 50);
	public static final ResourceCost COST_SPACEBRIDGE = new ResourceCost(0, 0, 200);
	
	public static final float MINING_TIME = 4.9f;
	public static final int MINING_AMOUNT = 10;
	
	public static final int PLANET_WIDTH = 64;
	public static final int PLANET_HEIGHT = 64;
	
	public static final float MY_FIGHTER_VELOCITY = 120;
	public static final int MY_FIGHTER_HEALTH_LOSS = 8;
	public static final float MY_FIGHTER_ORBITAL_VELOCITY = 2f;
	public static final float MY_FIRING_SPEED = 0.28f;
	
	public static final float ENEMY_FIGHTER_VELOCITY = 80;
	public static final float ENEMY_FIRING_SPEED = 0.4f;
	public static final int ENEMY_FIGHTER_HEALTH_LOSS = 20;
	
	public static final float FIGHTER_ORBIT_HEIGHT = 60;
	
	public static final float BULLET_SPEED = 270;
	
	public static final int FACTORY_OFFSET_X = -18;
	public static final int FACTORY_OFFSET_Y = 28;
	public static final int MINE_OFFSET_X = -21;
	public static final int MINE_OFFSET_Y = -58;

	public static final float WAVE_TIME = 12;

	public static final float ATTACK_EASE_DISTANCE_MODIFIER = 10; //used to calculate which planet should be targeted based on distance, lower is easier to attack
	public static final float ATTACK_EASE_DEFENSE_MODIFIER = 0.3f; //used to calculate which planet should be targeted based on how many fighters are defending it, lower is easier to attack
	
	public static final int PLANET_HEALTH_LOSS = 4;
	
	
	public static final int[] waveFighters = new int[]{
		1, 1, 2, 2 , 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5,5, 5, 6, 5, 6, 7, 7,6,6,7,7, 7, 8, 8,7, 8,8,8, 9,9,9,7,7,9,9,10,8,8,10,10,8,8, 10,10,11,11,11,12,12,12
	};
	public static final int MAX_WAVE = 13;
	
	
	
	
}
