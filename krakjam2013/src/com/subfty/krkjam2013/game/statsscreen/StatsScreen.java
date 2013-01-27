package com.subfty.krkjam2013.game.statsscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.game.actor.player.Stats;
import com.subfty.krkjam2013.game.actor.player.Stats.SNode;
import com.subfty.krkjam2013.util.Art;

public class StatsScreen extends Group{
	private Player player;
	private Sprite skillsBgSprite;
	
	public StatsScreen(Player player){
		this.visible = false;
		this.player = player;
		
		skillsBgSprite = Krakjam.art.atlases[Art.A_SKILLS].createSprite("skills");
	}
	
	public void showStatsScreen(){
		this.visible = true;
	}
	
	
	private boolean zPressed = false;
	private boolean xPressed = false;
	private boolean cPressed = false;
	
	public void act (float delta) {
		if(!visible) return;
		
		Stats.SNode root = player.stats.root;
		
		boolean end = false;
		
		if(Gdx.input.isKeyPressed(Keys.Z)) {
			if(!zPressed) {
				zPressed = true;
				
				Stats.SNode node = root.unlocks.get(0);
				
				while(node.unlocked && node.unlocks.size > 0) {
					node = node.unlocks.get(0);
				}
				
				if(node.unlocked == false) end = true;
				node.unlocked = true;
			}
		} else zPressed = false;
		
		if(Gdx.input.isKeyPressed(Keys.X)) {
			if(!xPressed) {
				xPressed = true;
				
				Stats.SNode node = root.unlocks.get(1);
				
				while(node.unlocked && node.unlocks.size > 0) {
					node = node.unlocks.get(0);
				}
				
				if(node.unlocked == false) end = true;
				node.unlocked = true;
			}
		} else xPressed = false;
		
		if(Gdx.input.isKeyPressed(Keys.C)) {
			if(!cPressed) {
				cPressed = true;
				
				Stats.SNode node = root.unlocks.get(2);
				
				while(node.unlocked && node.unlocks.size > 0) {
					node = node.unlocks.get(0);
				}
				
				if(node.unlocked == false) end = true;
				node.unlocked = true;
			}
		} else cPressed = false;
		
		if(end) {
			this.visible = false;
		}
		
		
		super.act(delta);
	}
	
	public void draw (SpriteBatch batch, float parentAlpha) {
		if(visible == false)
			return;
		
		super.draw(batch, parentAlpha);
		
		skillsBgSprite.setPosition((800-skillsBgSprite.getWidth())/2.0f, 0.0f);
		skillsBgSprite.draw(batch);
		
		Krakjam.art.fonts[Art.F_DIGITAL].setColor(1, 1, 1, 1.0f);
		Krakjam.art.fonts[Art.F_DIGITAL].setScale(0.2f);
		
		Stats.SNode root = player.stats.root;
		
		Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, root.description, 
				360, 600-70, 80, HAlignment.CENTER);
		
		float starty = 600 - 205;
		float spacingy = 79;
	
		float spacingx = 160;
		
		for(int j=0; j<3; j++) {
			Stats.SNode node = root.unlocks.get(j);
			for(int i=0; i<3; i++) {
				
				if(node.unlocked) {
					Krakjam.art.fonts[Art.F_DIGITAL].setColor(1, 1, 0, 1.0f);
				} else {
					Krakjam.art.fonts[Art.F_DIGITAL].setColor(0.5f, 0.5f, 0.5f, 1.0f);
				}
				
				Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, node.description, 
						360-spacingx+spacingx*j-5, starty-spacingy*i, 90, HAlignment.CENTER);
			
				if(node.unlocks.size > 0)
					node = node.unlocks.get(0);
			}
		}
		
		Krakjam.art.fonts[Art.F_DIGITAL].setColor(1, 1, 1, 1.0f);
		
		Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, "PRESS Z", 
				360-spacingx-60, starty+spacingy-20, 200, HAlignment.CENTER);
		Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, "PRESS X", 
				360-60, starty+spacingy-20, 200, HAlignment.CENTER);
		Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, "PRESS C", 
				360+spacingx-60, starty+spacingy-20, 200, HAlignment.CENTER);
	}
}
