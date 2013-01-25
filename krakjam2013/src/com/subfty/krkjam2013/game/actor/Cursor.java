package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Cursor extends Actor{
	private Sprite image;
	
	Cursor(){
		width=5f;
		height=5f;
		
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		image.draw(batch, parentAlpha);
	}

	@Override
	public Actor hit(float x, float y) {
		return null;
	}
}
