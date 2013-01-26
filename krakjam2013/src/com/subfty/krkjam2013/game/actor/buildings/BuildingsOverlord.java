package com.subfty.krkjam2013.game.actor.buildings;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;

public class BuildingsOverlord extends Group{
	
	//TYPE OF BUILDING
	public enum B_TYPE{
		BASE(3, 3, "base", "Main Base");
		
		public final int width;
		public final int height;
		public final String img;
		public final String desc;
		
		B_TYPE(int width, int height, String img, String desc){
			this.width = width;
			this.height = height;
			this.img = img;
			this.desc = desc;
		}
	}
	
	private Array<Building> buildings = new Array<Building>();
	
	private Background bg;
	private Group agents;
	
	public BuildingsOverlord(Background bg, Group agents){
		this.bg = bg;
		this.agents = agents;
	}
	
	public void act(float delta) {
		if(Krakjam.gameScreen.pause) return;
		//LOGIC
	}
	
	public void createNewBuilding(int tileX, int tileY, B_TYPE type){
		for(int i=0; i<buildings.size; i++)
			if(!buildings.get(i).visible){
				switch(type){
				case BASE:
					buildings.get(i).init(tileX, tileY, type);
					break;
				}
				
				return;
			}
		
		Building b = new Building(bg);
		buildings.add(b);
		agents.addActor(b);
		createNewBuilding(tileX,  tileY, type);
	}
	public void destroyAll(){
		for(int i=0; i<buildings.size; i++){
			if(buildings.get(i).visible)
				buildings.get(i).kill();
		}
	}
}
