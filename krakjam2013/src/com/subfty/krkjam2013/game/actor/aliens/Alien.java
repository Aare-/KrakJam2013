package com.subfty.krkjam2013.game.actor.aliens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.game.actor.Collider;
import com.subfty.krkjam2013.game.actor.Player;
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
	
	private ALIEN_TYPE type;
	
	private int life;
	public float speed = 0;
	private Sprite sprite;
	private float velAngle;
	
	public static int STATE_WALKING			= 0;
	public static int STATE_FOLLOW_PLAYER 	= 1;
	
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
			
			if(state == STATE_FOLLOW_PLAYER || tmp.len() < 400) {
				state = STATE_FOLLOW_PLAYER;
				tmp.nor().mul(delta*speed);
				dx += tmp.x;
				dy += tmp.y;
			} else {
				tmp.set((float)Math.cos(velAngle), (float)Math.sin(velAngle)).mul(speed*delta);
				dx += tmp.x;
				dy += tmp.y;
				velAngle += (Krakjam.rand.nextFloat()-0.5f)*2.0f * delta * 6;
			}	
		}
		
		if(dx < -0.1f)
			sprite.setRegion(type.sides[2]);
		else if(dx > 0.1f)
			sprite.setRegion(type.sides[3]);
		else if(dy > 0.1f)
			sprite.setRegion(type.sides[1]);
		else
			sprite.setRegion(type.sides[0]);
		
		//this.x = 
		byway = false;
		
		resolveCollisions();
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
		if(life <= 0) {
			kill();
		}
	}
	
	@Override
	public Actor hit(float x, float y) {
		return null;
	}
}
