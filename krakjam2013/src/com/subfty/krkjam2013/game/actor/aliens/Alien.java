package com.subfty.krkjam2013.game.actor.aliens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.game.actor.Collider;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.game.actor.buildings.Building;
import com.subfty.krkjam2013.util.Art;

public class Alien extends Collider {
	public enum ALIEN_TYPE{
		REGULAR(50.0f ,50.0f, "alien1", 5, 80, 80);
		
		public final float MIN_SPEED;
		public final float MAX_SPEED;
		public final String SPRITE;
		public final int MAX_LIFE;
		public final float WIDTH;
		public final float HEIGHT;
		
		public final TextureRegion sides[];
		
		private ALIEN_TYPE(float MIN_SPEED, float MAX_SPEED, String SPRITE, int MAX_LIFE, float WIDTH, float HEIGHT) {
			this.MIN_SPEED = MIN_SPEED;
			this.MAX_SPEED = MAX_SPEED;
			this.SPRITE = SPRITE;
			this.MAX_LIFE = MAX_LIFE;
			this.WIDTH = WIDTH;
			this.HEIGHT = HEIGHT;
			
			sides = new TextureRegion[4];
		}
	}
	
	public final float MAX_RESTING_TIME = 5.0f;
	public final float MIN_RESTING_TIME = 4.0f;
	public float restingTime;
	
	public final float MAX_WALKING_TIME = 5.0f;
	public final float MIN_WALKING_TIME = 4.0f;
	public float walkingTime;
	
	public float minFollowTime;
	
	public Building attackedBuilding = null;
	
	private float lastChangeSpriteX=0;
	private float lastChangeSpriteY=0;
	
	public void setState(int alienState) {
		if(alienState == STATE_RESTING) {
			state = STATE_RESTING;
			float k = Krakjam.rand.nextFloat();
			restingTime = MIN_RESTING_TIME*k + MIN_RESTING_TIME*(k-1);
		} else if(alienState == STATE_WALKING) {
			state = STATE_WALKING;
			float k = Krakjam.rand.nextFloat();
			walkingTime = MIN_WALKING_TIME*k + MIN_WALKING_TIME*(k-1);
		} else if(alienState == STATE_FOLLOW_PLAYER) {
			state = STATE_FOLLOW_PLAYER;
			minFollowTime = 4;
		} else { // STATE_ATTACK_BUILDING
			state = STATE_ATTACK_BUILDING;
		}
	}
	
	private ALIEN_TYPE type;
	
	private int life;
	public float speed = 0;
	private Sprite sprite;
	private float velAngle;
	
	public static int STATE_WALKING			= 0;
	public static int STATE_FOLLOW_PLAYER 	= 1;
	public static int STATE_ATTACK_BUILDING = 2;
	public static int STATE_RESTING			= 3;
	
	private int state = STATE_WALKING;
	
	public Alien(){
		sprite = new Sprite();
		
	    //INITING ENUM TEXTURE REGION CACHE
		for(int i=0; i<ALIEN_TYPE.values().length; i++)
			for(int j=0; j<ALIEN_TYPE.values()[i].sides.length; j++)
				ALIEN_TYPE.values()[i].sides[j] = Krakjam.art.atlases[Art.A_AGENTS].findRegion(ALIEN_TYPE.values()[i].SPRITE, j+1);
		
		this.visible = false;
	}
	
    //INITIATING AND KILLING ALIEN
	public void init(ALIEN_TYPE type, float x, float y){
		this.type = type;
		this.visible = true;
		
		velAngle = 0;
		
		speed = (type.MAX_SPEED-type.MIN_SPEED)*Krakjam.rand.nextFloat()+type.MIN_SPEED;
		sprite.setRegion(type.sides[0]);
		sprite.setSize(type.WIDTH, type.HEIGHT);
		
		this.x = x;
		this.y = y;
		
		this.life = type.MAX_LIFE;
		
		super.init();
		
		this.width = type.WIDTH;
		this.height = type.HEIGHT;
		
		radius = 40;
	}
	
	public void kill(){
		this.visible = false;
	}
	
	public boolean isDead() {
		return this.visible;
	}
	
	public void act(float delta) {
		super.act(delta);
		if(!visible || Krakjam.gameScreen.pause) return;
		
		if(byway) {
			Vector2 tmp = Vector2.tmp;
			tmp.set(x, y);
			tmp.sub(bywayX, bywayY);
			if(tmp.len() > 3) {
				tmp.set(bywayX, bywayY);
				tmp.sub(x, y);
				tmp.nor();
				
				dx += tmp.x * delta * speed;
				dy += tmp.y * delta * speed;
			}
		} else {
			Vector2 tmp = Vector2.tmp;
			Player p = Krakjam.gameScreen.player;
			tmp.set(p.x, p.y)
			   .sub(x, y);
			
			if(tmp.len() < 300)
				state = STATE_FOLLOW_PLAYER;
			
			// Atakuj budynki
			if(state != STATE_FOLLOW_PLAYER && state != STATE_ATTACK_BUILDING) {
				
				Array<Building> buildings = Krakjam.gameScreen.background.getBuildings();
				
				float minBuildingDistance = 100000;
				
				for(int i=0; i<buildings.size; i++) {
					Building b = buildings.get(i);
					
					float cx = b.x + b.width/2.0f;
					float cy = b.y + b.height/2.0f;
					
					float dist = Vector2.tmp.set(x, y).sub(cx, cy).len2();
					
					if(dist < minBuildingDistance) {
						minBuildingDistance = dist;
						attackedBuilding = b;
					}
				}
				
				if(minBuildingDistance < 400) {
					setState(STATE_ATTACK_BUILDING);
				}
			}
			
			if(state == STATE_FOLLOW_PLAYER) {
				minFollowTime -= delta;
				if(tmp.len() < 400 || minFollowTime > 0) {
					state = STATE_FOLLOW_PLAYER;
					tmp.nor().mul(delta*speed);
					dx += tmp.x;
					dy += tmp.y;
				} else {
					state = STATE_RESTING;
				}
			} else if(state == STATE_ATTACK_BUILDING) {
				if(attackedBuilding == null) { // building.jestNieZniszczony
					float 	cx = attackedBuilding.x + attackedBuilding.width, 
							cy = attackedBuilding.y + attackedBuilding.height;
					tmp.set(cx, cy).sub(x, y);
					tmp.nor().mul(delta*speed);
					dx += tmp.x;
					dy += tmp.y;
				} else
					setState(STATE_RESTING);
			} else if(state == STATE_RESTING)
			{
				restingTime -= delta;
				if(restingTime <= 0) {
					setState(STATE_WALKING);
				}
			} else { // STATE_WALKING
				tmp.set((float)Math.cos(velAngle), (float)Math.sin(velAngle)).mul(speed*delta);
				dx += tmp.x;
				dy += tmp.y;
				velAngle += (Krakjam.rand.nextFloat()-0.5f)*2.0f * delta * 6;
				
				walkingTime -= delta;
				if(walkingTime <= 0) {
					setState(STATE_RESTING);
				}
			}
		}
		
		if(Vector2.tmp.set(x, y).sub(lastChangeSpriteX, lastChangeSpriteY).len2() > 10) {
			boolean spriteChanged = false;
			float adx = Math.abs(lastdx);
			float ady = Math.abs(lastdy);
			
			if(adx > ady) {
				if(lastdx < -0.1f) {
					sprite.setRegion(type.sides[2]);
					spriteChanged = true;
				} else if(lastdy > 0.1f) {
					sprite.setRegion(type.sides[3]);
					spriteChanged = true;
				} 
			} else {
				if(lastdy > 0.1f) {
					sprite.setRegion(type.sides[1]);
					spriteChanged = true;
				} else if(lastdy < -0.1f){
					sprite.setRegion(type.sides[0]);
					spriteChanged = true;
				}
			}
			
			if(spriteChanged) {
				lastChangeSpriteX = x;
				lastChangeSpriteY = y;
			}
		}
		
		//this.x = 
		byway = false;
		
		resolveCollisions();
	}

	public void attackBuilding(Building building) {
		attackedBuilding = building;
		state = STATE_ATTACK_BUILDING;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		sprite.setPosition(x-sprite.getWidth()/2.0f, y);
		sprite.draw(batch);
		
		//drawDebug(batch);
	}

	public void shoot() {
		life--;
		setState(STATE_FOLLOW_PLAYER);
		minFollowTime = 10.0f;
		if(life <= 0) {
			kill();
		}
	}
	
	@Override
	public Actor hit(float x, float y) {
		return null;
	}
}
