package com.subfty.krkjam2013.game;

import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.util.Screen;

public class GameScreen extends Screen{

    //GROUPS
	public Background background;
	public Group agents;
	public Group ui;
	
	public Player player;
	
	public GameScreen(Stage stage){
		super(stage);
		
		background = new Background();
		agents= new Group();
		ui = new Group();
		
		player = new Player(0,0);
		agents.addActor(player);
		
		this.addActor(background);
		this.addActor(agents);
		this.addActor(ui);
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
	    //SORTING ACTORS
		agents.sortChildren(new Comparator<Actor>() {
			@Override
			public int compare(Actor arg0, Actor arg1) {
				return (int)(arg0.y - arg1.y);
			}
		});
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}
}
