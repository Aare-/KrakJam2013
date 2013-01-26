package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.util.Art;

public class Cursor extends Actor{
	private Sprite image;
	
	Cursor(){
		width=5f;
		height=5f;
		
		image=Krakjam.art.atlases[Art.A_ENTITIES].createSprite("celownik");
		image.setOrigin(image.getWidth()/2.0f, image.getHeight()/2.0f);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
		image.draw(batch, parentAlpha);
	}

	@Override
	public Actor hit(float x, float y) {
		return null;
	}
	
	public void setX(float x){
		x -= image.getWidth()/2.0f;
		this.x=x;
		image.setX(x);
	}
	
	public void setY(float y){
		y -= image.getHeight()/2.0f;
		this.y=y;
		image.setY(y);
	}
}
