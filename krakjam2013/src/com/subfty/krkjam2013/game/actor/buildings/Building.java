package com.subfty.krkjam2013.game.actor.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.util.Art;

public class Building extends Group{
	
	public int tileX,
			   tileY,
			   tileWidth,
			   tileHeight;
	private Sprite image;
	private Background bg;
	
	public Building(Background bg){
		this.bg = bg;
		
		image = new Sprite();//Krakjam.art.atlases[Art.A_AGENTS].createSprite("base1");
		this.visible = false;
	}
	
	public void init(int tileX, int tileY, int tileWidth, int tileHeight, String img){
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		this.visible = true;
		bg.registerBuilding(this);
		
		image.setRegion(Krakjam.art.atlases[Art.A_AGENTS].createSprite(img));
		image.setSize(tileWidth * Background.TILE_SIZE,  
					  tileHeight * Background.TILE_SIZE);
		
		width  = tileWidth * Background.TILE_SIZE;
		height = tileHeight * Background.TILE_SIZE;
		
		act(0);
	}
	public void kill(){
		this.visible = false;
		bg.unregisterBuilding(this);
	}
	
	@Override
	public void act(float delta) {
		this.x = -bg.x + tileX * Background.TILE_SIZE;
		this.y = -bg.y + tileY * Background.TILE_SIZE;
		
		image.setPosition(x,y);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		image.draw(batch, parentAlpha);
	}
}
