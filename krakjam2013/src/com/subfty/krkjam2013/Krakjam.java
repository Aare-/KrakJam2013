package com.subfty.krkjam2013;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Krakjam implements ApplicationListener {
	
  //SCREEN STUFF
	public static float SCREEN_WIDTH,
						SCREEN_HEIGHT,
						SCALE;
	public final static float STAGE_W=700f,
							  STAGE_H=1280f,
							  ASPECT_RATIO = STAGE_W/STAGE_H;
	
	public static Stage stage;
	public static TweenManager tM;
	
	@Override
	public void create() {		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		stage = new Stage(STAGE_W, STAGE_H, false);
		tM = new TweenManager();
		Gdx.input.setInputProcessor(stage);
	
	}

	@Override
	public void dispose() {
	
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}
	
	@Override
	public void resize(int w, int h) {
		float ratio = (float)w/(float)h;
		if(ratio > STAGE_W/STAGE_H)
			SCALE = STAGE_H/((float)h);
		else
			SCALE = STAGE_W/((float)w);
		
		Camera c = stage.getCamera(); 
		c.viewportWidth = SCREEN_WIDTH= w*SCALE;
		c.viewportHeight = SCREEN_HEIGHT = h*SCALE;
		c.position.set(STAGE_W/2,STAGE_H/2,0);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
