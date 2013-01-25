package com.subfty.krkjam2013.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.util.Screen;

public class GameScreen extends Screen{

	public Background background;
	public Player player;
	
	public GameScreen(Stage stage){
		super(stage);
		
		background = new Background();
		player = new Player(0,0);
		
		this.addActor(background);
		this.addActor(player);
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}
}
