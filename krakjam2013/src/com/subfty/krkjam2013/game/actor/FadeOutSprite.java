package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class FadeOutSprite extends Actor {
	private float timer;
	private float maxTime;
	public float angle = 0;
	private Sprite sprite;
	
	FadeOutSprite(float time, Sprite sprite) {
		timer   	= time;
		maxTime 	= time;
		this.sprite = new Sprite(sprite);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setColor(1, 1, 1, timer/maxTime);
		sprite.setRotation(angle);
		sprite.draw(batch);
	}

	public void act(float delta) {
		timer -= delta;
		
		if(timer <= 0) {
			stage.removeActor(this);
		}
	}
	
	@Override
	public Actor hit(float x, float y) {
		return null;
	}
	
	
}
