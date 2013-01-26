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
		REGULAR;
	}
	
	private ALIEN_TYPE type;
	
	public float speed = 20;
	private Sprite sprite;
	private float velAngle;
	
	public static int STATE_WALKING			= 0;
	public static int STATE_FOLLOW_PLAYER 	= 1;
	
	private int state = STATE_WALKING;
	
	public Alien(){
		this.visible = false;
	}
	
	public void init(ALIEN_TYPE type, float x, float y){
		this.type = type;
		this.visible = true;
		
		velAngle = 0;
		sprite = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("alien");
		this.x = x;
		this.y = y;
	}
	
	public void kill(){
		this.visible = false;
	}
	
	public void act(float delta) {
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
		// TODO Auto-generated method stub
		return null;
	}
}
