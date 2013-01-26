package com.subfty.krkjam2013.game.actor.crystals;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.actor.Player;

public class CrystalOverlord extends Actor{

	private Array<Crystal> crystals = new Array<Crystal>();
	private Player p;
	private Group agents;
	
	public CrystalOverlord(Player p, Group agents){
		this.p = p;
		this.agents = agents;
	}
	
	public void spawnNew(){
		for(int i=0; i<crystals.size; i++)
			if(!crystals.get(i).visible){
				Vector2.tmp.set(1, 0)
						   .rotate(460*Krakjam.rand.nextFloat())
						   .mul(3000 + 2000*Krakjam.rand.nextFloat());
				
				crystals.get(i).init(Vector2.tmp.x, Vector2.tmp.y);
				
				return;
			}
		
		Crystal c = new Crystal(p);
		crystals.add(c);
		agents.addActor(c);
		spawnNew();
	}
	
	public Crystal isCristalInRange(float radius){
		for(int i=0; i<crystals.size; i++)
			if(Vector2.tmp.set(p.x - crystals.get(i).x, p.y - crystals.get(i).y).len() < radius)
				return crystals.get(i);
			
		return null;
	}

	@Override
	public void act (float delta) {
		if(Krakjam.gameScreen.pause) return;
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		
	}

	@Override
	public Actor hit(float x, float y) {
		return null;
	}
}
