package com.subfty.krkjam2013.game;

import java.util.Comparator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.subfty.krkjam2013.game.actor.Alien;
import com.subfty.krkjam2013.game.actor.Collider;
import com.subfty.krkjam2013.game.actor.FadeOutSprite;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.game.actor.buildings.BuildingsOverlord;
import com.subfty.krkjam2013.game.actor.buildings.BuildingsOverlord.B_TYPE;
import com.subfty.krkjam2013.util.Screen;

public class GameScreen extends Screen{

    //GROUPS
	public Background background;
	public Group agents;
	public Group ui;
	
	public Player player;
	public BuildingsOverlord bOverlord;

	public LinkedList<Collider> colliders = new LinkedList<Collider>();
	
	public GameScreen(Stage stage){
		super(stage);
		
		background = new Background();
		agents= new Group();
		ui = new Group();
		bOverlord = new BuildingsOverlord(background, agents);
		player = new Player(background, 0,0);
		
		agents.addActor(player);
		colliders.add(player);
		
		////////// ALIENS, debug
		
		for(int i=0; i<5; i++) {
			Alien al = new Alien();
			al.load();
			agents.addActor(al);
		
			colliders.add(al);
		}
		
		////////// 
		
		this.addActor(background);
		this.addActor(agents);
		this.addActor(ui);
		
		bOverlord.createNewBuilding(5, 5, B_TYPE.BASE);
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
	    //SORTING ACTORS
		agents.sortChildren(new Comparator<Actor>() {
			@Override
			public int compare(Actor arg0, Actor arg1) {
				return (int)(arg1.y - arg0.y);
			}
		});
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}
}
