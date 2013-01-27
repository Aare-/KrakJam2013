package com.subfty.krkjam2013.game.actor.player;

import com.badlogic.gdx.utils.Array;

public class Stats {
	
	public class SNode{
		public String description;
		public float value;
		public Array<SNode> unlocks;
		public boolean unlocked;
		
		public SNode(String desc, float value, Array<SNode> unlocks){
			this.description = desc;
			this.value = value;
			this.unlocks = unlocks;
		}
		public SNode(String desc, float value){
			this.description = desc;
			this.value = value;
			this.unlocks = new Array<SNode>();
		}
	}
	
	public SNode root;
	public Stats(){
		root = new SNode("Level 0", 0, new Array<SNode>());
		
		SNode repair_tier1 = new SNode("Repair I", 3),
			  repair_tier2 = new SNode("Repair II", 5),
			  repair_tier3 = new SNode("Repair III", 10),
			  repair_tier4 = new SNode("Repair IV", 20),
			  repair_tier5 = new SNode("Repair V", 1000),
			  
			  fight_tier1 = new SNode("Fight I", 0.4f),
			  fight_tier2 = new SNode("Fight II", 0.3f),
			  fight_tier3 = new SNode("Fight III", 0.25f),
			  fight_tier4 = new SNode("Fight IV", 0.2f),
			  fight_tier5 = new SNode("Fight V", 0.15f),
			  
			  meteorit_miner_tier1 = new SNode("Detect I", 1800),
			  meteorit_miner_tier2 = new SNode("Detect II", 2000),
			  meteorit_miner_tier3 = new SNode("Detect III", 2500),
			  meteorit_miner_tier4 = new SNode("Detect IV", 3000),
			  meteorit_miner_tier5 = new SNode("Detect V", 4000);
		
		root.unlocks.addAll(new SNode[] {repair_tier1, fight_tier1, meteorit_miner_tier1});
		
		repair_tier1.unlocks.add(repair_tier2);
		repair_tier2.unlocks.add(repair_tier3);
		repair_tier3.unlocks.add(repair_tier4);
		repair_tier4.unlocks.add(repair_tier5);
		
		fight_tier1.unlocks.add(fight_tier2);
		fight_tier2.unlocks.add(fight_tier3);
		fight_tier3.unlocks.add(fight_tier4);
		fight_tier4.unlocks.add(fight_tier5);
		
		meteorit_miner_tier1.unlocks.add(meteorit_miner_tier2);
		meteorit_miner_tier2.unlocks.add(meteorit_miner_tier3);	
		meteorit_miner_tier3.unlocks.add(meteorit_miner_tier4);	
		meteorit_miner_tier4.unlocks.add(meteorit_miner_tier5);	
	}
	
	private float getUnlock(SNode node, float def) {
		while(node.unlocks.size > 0 && node.unlocks.get(0).unlocked) {
			node = node.unlocks.get(0);
		}
		
		if(node.unlocked)
			return node.value;
		
		return def;
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
		return getUnlock(root.unlocks.get(1), 0.5f);
	}
	public float getMovementSpeed(){
		return getUnlock(root.unlocks.get(2), 1500)/1500.0f*250.0f;
	}
	public float getScanSensitivity(){
		return getUnlock(root.unlocks.get(2), 1500);
	}
	public float getRepairSpeed(){
		return getUnlock(root.unlocks.get(0), 2);
	}
}
