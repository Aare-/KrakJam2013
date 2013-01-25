package com.subfty.krkjam2013.util;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Art {
	
    //ID OF ATLASES
	public static int A_BACKGROUND = 0;
	public static int A_ALIEN = 0; // !!! zmienic id
	
	private String atlasesSrc[] = { "data/alien.pack" };  
	public TextureAtlas atlases[] = new TextureAtlas[atlasesSrc.length];
	
	public Art(){
		for(int i=0; i<atlasesSrc.length; i++){
			atlases[i] = new TextureAtlas(atlasesSrc[i]);
		}
		
		//TODO: loading textures
		
	}
	
	
}
