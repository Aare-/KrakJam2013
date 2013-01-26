package com.subfty.krkjam2013.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Art {
	
    //ID OF ATLASES
	public static int A_ENTITIES = 0,
					  A_BACKGROUND = 1,
					  A_AGENTS = 2;
	
	private String atlasesSrc[] = { "data/alien.pack",
									"textures/bg",
									"textures/agents"};  
	public TextureAtlas atlases[] = new TextureAtlas[atlasesSrc.length];
	
	public Art(){
		for(int i=0; i<atlasesSrc.length; i++)
			atlases[i] = new TextureAtlas(Gdx.files.internal(atlasesSrc[i]));
		
	}
}
