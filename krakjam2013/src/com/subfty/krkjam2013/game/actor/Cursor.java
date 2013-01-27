package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.actor.buildings.Building;
import com.subfty.krkjam2013.game.actor.crystals.Crystal;
import com.subfty.krkjam2013.util.Art;

public class Cursor extends Actor{
	public static final int M_SHOOT = 0,
						    M_REPAIR = 1,
						    M_MINE = 2;
	public int mode;
	
	private Sprite image;
	
	
	private TextureRegion regions[];
	
	Cursor(){
		width=5f;
		height=5f;
		
		regions = new TextureRegion[]{Krakjam.art.atlases[Art.A_AGENTS].findRegion("aim", 1),
									  Krakjam.art.atlases[Art.A_AGENTS].findRegion("aim", 2),
									  Krakjam.art.atlases[Art.A_AGENTS].findRegion("aim", 3)};
		
		image=Krakjam.art.atlases[Art.A_ENTITIES].createSprite("celownik");
		image.setSize(100, 100);
		image.setOrigin(image.getWidth()/2.0f, image.getHeight()/2.0f);
	}

	public void setMode(int mode){
		this.mode = mode;
		switch(mode){
		case M_SHOOT:
			image.setRegion(regions[0]);
			image.setSize(100, 100);
			break;
		case M_REPAIR:
			image.setRegion(regions[1]);
			image.setSize(60, 60);
			break;
		case M_MINE:
			image.setRegion(regions[2]);
			image.setSize(60, 60);
			break;
		}
		
	}
	
	@Override
	public void act(float delta){
		if(Krakjam.gameScreen.pause) return;
		
		super.act(delta);
		
		Crystal c = Krakjam.gameScreen.cOverlord.isCristalInRange(150);
		Building b = Krakjam.gameScreen.bOverlord.collidingWithBuilding(this.x, this.y);
		if(c != null){
			setMode(M_MINE);
			if(Gdx.input.justTouched()){
				Krakjam.gameScreen.player.cristalCollected(c.value);
				c.kill();
			}
		} else if(b != null){
			setMode(M_REPAIR);
			if(Gdx.input.justTouched()){
				b.repair(Krakjam.gameScreen.player.stats.getRepairSpeed());
			}
		}else
			setMode(M_SHOOT);
		
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
