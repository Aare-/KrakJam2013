package com.subfty.krkjam2013.game;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.subfty.krkjam2013.Krakjam;

public class Background extends Group{
	
	private final static float TILE_SIZE = 5;
	private final long SEED = 123412431;
	private Random random;
	
	private Sprite bgSprites[][]= new Sprite[(int) (Krakjam.SCREEN_WIDTH / TILE_SIZE + 2)]
			  								[(int) (Krakjam.SCREEN_HEIGHT / TILE_SIZE + 2)]; 
	
    //INIT
	public Background(){
		random = new Random(this.SEED);
		
	}
	public Background(long SEED){
		random = new Random(SEED);
		
	}
	private void init(){
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++){
				bgSprites[i][j] = new Sprite();
			}
	}
	
	public void act(float delta) {
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++){
				//bgSprites[i][j]
			}
	}
	
	public void draw(SpriteBatch batch, float parentAlpha) {
		
	}
}
