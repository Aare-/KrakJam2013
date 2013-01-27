package com.subfty.krkjam2013.game;

import java.util.Comparator;
import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.actor.Collider;
import com.subfty.krkjam2013.game.actor.Player;
import com.subfty.krkjam2013.game.actor.aliens.Alien.ALIEN_TYPE;
import com.subfty.krkjam2013.game.actor.aliens.AlienOverlord;
import com.subfty.krkjam2013.game.actor.buildings.Building;
import com.subfty.krkjam2013.game.actor.buildings.BuildingsOverlord;
import com.subfty.krkjam2013.game.actor.buildings.BuildingsOverlord.B_TYPE;
import com.subfty.krkjam2013.game.actor.crystals.CrystalOverlord;
import com.subfty.krkjam2013.game.actor.ui.BloodyScreen;
import com.subfty.krkjam2013.game.statsscreen.StatsScreen;
import com.subfty.krkjam2013.util.Art;
import com.subfty.krkjam2013.util.Screen;

public class GameScreen extends Screen{

	public boolean pause;
	
    //GROUPS
	public Background background;
	public Group agents;
	public Group ui;
	public StatsScreen stats;
	
	public Player player;
	public BuildingsOverlord bOverlord;
	public AlienOverlord aOverlord;
	public CrystalOverlord cOverlord;
	
	//UI
	BloodyScreen bScreen;
	Sprite expBackSprite;
	Sprite expFrontSprite;
	Sprite counterSprite;
	
	public Building base;
	
	public LinkedList<Collider> colliders = new LinkedList<Collider>();
	
	public GameScreen(Stage stage){
		super(stage);
		
		Krakjam.gameScreen = this;
		
		agents= new Group();
		background = new Background(this);
		
		ui = new Group();
		
		bOverlord = new BuildingsOverlord(background, agents);
		
		player = new Player(background, 0,0);
		stats = new StatsScreen(player);
		aOverlord = new AlienOverlord(background, this);
		cOverlord = new CrystalOverlord(player, agents);
		
		this.addActor(cOverlord);
		this.addActor(bOverlord);
		this.addActor(aOverlord);
		
		bScreen = new BloodyScreen(player);
		ui.addActor(bScreen);
		
		agents.addActor(player);
		colliders.add(player);
		
		////////// DEBUG
		ALIEN_TYPE alien_types[] = {ALIEN_TYPE.EXPLODING, ALIEN_TYPE.REGULAR,ALIEN_TYPE.EXPLODING, ALIEN_TYPE.REGULAR, ALIEN_TYPE.SHOOTER, ALIEN_TYPE.SHOOTER};
		for(int i=0; i<alien_types.length; i++)
			aOverlord.spawn(300 + Krakjam.rand.nextFloat(), 
						    300 + Krakjam.rand.nextFloat() , alien_types[i]);
		
		player.init();
		cOverlord.spawnNew();
		////////// 
		
		this.addActor(background);
		
		this.addActor(agents);
		
		this.addActor(ui);
		
		//bOverlord.createNewBuilding(5, 5, B_TYPE.BASE);
		//bOverlord.createNewBuilding(5+10, 5, B_TYPE.BASE);
		bOverlord.createNewBuilding(5, 7, B_TYPE.BASE);
		//bOverlord.createNewBuilding(5, 5+30, B_TYPE.BASE);
		
		bOverlord.createNewBuilding(0, 4, B_TYPE.TURRET);
		//bOverlord.createNewBuilding(10, 5+30, B_TYPE.TURRET);
		//bOverlord.createNewBuilding(0, -10, B_TYPE.TURRET);
		
		// LOADING HUD
		expBackSprite = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("exp-back");
		expFrontSprite = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("exp-front");
		counterSprite = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("counter");
	}
	
	@Override
	public void act(float delta){
		if(stats.visible)
			stats.act(delta);
		else {
			super.act(delta);
		
	    	//SORTING ACTORS
			agents.sortChildren(new Comparator<Actor>() {
				@Override
				public int compare(Actor arg0, Actor arg1) {
					return (int)(arg1.y - arg0.y);
				}
			});
		}
	}
	
	@Override
	public void draw (SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		//agents.x += 1;
		Krakjam.shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		
		counterSprite.setPosition(5, 600-counterSprite.getHeight()-5);
		counterSprite.draw(batch);
		
		Krakjam.art.fonts[Art.F_DIGITAL].setColor(0.5f, 0, 0, 1);
		Krakjam.art.fonts[Art.F_DIGITAL].setScale(0.4f);
		Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, ""+player.numberOfCrystals, 
				0, 600 - 18, 138, HAlignment.RIGHT);
		
		int prevExp = player.getLevelExp(player.getLevel()-1);
		int nextExp = player.getLevelExp(player.getLevel());
		
		float expState = (player.exp - prevExp)/(float)(nextExp-prevExp);
		
		expBackSprite.setPosition(800 - 5 - expBackSprite.getWidth(), 600 - expBackSprite.getHeight() - 5);
		expBackSprite.draw(batch);
		
		expFrontSprite.setPosition(800 - 5 - expBackSprite.getWidth(), 600 - expBackSprite.getHeight() - 5);
		expFrontSprite.setRegion(expFrontSprite.getRegionX(), expFrontSprite.getRegionY(),
				(int)(expBackSprite.getRegionWidth()*expState), expBackSprite.getRegionHeight());
		expFrontSprite.setSize((int)(expBackSprite.getRegionWidth()*expState), expBackSprite.getRegionHeight());
		expFrontSprite.draw(batch);
		
		Krakjam.art.fonts[Art.F_DIGITAL].setColor(0.5f, 0, 0, 1);
		Krakjam.art.fonts[Art.F_DIGITAL].setScale(0.4f);
		Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, "LEVEL "+player.getLevel(),
				800-300-5, 600 - 18, 300, HAlignment.CENTER);
		
		stats.draw(batch, parentAlpha);
	}
}
