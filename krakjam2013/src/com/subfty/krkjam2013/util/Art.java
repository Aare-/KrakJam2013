package com.subfty.krkjam2013.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Art {
	
    //ID OF ATLASES
	public static int A_ENTITIES = 0,
					  A_BACKGROUND = 1,
					  A_AGENTS = 2,
					  A_BSCREEN = 3;
	
	private String atlasesSrc[] = { "data/alien.pack",
									"textures/bg",
									"textures/agents",
									"textures/bloodyscreen"};  
	public TextureAtlas atlases[] = new TextureAtlas[atlasesSrc.length];
	
	//ID OF FONTS
	public static int F_DIGITAL = 0;
	private String fontsSrc[] = {"fonts/digital.fnt"};
	public BitmapFont fonts[] = new BitmapFont[fontsSrc.length];
	
	public Art(){
		for(int i=0; i<atlasesSrc.length; i++)
			atlases[i] = new TextureAtlas(Gdx.files.internal(atlasesSrc[i]));
		
		for(int i=0; i<fontsSrc.length; i++)
			fonts[i] = new BitmapFont(Gdx.files.internal(fontsSrc[i]), false);
		
		
	}
}
