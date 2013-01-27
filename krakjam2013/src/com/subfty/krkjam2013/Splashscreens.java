package com.subfty.krkjam2013;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.subfty.krkjam2013.util.Art;
import com.subfty.krkjam2013.util.Screen;

public class Splashscreens extends Screen {

	float timer = 0;
	
	Sprite screens[] = new Sprite[5];
	
	int currentScreen = 0;
	int state = 0;
	
	
	public Splashscreens(Stage stage) {
		super(stage);
		
		screens[0] = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("intro1");
		screens[1] = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("intro2");
		screens[2] = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("intro3");
		screens[3] = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("intro4");
		screens[4] = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("intro5");
		
	}
	
	public void draw (SpriteBatch batch, float parentAlpha) {
		
		if(currentScreen >= screens.length)
			return;
		
		screens[currentScreen].draw(batch);
		
		batch.end();
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		
		float alpha = 0;
		
		if(state == 0)
			alpha = timer;
		if(state == 1)
			alpha = 1-timer;
		
		if(alpha < 0) alpha = 0;
		if(alpha > 1) alpha = 1;
		
		Krakjam.shapeRenderer.setColor(0, 0, 0,1-alpha);
		Krakjam.shapeRenderer.begin(ShapeType.FilledRectangle);
		Krakjam.shapeRenderer.filledRect(-10, -10, 1000, 1000);
		Krakjam.shapeRenderer.end();
		
		Gdx.gl.glDisable(GL10.GL_BLEND);
		
		batch.begin();
	}
	
	public void act(float delta){
		timer += delta*2;
		
		if(state == 1 && timer > 1) {
			currentScreen++;
			timer = 0;
			state = 0;
			
			if(currentScreen >= screens.length) {
				Krakjam.singleton.showScreen(Krakjam.S_GAME);
				
				return;
			}
		}
		
		if(Gdx.input.justTouched() && timer > 1 && state == 0) {
			timer = 0;
			state = 1;
		}
	}
}