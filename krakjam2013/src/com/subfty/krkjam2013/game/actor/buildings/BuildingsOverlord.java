package com.subfty.krkjam2013.game.actor.buildings;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;

public class BuildingsOverlord extends Group{
	
	//TYPE OF BUILDING
	public enum B_TYPE{
		BASE(4, 4, "base", "Main Base",20, true),
		TURRET(2,3, "turent", "Turret", 20, true),
		//RESP(3,3, "resp", "Respawn", 500, false)
		;
		
		public final int width;
		public final int height;
		public final String img;
		public final String desc;
		public final int MAX_HEALTH;
		public final boolean destroyable;
		
		B_TYPE(int width, int height, String img, String desc, int MAX_HEALTH, boolean destroyable){
			this.width = width;
			this.height = height;
			this.img = img;
			this.desc = desc;
			this.MAX_HEALTH = MAX_HEALTH;
			this.destroyable = destroyable;
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
				buildings.get(i).init(tileX, tileY, type);
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
