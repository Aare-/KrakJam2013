package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.game.actor.player.Stats;
import com.subfty.krkjam2013.util.Art;

public class Player extends Collider {
	//SETTINGS
	private final float MARGIN_X = 250,
						MARGIN_Y = 200,
						MAX_LIFE = 60*3;
	private final float SPAWN_X, SPAWN_Y;
	
	private float step;
	private float cursorDistance;

	private float angle,
				  angle2,
				  move,
				  life;
	
	public Stats stats;
	
    //VISUALS
	private Sprite image;
	private Cursor cursor;
	
	private Sprite smuga;
	
	private Background bg;
	
	public Player(Background bg, float spawn_x, float spawn_y){
		this.bg = bg;
		
		this.SPAWN_X = spawn_x;
		this.SPAWN_Y = spawn_y;
		
		step=500f;
		cursorDistance=50;
	
		stats = new Stats();
		cursor=new Cursor();		
		image = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("alien");
		image.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		smuga = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("smugi");
		smuga.setOrigin(smuga.getWidth()/2.0f, 0);
		
		this.addActor(cursor);
	}
	
    //STAGES OF LIFE
	public void init(){
		stats.clearAll();
		
		this.x = SPAWN_X;
		this.y = SPAWN_Y;
		life = MAX_LIFE;
	}
	public void kill(){
		init();
	}
	
	@Override
	public void act(float delta) {
		move=step*delta;
		angle=0f;

		updateInput();
		
		x+=move*Math.cos(angle);
		y+=move*Math.sin(angle);
		
		image.setPosition(x - image.getWidth()/2.0f, y);
		
		int wspx = Gdx.input.getX();
		int wspy = Gdx.input.getY();
		Vector2 v=new Vector2();
		Krakjam.stage.toStageCoordinates(wspx, wspy, v);
		
		angle2=(float)Math.atan2(v.y-y, v.x-x);
				
		cursor.setX((float)(cursorDistance*Math.cos(angle2))+x);
		cursor.setY((float)(cursorDistance*Math.sin(angle2))+y);
		
		resolveCollisions();
		scrollBackground(delta);
		
		if(Gdx.input.justTouched()) {
			smuga.setPosition(x-smuga.getWidth()/2.0f, y);
			FadeOutSprite fadeOut = new FadeOutSprite(0.2f, smuga);
			fadeOut.angle = (float)(angle2*180/Math.PI) - 90;
			stage.addActor(fadeOut);
		}
		
		//UPDATING LIFE
		life -= delta;
		if(life < 0){
			kill();
		}
	}
	private void updateInput(){
		if(Gdx.input.isKeyPressed(Keys.W) && Gdx.input.isKeyPressed(Keys.D))
			angle=(float)Math.PI/4.0f;
		else if(Gdx.input.isKeyPressed(Keys.D) && Gdx.input.isKeyPressed(Keys.S))
			angle=7*(float)Math.PI/4.0f;
		else if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.S))
			angle=5*(float)Math.PI/4.0f;
		else if(Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.W))
			angle=3*(float)Math.PI/4.0f;
		else if(Gdx.input.isKeyPressed(Keys.W))
			angle=(float)Math.PI/2.0f;
		else if(Gdx.input.isKeyPressed(Keys.S))
			angle=3*(float)Math.PI/2.0f;
		else if(Gdx.input.isKeyPressed(Keys.A))
			angle=(float)Math.PI;
		else if(Gdx.input.isKeyPressed(Keys.D))
			angle=0f;
		else
			move=0f;
	}
	private void scrollBackground(float delta){
		float speed = 3f;
		float bgScrollX = 0,
			  bgScrollY = 0;
		
		if(x < MARGIN_X){
			float diff = (MARGIN_X-x); 
			bgScrollX = -diff*speed*delta;
			x += diff*speed*delta;
		}
		if(x > Krakjam.STAGE_W-MARGIN_X){
			float diff = (x-(Krakjam.STAGE_W-MARGIN_X)); 
			bgScrollX = diff*speed*delta;
			x -= diff*speed*delta;
		}
		if(y < MARGIN_Y){
			float diff = (MARGIN_Y-y); 
			bgScrollY = -diff*speed*delta;
			y += diff*speed*delta;
		}
		if(y > Krakjam.STAGE_H-MARGIN_Y){
			float diff = (y-(Krakjam.STAGE_H-MARGIN_Y)); 
			bgScrollY = diff*speed*delta;
			y -= diff*speed*delta;
		}
		
		bg.scroll(bgScrollX, bgScrollY);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		drawDebug(batch);
		
		image.draw(batch);
		cursor.draw(batch, parentAlpha);
	}
	
	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}
}
