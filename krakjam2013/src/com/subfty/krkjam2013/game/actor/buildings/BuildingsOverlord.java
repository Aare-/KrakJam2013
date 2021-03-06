package com.subfty.krkjam2013.game.actor.buildings;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;

public class BuildingsOverlord extends Group{
	
	//TYPE OF BUILDING
	public enum B_TYPE{
		BASE(4, 4, "base", "Main Base",20, true, false),
		TURRET(2,3, "turent", "Turret", 20, true, true),
		GENERATOR(3,2, "electro", "Generator", 10, false, true),
		MEDCENTRE(4,3, "med", "MED", 10, true, false);
		
		public final int width;
		public final int height;
		public final String img;
		public final String desc;
		public final int MAX_HEALTH;
		public final boolean destroyable;
		public final boolean canFeedCristal;
		
		B_TYPE(int width, int height, String img, String desc, int MAX_HEALTH, boolean destroyable, boolean canFeed){
			this.width = width;
			this.height = height;
			this.img = img;
			this.desc = desc;
			this.MAX_HEALTH = MAX_HEALTH;
			this.destroyable = destroyable;
			this.canFeedCristal = canFeed;
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
	
	public Building createNewBuilding(int tileX, int tileY, B_TYPE type){
		for(int i=0; i<buildings.size; i++)
			if(!buildings.get(i).visible){
				buildings.get(i).init(tileX, tileY, type);
				return buildings.get(i);
			}
		
		Building b = new Building(bg);
		buildings.add(b);
		agents.addActor(b);
		return createNewBuilding(tileX,  tileY, type);
	}
	public void destroyAll(){
		for(int i=0; i<buildings.size; i++){
			if(buildings.get(i).visible)
				buildings.get(i).kill();
		}
	}
	
	public Building collidingWithBuilding(float testX, float testY){
		for(int i=0; i<buildings.size; i++)
			if(buildings.get(i).visible && 
			   testX > buildings.get(i).x - 30 && 
			   testX < buildings.get(i).x + buildings.get(i).width -30 &&
			   testY > buildings.get(i).y - 30 &&
			   testY < buildings.get(i).y + buildings.get(i).height - 30)
				return buildings.get(i);
		
		return null;
	}
}
