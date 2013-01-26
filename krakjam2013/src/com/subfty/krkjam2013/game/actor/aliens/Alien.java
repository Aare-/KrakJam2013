package com.subfty.krkjam2013.game.actor.aliens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.actor.Collider;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.util.Art;

public class Alien extends Collider {
	public enum ALIEN_TYPE{
		REGULAR(20.0f ,30.0f, "alien");
		
		public final float MIN_SPEED;
		public final float MAX_SPEED;
		public final String SPRITE;
		
		private ALIEN_TYPE(float MIN_SPEED, float MAX_SPEED, String SPRITE) {
			this.MIN_SPEED = MIN_SPEED;
			this.MAX_SPEED = MAX_SPEED;
			this.SPRITE = SPRITE;
		}
	}
	
	private ALIEN_TYPE type;
	
	public float speed = 0;
	private Sprite sprite;
	private float velAngle;
	
	public static int STATE_WALKING			= 0;
	public static int STATE_FOLLOW_PLAYER 	= 1;
	
	private int state = STATE_WALKING;
	
	public Alien(){
		sprite = new Sprite();
		
		this.visible = false;
	}
	
    //INITIATING AND KILLING ALIEN
	public void init(ALIEN_TYPE type, float x, float y){
		this.type = type;
		this.visible = true;
		
		velAngle = 0;
		
		speed = (type.MAX_SPEED-type.MIN_SPEED)*Krakjam.rand.nextFloat()+type.MIN_SPEED;
		sprite.setRegion(Krakjam.art.atlases[Art.A_ENTITIES].findRegion(type.SPRITE));
		
		this.x = x;
		this.y = y;
	}
	
	public void kill(){
		this.visible = false;
	}
	
	public void act(float delta) {
		if(!visible) return;
		
		Vector2 tmp = Vector2.tmp;
		Player p = Krakjam.gameScreen.player;
		tmp.set(p.x, p.y)
		   .sub(x, y);
		
		if(state == STATE_FOLLOW_PLAYER || tmp.len() < 400) {
			state = STATE_FOLLOW_PLAYER;
			tmp.nor().mul(delta*speed);
			x += tmp.x;
			y += tmp.y;
		} else {
			tmp.set((float)Math.cos(velAngle), (float)Math.sin(velAngle)).mul(speed*delta);
			x += tmp.x;
			y += tmp.y;
			velAngle += (Krakjam.rand.nextFloat()-0.5f)*2.0f * delta * 6;
		}
		
		resolveCollisions();
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setPosition(x-sprite.getWidth()/2.0f, y);
		sprite.draw(batch);
		
		drawDebug(batch);
	}

	@Override
	public Actor hit(float x, float y) {
		return null;
	}
}
