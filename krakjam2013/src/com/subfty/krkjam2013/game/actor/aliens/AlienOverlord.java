package com.subfty.krkjam2013.game.actor.aliens;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.game.GameScreen;

public class AlienOverlord extends Group{
	
	public Array<Alien> aliens = new Array<Alien>();
	private Background bg;
	private GameScreen gs;
	
	public AlienOverlord(Background bg, GameScreen gs){
		this.bg = bg;
		this.gs = gs;
	}
	
	public void act(float delta) {
		if(Krakjam.gameScreen.pause) return;
		//LOGIC
	}
	
	public void spawn(float x, float y, Alien.ALIEN_TYPE type){
		for(int i=0; i<aliens.size; i++)
			if(!aliens.get(i).visible){
				aliens.get(i).init(type, 
									x + bg.x,
									y + bg.y);
				
				return;
			}

		Alien a = new Alien();
		gs.agents.addActor(a);
		gs.colliders.add(a);
		aliens.add(a);
		spawn(x,y,type);
	}
	
	public void killAll(){
		for(int i=0; i<aliens.size; i++)
			if(aliens.get(i).visible)
				aliens.get(i).kill();
	}
	
}
