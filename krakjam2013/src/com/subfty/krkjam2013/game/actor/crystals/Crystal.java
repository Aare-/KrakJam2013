package com.subfty.krkjam2013.game.actor.crystals;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.util.Art;

public class Crystal extends Actor{
	
	private Sprite img;
	private Player p;
	private float pingDelay = -1,
				  pingVolume;
	public boolean diamondVisible;
	public int value;
	
	public Crystal(Player p){
		this.p = p;
		img = Krakjam.art.atlases[Art.A_AGENTS].createSprite("crystal");
		img.setSize(50, 50);
		this.visible = false;
	}
	
	public void init(float x, float y, int value, boolean visible){
		this.x = x;
		this.y = y;
		this.visible = true;
		this.value = value;
		pingDelay = -1;
		diamondVisible = visible;
	}
	
	public void kill(){
		this.visible = false;
	}
	
	@Override
	public void act (float delta) {
		if(!this.visible || Krakjam.gameScreen.pause) return;
		super.act(delta);
		
		if(pingDelay != -1){
			pingDelay -= delta;
			if(pingDelay < 0){
				pingDelay = getPingDelay();
				Krakjam.art.ping.play(pingVolume);
			}
		}else{
			pingDelay = getPingDelay();
			if(pingDelay != -1)
				pingDelay = 0;
		}
	}
	
	private float getPingDelay(){
		if(Vector2.tmp.set(this.x - p.x, this.y-p.y).len() < p.stats.getScanSensitivity()){
			float v = Vector2.tmp.set(this.x - p.x, this.y-p.y).len()/p.stats.getScanSensitivity();
			pingVolume = Math.max(0.1f, 1-v);
			
			return Math.max(0.3f,Math.max(0.1f, v*1.5f));
		}else
			return -1;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		img.setPosition(x-img.getWidth()/2, 
						y-img.getHeight()/2);
		if(diamondVisible)
			img.draw(batch, parentAlpha);
	}

	@Override
	public Actor hit(float x, float y) {
		return null;
	}
}
