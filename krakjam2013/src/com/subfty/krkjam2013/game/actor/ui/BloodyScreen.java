package com.subfty.krkjam2013.game.actor.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Logger;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.util.Art;

//SO REAL!!!

public class BloodyScreen extends Actor{

	public Sprite sprite;
	private float anim;
	private Player p;
	
	public BloodyScreen(Player p){
		this.p = p;
		
		sprite = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("bscreen");
		sprite.setPosition(0, 0);
		sprite.setSize(Krakjam.STAGE_W, Krakjam.STAGE_H);
		
		this.visible = true;
		sprite.setColor(1, 1, 1, 0);
		anim = 0;
	}
	
	@Override
	public void act (float delta) {
		if(!this.visible) return;
		
		anim += Math.PI*2*delta*1.3f;
		if(anim > Math.PI*2)
			anim = 0;
		
		if(!Krakjam.tM.containsTarget(sprite))
			sprite.setColor(1, 1, 1, (float)(((Math.sin(anim)+1.0f)/2.0f)*(0.8f * (1-Math.min(p.life, 30.0f)/30.0f))));
		
		if(Math.min(p.life, 60.0f)/60.0f < 1){
			if(!Art.heartbeat.isPlaying()){
				Art.heartbeat.play();
				Art.heartbeat.setLooping(true);
			}
			Art.heartbeat.setVolume((1-Math.min(p.life, 60.0f)/60.0f));
		}else if(Art.heartbeat.isPlaying()){
			Art.heartbeat.stop();
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.draw(batch, parentAlpha);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

}
