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

	private Sprite sprite;
	private float anim;
	private Player p;
	
	public BloodyScreen(Player p){
		this.p = p;
		
		sprite = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("bscreen");
		sprite.setPosition(0, 0);
		sprite.setSize(Krakjam.STAGE_W, Krakjam.STAGE_H);
		
		this.visible = true;
		anim = 0;
	}
	
	@Override
	public void act (float delta) {
		if(!this.visible) return;
		
		anim += Math.PI*2*delta*1.2f;
		if(anim > Math.PI*2)
			anim = 0;
		
		sprite.setColor(1, 1, 1, (float)(((Math.sin(anim)+1.0f)/2.0f)*(0.8f * (1-Math.min(p.life, 30.0f)/30.0f))));
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
