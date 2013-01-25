package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Cursor {
	private float x;
	private float y;
	private float width;
	private float height;
	private Texture image;
	
	Cursor(){
		width=5f;
		height=5f;
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(image, x, y, width, height);
	}
	
	public void setX(float x){
		this.x=x;
	}
	public void setY(float y){
		this.y=y;
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
}
