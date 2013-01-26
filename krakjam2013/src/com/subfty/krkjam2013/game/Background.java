package com.subfty.krkjam2013.game;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.util.Art;

public class Background extends Group{
	
	private final static float TILE_SIZE = 50;
	private final long SEED = -123412431;
	private Random random;
	
	private Sprite bgSprites[][]= new Sprite[(int) (Krakjam.STAGE_W / TILE_SIZE + 2)]
			  								[(int) (Krakjam.STAGE_H / TILE_SIZE + 2)]; 
	
	private TextureRegion regions[];
	
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
		
		regions = new TextureRegion[2];
		regions[0] = Krakjam.art.atlases[Art.A_BACKGROUND].findRegion("rock",1);
		regions[1] = Krakjam.art.atlases[Art.A_BACKGROUND].findRegion("rock",2);
		
		act(0);
	}
	
	public void act(float delta) {
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++){
				random.setSeed(SEED + 
							   (int)(this.x / TILE_SIZE) +
							   (int)(this.y / TILE_SIZE)*bgSprites.length +
							   i + j*bgSprites.length);
				
				bgSprites[i][j].setRegion(regions[0]);
				bgSprites[i][j].setX(i * TILE_SIZE - this.x % TILE_SIZE);
				bgSprites[i][j].setY(j * TILE_SIZE - this.y % TILE_SIZE);
				
				bgSprites[i][j].setColor(255.0f/255.0f - 60.0f*random.nextFloat()/255.0f, 
										 45.0f/255.0f  + 50.0f*random.nextFloat()/255.0f, 
										 23.0f/255.0f  + 50.0f*random.nextFloat()/255.0f, 1);
			}
		
		this.x += 120f*delta;
		this.y += 120f*delta;
	}
	
	public void draw(SpriteBatch batch, float parentAlpha) {
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++)
				bgSprites[i][j].draw(batch, parentAlpha);
	}
	
	//OCCUPATION
	public boolean isOccupied(int x, int y){
		return false;
	}
	
	public void registerBuilding(Actor building){
		
	}
}
