package com.subfty.krkjam2013.game.actor.buildings;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.game.Background;

public class BuildingsOverlord {
	
	//TYPE OF BUILDING
	public enum B_TYPE{
		BASE(3, 3, "base1");
		
		public final int width;
		public final int height;
		public final String img;
		
		B_TYPE(int width, int height, String img){
			this.width = width;
			this.height = height;
			this.img = img;
		}
	}
	
	private Array<Building> buildings = new Array<Building>();
	
	private Background bg;
	private Group agents;
	
	public BuildingsOverlord(Background bg, Group agents){
		this.bg = bg;
		this.agents = agents;
	}
	
	public void createNewBuilding(int tileX, int tileY, B_TYPE type){
		for(int i=0; i<buildings.size; i++)
			if(!buildings.get(i).visible){
				switch(type){
				case BASE:
					buildings.get(i).init(tileX, tileY, type.width, type.height, type.img);
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
		
	}
}
