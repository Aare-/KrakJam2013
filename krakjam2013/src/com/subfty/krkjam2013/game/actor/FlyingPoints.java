package com.subfty.krkjam2013.game.actor;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.equations.*;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.util.Art;

public class FlyingPoints extends Actor {
	
	public class FlyingPointsAccessor implements TweenAccessor<FlyingPoints> {

	    public static final int SCALE 		= 1;
	    public static final int POSITION_Y 	= 2;
	    public static final int ALPHA		= 3;

	    @Override
	    public int getValues(FlyingPoints target, int tweenType, float[] returnValues) {
	        switch (tweenType) {
	            case POSITION_Y: 	returnValues[0] = target.y; return 1;
	            case SCALE: 		returnValues[0] = scale; 	return 1;
	            case ALPHA: 		returnValues[0] = alpha; 	return 1;
	            default: assert false; return -1;
	        }
	    }
	    
	    @Override
	    public void setValues(FlyingPoints target, int tweenType, float[] newValues) {
	        switch (tweenType) {
	            case POSITION_Y: 	target.y = newValues[0]; 	break;
	            case SCALE: 		scale = newValues[0]; 		break;
	            case ALPHA: 		alpha = newValues[0]; 		break;
	            default: assert false; break;
	        }
	    }
	}
	
	private float timer;
	private int points;
	
	public float alpha;
	public float scale;
	
	public FlyingPoints(float x, float y, int points) {
		this.x = x;
		this.y = y;
		this.points = points;
		
		this.scale = 0.01f;
		this.alpha = 1;
		
		timer = 5.0f;
		
		Tween.registerAccessor(FlyingPoints.class, new FlyingPointsAccessor());
		
		Tween.to(this, FlyingPointsAccessor.SCALE, 0.5f)
		.target(1.0f)
		.ease(Elastic.OUT)
		.start(Krakjam.tM);
		Tween
		.to(this, FlyingPointsAccessor.POSITION_Y, 0.5f)
		.delay(0.5f)
	    .target(y+20)
	    .ease(Sine.OUT)
	    .start(Krakjam.tM);
		Tween
		.to(this, FlyingPointsAccessor.ALPHA, 0.5f)
		.delay(1.0f)
	    .target(0)
	    .ease(Sine.OUT)
	    .start(Krakjam.tM);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		//sprite.setColor(1, 1, 1, timer/maxTime);
		
		Background bg = Krakjam.gameScreen.background;
		
		Krakjam.art.fonts[Art.F_DIGITAL].setColor(1, 1, 1, 1.0f*alpha);
		Krakjam.art.fonts[Art.F_DIGITAL].setScale(0.25f*scale);
		Krakjam.art.fonts[Art.F_DIGITAL].drawWrapped(batch, "-"+points+"", this.x-bg.x, this.y-bg.y, 100, HAlignment.CENTER);
		
	}

	public void act(float delta) {
		timer -= delta;
		
		if(timer <= 0) {
			stage.removeActor(this);
		}
	}
	
	@Override
	public Actor hit(float x, float y) {
		return null;
	}
}