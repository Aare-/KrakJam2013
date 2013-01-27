package com.subfty.krkjam2013.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Art {
	
	//MUSIC
	public static Music heartbeat;
	//public static Music bgM;
	
    //ID OF ATLASES
	public static int A_ENTITIES = 0,
					  A_BACKGROUND = 1,
					  A_AGENTS = 2,
					  A_BSCREEN = 3,
					  A_SKILLS = 4;
	
	private String atlasesSrc[] = { "data/alien.pack",
									"textures/bg",
									"textures/agents",
									"textures/bloodyscreen",
									"textures/skills"};  
	public TextureAtlas atlases[] = new TextureAtlas[atlasesSrc.length];
	
	public Sound ping;
	public Sound explosion;
	public Sound shoot;
	public Sound dead;
	public Sound diamond;
	public Sound hitAlien;
	public Sound hitBuilding;
	public Sound levelUp;
	public Sound turret;
	public Sound hammer;
	
	//ID OF FONTS
	public static int F_DIGITAL = 0;
	private String fontsSrc[] = {"fonts/digital.fnt"};
	public BitmapFont fonts[] = new BitmapFont[fontsSrc.length];
	
	public Art(){
		for(int i=0; i<atlasesSrc.length; i++)
			atlases[i] = new TextureAtlas(Gdx.files.internal(atlasesSrc[i]));
		
		for(int i=0; i<fontsSrc.length; i++)
			fonts[i] = new BitmapFont(Gdx.files.internal(fontsSrc[i]), false);
		
	    //MUSIC
		heartbeat = Gdx.audio.newMusic(Gdx.files.internal("sounds/heartbeat.mp3"));
		//bgM = Gdx.audio.newMusic(Gdx.files.internal("sounds/ambient.mp3"));
		
		//SOUNDS
		ping  		= Gdx.audio.newSound(Gdx.files.internal("sounds/ping.wav"));
		explosion  	= Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
		shoot  		= Gdx.audio.newSound(Gdx.files.internal("sounds/shoot.wav"));
		dead		= Gdx.audio.newSound(Gdx.files.internal("sounds/dead.wav"));
		diamond		= Gdx.audio.newSound(Gdx.files.internal("sounds/diamond.wav"));
		hitAlien	= Gdx.audio.newSound(Gdx.files.internal("sounds/hit-alien.wav"));
		hitBuilding	= Gdx.audio.newSound(Gdx.files.internal("sounds/hit-building.wav"));
		levelUp		= Gdx.audio.newSound(Gdx.files.internal("sounds/levelup.wav"));
		turret		= Gdx.audio.newSound(Gdx.files.internal("sounds/turret.wav"));
		hammer		= Gdx.audio.newSound(Gdx.files.internal("sounds/hammer.wav"));
	}
}
