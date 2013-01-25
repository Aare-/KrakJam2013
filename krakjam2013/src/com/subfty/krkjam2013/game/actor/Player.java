package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Logger;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.util.Art;

public class Player extends Group{
	private float step;
	private float cursorDistance;
	
	private Sprite image;
	private Cursor cursor;
	
	public Player(float x, float y){
		this.x=x;
		this.y=y;
		width=20f;
		height=20f;
		
		step=500f;
		cursorDistance=5f;
		
		cursor=new Cursor();		
		image = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("alien");
		image.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		
		this.addActor(cursor);

	}
	
	@Override
	public void act(float delta) {

		float move=step*delta;
		double kat=0f;
		double kat2;

		if(Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D)){
			kat=Math.PI/4.0f;
		}
		else if(Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S)){
			kat=7*Math.PI/4.0f;
		}
		else if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.S)){
			kat=5*Math.PI/4.0f;
		}
		else if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.W)){
			kat=3*Math.PI/4.0f;
		}
		else if(Gdx.input.isKeyPressed(Keys.W)){
			kat=Math.PI/2.0f;
			//y+=20f;
		}
		else if(Gdx.input.isKeyPressed(Keys.S)){
			kat=3*Math.PI/2.0f;
			//y--;
		}
		else if(Gdx.input.isKeyPressed(Keys.A)){
			kat=Math.PI;
			//y--;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)){
			kat=0f;
			//x++;
		}
		else{
			move=0f;
		}
		
		x+=move*Math.cos(kat);
		y+=move*Math.sin(kat);
		
		image.setPosition(x, y);
		float wspx = Gdx.input.getX();
		float wspy = Gdx.input.getY();
		
		kat2=Math.atan2(wspy, wspx);
				
		cursor.x = (float)(cursorDistance*Math.cos(kat2))+x+width/2.0f;
		cursor.y = (float)(cursorDistance*Math.sin(kat2))+y+width/2.0f;
		
		
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		image.draw(batch);
		//cursor.draw(batch, parentAlpha);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}
}
