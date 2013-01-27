package com.subfty.krkjam2013.game.actor.buildings;

import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	private Sprite imageDamaged;
	private String desc;
	private Background bg;
	private float health;
	
	private B_TYPE type;
	
	public Building(Background bg){
		health = 1.0f;
		this.bg = bg;
		
		image = new Sprite();//Krakjam.art.atlases[Art.A_AGENTS].createSprite("base1");
		imageDamaged = new Sprite();
		this.visible = false;
	}
	
	public void init(int tileX, int tileY, B_TYPE type){
		this.type = type;
		
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
		TextureRegion region = Krakjam.art.atlases[Art.A_AGENTS].createSprite(type.img+"_damaged");
		if(region != null) {
			imageDamaged.setRegion(region);
		}
		else
			imageDamaged = null; // TODO
		width  = tileWidth * Background.TILE_SIZE;
		image.setSize(this.width, this.height);
		if(region != null) {
			imageDamaged.setSize(this.width, this.height);
		}
		height = tileHeight * Background.TILE_SIZE;
		
		if(type == B_TYPE.BASE) {
			Krakjam.gameScreen.base = this;
		}
		
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
		
		if(imageDamaged != null) {
			imageDamaged.setPosition(x, y);
			imageDamaged.draw(batch, 1.0f - health);
		}
		
		Krakjam.art.fonts[Art.F_DIGITAL].setColor(1, 1, 1, 0.5f);
		Krakjam.art.fonts[Art.F_DIGITAL].setScale(0.25f);
		Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, desc, this.x, this.y-5, this.width, HAlignment.CENTER);
		
	}

    //HEALTH
	public void repair(float ammount){
		this.health += ammount;
		health = Math.min(type.MAX_HEALTH, health);
	}
	public float getHealth(){
		return health;
	}
	
	public void damage(float ammount) {
		this.health -= ammount;
		if(this.health < 0) this.health = 0;
	}
}
