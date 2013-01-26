package com.subfty.krkjam2013.game.actor.player;

import com.badlogic.gdx.utils.Array;

public class Stats {
	
	public class SNode{
		public String description;
		public int reqXP;
		public Array<SNode> unlocks;
		public boolean unlocked;
		
		public SNode(String desc, int reqXP, Array<SNode> unlocks){
			this.description = desc;
			this.reqXP = reqXP;
			this.unlocks = unlocks;
		}
		public SNode(String desc, int reqXP){
			this.description = desc;
			this.reqXP = reqXP;
			this.unlocks = new Array<SNode>();
		}
	}
	
	public SNode root;
	public Stats(){
		root = new SNode("", 0, new Array<SNode>());
		
		SNode repair_tier1 = new SNode("Repair I", 10),
			  repair_tier2 = new SNode("Repair II", 30),
			  repair_tier3 = new SNode("Repair III", 50),
			  
			  fight_tier1 = new SNode("Fight I", 10),
			  fight_tier2 = new SNode("Fight II", 30),
			  fight_tier3 = new SNode("Fight III", 50),
			  
			  meteorit_miner_tier1 = new SNode("Detect I", 10),
			  meteorit_miner_tier2 = new SNode("Detect II", 30),
			  meteorit_miner_tier3 = new SNode("Detect III", 50);
		
		root.unlocks.addAll(new SNode[] {repair_tier1, fight_tier1, meteorit_miner_tier1});
		
		repair_tier1.unlocks.add(repair_tier2);
		repair_tier2.unlocks.add(repair_tier3);
		
		fight_tier1.unlocks.add(fight_tier2);
		fight_tier2.unlocks.add(fight_tier3);
		
		meteorit_miner_tier1.unlocks.add(meteorit_miner_tier2);
		meteorit_miner_tier2.unlocks.add(meteorit_miner_tier3);	
	}
	
	public void clearAll(){
		clearAll(root);
	}
	public void clearAll(SNode n){
		if(n.unlocks != null)
			for(int i=0; i<n.unlocks.size; i++){
				n.unlocks.get(i).unlocked = false;
				clearAll(n.unlocks.get(i));
			}
	}
	
    //STATISTIC GETTERS
	public float getRateOfFire(){
		return 0.5f;
	}
	public float getMovementSpeed(){
		return 250;
	}
	public float getScanSensitivity(){
		return 1500;
	}
}
