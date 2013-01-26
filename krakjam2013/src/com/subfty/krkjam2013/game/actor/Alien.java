package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.util.Art;

public class Alien extends  Collider {
	public float speed = 20;
	private Sprite sprite;
	private float velAngle;
	
	public static int STATE_WALKING			= 0;
	public static int STATE_FOLLOW_PLAYER 	= 1;
	
	private int state = STATE_WALKING;
	
	 public void load() {
		 velAngle = 0;
		 sprite = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("alien");
		 x = 300 + Krakjam.rand.nextFloat();
		 y = 300 + Krakjam.rand.nextFloat();
	}
	
	public void tick(float delta) {
		Vector2 tmp = Vector2.tmp;
		Player p = Krakjam.gameScreen.player;
		tmp.set(p.x, p.y).sub(x, y);
		
		if(state == STATE_FOLLOW_PLAYER || tmp.len() < 400) {
			state = STATE_FOLLOW_PLAYER;
			tmp.nor().mul(delta*speed);
			//x += tmp.x;
			//y += tmp.y;
		} else {
			tmp.set((float)Math.cos(velAngle), (float)Math.sin(velAngle)).mul(speed*delta);
			//x += tmp.x;
			//y += tmp.y;
			velAngle += (Krakjam.rand.nextFloat()-0.5f)*2.0f * delta * 6;
		}
		
		resolveCollisions();
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		tick(Gdx.graphics.getDeltaTime());
		
		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}
}
