package com.subfty.krkjam2013.game.actor.buildings;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.game.actor.buildings.BuildingsOverlord.B_TYPE;
import com.subfty.krkjam2013.util.Art;

public class Building extends Group{
	
	public int tileX,
			   tileY,
			   tileWidth,
			   tileHeight;
	private Sprite image;
	private String desc;
	private Background bg;
	
	public Building(Background bg){
		this.bg = bg;
		
		image = new Sprite();//Krakjam.art.atlases[Art.A_AGENTS].createSprite("base1");
		this.visible = false;
	}
	
	public void init(int tileX, int tileY, B_TYPE type){
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileWidth = type.width;
		this.tileHeight = type.height;
		
		this.visible = true;
		bg.registerBuilding(this);
		this.desc = type.desc;
		
		this.width = tileWidth * Background.TILE_SIZE;
		this.height = tileHeight * Background.TILE_SIZE;
		
		image.setRegion(Krakjam.art.atlases[Art.A_AGENTS].createSprite(type.img));
		width  = tileWidth * Background.TILE_SIZE;
		image.setSize(this.width, this.height);
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
		
		Krakjam.art.fonts[Art.F_DIGITAL].setColor(1, 1, 1, 0.5f);
		Krakjam.art.fonts[Art.F_DIGITAL].setScale(0.25f);
		Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, desc, this.x, this.y-5, this.width, HAlignment.CENTER);
		
	}
}
