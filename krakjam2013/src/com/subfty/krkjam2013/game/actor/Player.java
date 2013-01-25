package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.util.Art;

public class Player extends Actor{
	public float x;
	public float y;
	private float width;
	private float height;
	private float step;
	private Sprite image;
	private Art art;
	
	private Cursor cursor;
	
	Player(float x, float y){
		this.x=x;
		this.y=y;
		step=10f;
		width=20f;
		height=20f;
		
		cursor=new Cursor();
		art=new Art();
	}
	
	@Override
	public void act(float delta) {
		float move=step*delta;
		double kat=0f;

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
		if(Gdx.input.isKeyPressed(Keys.W)){
			kat=Math.PI/2.0f;
		}
		else if(Gdx.input.isKeyPressed(Keys.S)){
			kat=3*Math.PI/2.0f;
		}
		else if(Gdx.input.isKeyPressed(Keys.A)){
			kat=Math.PI;
		}
		else if(Gdx.input.isKeyPressed(Keys.D)){
			kat=0f;
		}
		else{
			move=0f;
		}
		
		x+=move*Math.cos(kat);
		y+=move*Math.sin(kat);
		
		cursor.setX(Gdx.input.getX());
		cursor.setY(Gdx.input.getY());
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.draw(image, x, y, width, height);
		cursor.draw(batch);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}
}
