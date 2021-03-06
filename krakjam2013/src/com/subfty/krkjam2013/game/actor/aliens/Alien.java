package com.subfty.krkjam2013.game.actor.aliens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.game.GameScreen;
import com.subfty.krkjam2013.game.actor.Bullet;
import com.subfty.krkjam2013.game.actor.Collider;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.game.actor.buildings.Building;
import com.subfty.krkjam2013.util.Art;

public class Alien extends Collider {
	public enum ALIEN_TYPE{
		
		REGULAR(150.0f ,200.0f, "alien1", 5, 80, 80, 4, 40),
		EXPLODING(300.0f ,350.0f, "alien2", 2, 80, 80, 1, 20),
		SHOOTER(50.0f ,90.0f, "alien3", 5, 80, 80, 2, 20);
		
		public final float MIN_SPEED;
		public final float MAX_SPEED;
		public final String SPRITE;
		public final int MAX_LIFE;
		public final float WIDTH;
		public final float HEIGHT;
		public final float RADIUS;
		
		public final TextureRegion sides[];
		
		private ALIEN_TYPE(float MIN_SPEED, float MAX_SPEED, String SPRITE, int MAX_LIFE, 
				float WIDTH, float HEIGHT, int sidesCount, float RADIUS) {
			this.MIN_SPEED = MIN_SPEED;
			this.MAX_SPEED = MAX_SPEED;
			this.SPRITE = SPRITE;
			this.MAX_LIFE = MAX_LIFE;
			this.WIDTH = WIDTH;
			this.HEIGHT = HEIGHT;
			this.RADIUS = RADIUS;
			
			sides = new TextureRegion[sidesCount];
		}
	}
	
	public final float MAX_RESTING_TIME = 5.0f;
	public final float MIN_RESTING_TIME = 4.0f;
	public float restingTime = 1;
	
	public final float MAX_WALKING_TIME = 5.0f;
	public final float MIN_WALKING_TIME = 4.0f;
	public float walkingTime;
	
	public float minFollowTime;
	
	public float explodingNextChangeAngleTime = 1;
	public final float MAX_EXPLODING_ANGLE_TIME = 0.3f;
	public final float MIN_EXPLODING_ANGLE_TIME = 0.1f;
	
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
	
	private float nextShoot = 4;
	public final float MAX_NEXT_SHOOT = 1;
	public final float MIN_NEXT_SHOOT = 0.6f;
	
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
		
		radius = type.RADIUS;
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
			
			if(type == ALIEN_TYPE.SHOOTER) {
				minFollowTime = 10.0f;
				state = STATE_FOLLOW_PLAYER;
				
				nextShoot -= delta;
				if(nextShoot <= 0) {
					float k = Krakjam.rand.nextFloat();
					nextShoot = MIN_NEXT_SHOOT*k + MAX_NEXT_SHOOT*(1-k);
					if(tmp.len() < 300) {
						tmp.nor();
						Bullet bullet = p.obtainBullet();
						final float bulletSpeed = 400;
						bullet.init(tmp.x*bulletSpeed, tmp.y*bulletSpeed, x, y, 0, 0, null);
						bullet.antyPlayer = true;
					}
				}
			}
			
			// Atakuj budynki
			if(state != STATE_FOLLOW_PLAYER && state != STATE_ATTACK_BUILDING) {
				Array<Building> buildings = Krakjam.gameScreen.background.getBuildings();
				float minBuildingDistance = 100000;
				
				for(int i=0; i<buildings.size; i++) {
					Building b = buildings.get(i);
					
					float cx = b.x + b.width/2.0f;
					float cy = b.y + b.height/2.0f;
					
					float dist = Vector2.tmp.set(x, y).sub(cx, cy).len();
					
					if(dist < minBuildingDistance && b.getHealth() > 0) {
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
				tmp.set(p.x, p.y)
				   .sub(x, y);
				if(tmp.len() < 400 || minFollowTime > 0) {
					state = STATE_FOLLOW_PLAYER;
					tmp.nor().mul(delta*speed);
					dx += tmp.x;
					dy += tmp.y;
				} else {
					state = STATE_RESTING;
				}
			} else if(state == STATE_ATTACK_BUILDING) {
				if(attackedBuilding != null && attackedBuilding.getHealth() > 0) {
					float 	cx = attackedBuilding.x + attackedBuilding.width, 
							cy = attackedBuilding.y + attackedBuilding.height;
					tmp.set(cx, cy).sub(x, y);
					tmp.nor().mul(delta*speed);
					dx += tmp.x;
					dy += tmp.y;
				} else {
					setState(STATE_RESTING);
				}
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
				if(type == ALIEN_TYPE.REGULAR) {
					velAngle += (Krakjam.rand.nextFloat()-0.5f)*2.0f * delta * 6;
				} else if(type == ALIEN_TYPE.EXPLODING) {
					explodingNextChangeAngleTime -= delta;
					if(explodingNextChangeAngleTime <= 0) {
						float k = Krakjam.rand.nextFloat();
						explodingNextChangeAngleTime = 	MIN_EXPLODING_ANGLE_TIME*k + 
														MAX_EXPLODING_ANGLE_TIME*(1-k);
						velAngle = (Krakjam.rand.nextFloat()-0.5f)*2.0f * 50;
					}
				}
				walkingTime -= delta;
				if(walkingTime <= 0) {
					if(type != ALIEN_TYPE.EXPLODING)
						setState(STATE_RESTING);
					else
						setState(STATE_WALKING);
				}
			}
		}
		
		if(Vector2.tmp.set(x, y).sub(lastChangeSpriteX, lastChangeSpriteY).len2() > 10) {
			boolean spriteChanged = false;
			float adx = Math.abs(lastdx);
			float ady = Math.abs(lastdy);
			
			if(type == ALIEN_TYPE.REGULAR) {
				if(adx > ady) {
					if(lastdx < -0.1f) {
						sprite.setRegion(type.sides[2]);
						spriteChanged = true;
					} else if(lastdx > 0.1f) {
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
			} else if(type == ALIEN_TYPE.SHOOTER) {
					if(lastdx < -0.1f) {
						sprite.setRegion(type.sides[0]);
						spriteChanged = true;
					} else if(lastdx > 0.1f) {
						sprite.setRegion(type.sides[1]);
						spriteChanged = true;
					} 
			} else {
				sprite.setRegion(type.sides[0]);
			}
			
			if(spriteChanged) {
				lastChangeSpriteX = x;
				lastChangeSpriteY = y;
			}
		}
		
		if(type == ALIEN_TYPE.EXPLODING) {
			Player p = Krakjam.gameScreen.player;
			if(Vector2.tmp.set(x, y).sub(p.x, p.y).len() < 100) {
				this.visible = false;
				
				Krakjam.art.explosion.play();
				
				PooledEffect effect = Krakjam.bombEffectPool.obtain();
				effect.setPosition(x, y+height/2.0f);
				Krakjam.effects.add(effect);
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
	
	public static float lastSound = 0;
	
	public void onTouchBuilding(Building building) {
		building.damage(Gdx.graphics.getDeltaTime());
		if(GameScreen.timer - lastSound > 0.5f) {
			Krakjam.art.hitBuilding.play();
			lastSound = GameScreen.timer;
		}
	}
	
	@Override
	public Actor hit(float x, float y) {
		return null;
	}
}
