package com.subfty.krkjam2013.game;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.util.Art;

public class Background extends Group{
	
	private final static float TILE_SIZE = 5;
	private final long SEED = 123412431;
	private Random random;
	
	private Sprite bgSprites[][]= new Sprite[(int) (Krakjam.SCREEN_WIDTH / TILE_SIZE + 2)]
			  								[(int) (Krakjam.SCREEN_HEIGHT / TILE_SIZE + 2)]; 
	
    //INIT
	public Background(){
		random = new Random(this.SEED);
		init();
	}
	public Background(long SEED){
		random = new Random(SEED);
		init();
	}
	private void init(){
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++){
				bgSprites[i][j] = new Sprite();
				bgSprites[i][j].setSize(TILE_SIZE, TILE_SIZE);
			}
	}
	
	public void act(float delta) {
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++){
				bgSprites[i][j].setRegion(Krakjam.art.atlases[Art.A_BACKGROUND].findRegion("bg", 1));
				bgSprites[i][j].setX(i * TILE_SIZE);
				bgSprites[i][j].setY(j * TILE_SIZE);
			}
	}
	
	public void draw(SpriteBatch batch, float parentAlpha) {
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++)
				bgSprites[i][j].draw(batch, parentAlpha);
	}
}
