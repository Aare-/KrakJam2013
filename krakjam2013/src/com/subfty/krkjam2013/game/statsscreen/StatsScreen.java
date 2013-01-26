package com.subfty.krkjam2013.game.statsscreen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.subfty.krkjam2013.game.actor.Player;

public class StatsScreen extends Group{
	private Player player;
	
	public StatsScreen(Player player){
		this.visible = false;
		this.player = player;
	}
	
	public void showStatsScreen(){
		
	}
	
	public void act (float delta) {
		if(!visible) return;
		
		super.act(delta);

	}
	
	public void draw (SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}
}
