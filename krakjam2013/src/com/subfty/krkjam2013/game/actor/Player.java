package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.game.actor.buildings.Building;
import com.subfty.krkjam2013.game.actor.player.Stats;
import com.subfty.krkjam2013.util.Art;

public class Player extends Collider {
	//SETTINGS
	private final float MARGIN_X = 350,
						MARGIN_Y = 300,
						MAX_LIFE = 60*3;
	private final float SPAWN_X, SPAWN_Y;
	
	private float step;
	private float cursorDistance;

	private float angle,
				  angle2,
				  move;
	public float life;
	
	public Stats stats;
	public int exp = 0;
	public int numberOfCrystals;
	
	//SHOOTING
	private Array<Bullet> bullets = new Array<Bullet>();
	public float rateOfFire;
	private float gunX = -25.0f;
	private float gunY = 40.0f;
	
    //VISUALS
	private TextureRegion playerSprites[];
	private Sprite image;
	public Cursor cursor;
	
	private Sprite smuga;
	private Sprite arrow;
	
	private Background bg;
	
	public Player(Background bg, float spawn_x, float spawn_y){
		this.bg = bg;
		
		this.SPAWN_X = spawn_x;
		this.SPAWN_Y = spawn_y;
		
		step=280f;
		cursorDistance=100;
	
		stats = new Stats();
		cursor=new Cursor();		
		playerSprites = new TextureRegion[]{Krakjam.art.atlases[Art.A_AGENTS].findRegion("clone", 1),
											Krakjam.art.atlases[Art.A_AGENTS].findRegion("clone", 2),
											Krakjam.art.atlases[Art.A_AGENTS].findRegion("clone", 3),
											Krakjam.art.atlases[Art.A_AGENTS].findRegion("clone", 4)};
		image = new Sprite();
		image.setRegion(playerSprites[0]);
		
		image.setSize(Background.TILE_SIZE, 200/100*Background.TILE_SIZE);
		smuga = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("smugi");
		arrow = Krakjam.art.atlases[Art.A_BSCREEN].createSprite("strzalka");
		smuga.setOrigin(smuga.getWidth()/2.0f, 0);
	}
	
	final int EXP_LEVEL_1 = 50;
	final int EXP_LEVEL_2 = 70;
	
	public int getLevelExp(int level) {
		int a = EXP_LEVEL_1;
		int b = EXP_LEVEL_2;
		
		if(level < 0) return 0;
		
		if(level == 0) return a;
		if(level == 1) return b;
		
		while(level>0) {
			level--;
			
			int c = a;
			a = b;
			b = c+a;
		}
		
		return a;
	}
	
	public int getLevel() {
		int e = exp;
		
		// 50
		// 70
		// 120
		// 190
		int a = EXP_LEVEL_1;
		int b = EXP_LEVEL_2;
		int c;
		int k = 0;
		
		while(e >= a) {
			c = a;
			a = b;
			b = c+b;
			
			k++;
		}
		
		return k;
	}
	
    //STAGES OF LIFE
	public void init(){
		stats.clearAll();
		
		this.x = SPAWN_X;
		this.y = SPAWN_Y;
		life = MAX_LIFE;
		exp = 0;
		numberOfCrystals = 0;
	}
	public void kill(){
		int deathX = (int)((this.x-20) /Background.TILE_SIZE);
		int deathY = (int)((this.y-40) /Background.TILE_SIZE);
		bg.addMarker(deathX,
					 deathY, Art.A_BACKGROUND, "body", -1, Background.TILE_SIZE*2, Background.TILE_SIZE*2);
		
		if(numberOfCrystals > 0)
			Krakjam.gameScreen.cOverlord.spawnNew(this.x-20, 
												  this.y-40, 
												  numberOfCrystals,
												  true);
		
		init();
	}
	
	@Override
	public void act(float delta) {
		if(!this.visible || Krakjam.gameScreen.pause)
			return;
		move=step*delta;
		angle=0f;

		cursor.act(delta);
		updateInput();
		
		dx+=move*Math.cos(angle);
		dy+=move*Math.sin(angle);
		
		if(dx < -0.1f)
			image.setRegion(playerSprites[2]);
		else if(dx > 0.1f)
			image.setRegion(playerSprites[3]);
		else if(dy > 0.1f)
			image.setRegion(playerSprites[1]);
		else
			image.setRegion(playerSprites[0]);
		
		int wspx = Gdx.input.getX();
		int wspy = Gdx.input.getY();
		Vector2 v=new Vector2();
		
		Krakjam.stage.toStageCoordinates(wspx, wspy, v);
		this.toLocalCoordinates(v);
		
		angle2=(float)Math.atan2(v.y, v.x);
				
		cursor.setX((float)(cursorDistance*Math.cos(angle2))+x);//+gunX);
		cursor.setY((float)(cursorDistance*Math.sin(angle2))+y);//+gunY);
		
		resolveCollisions();
		scrollBackground(delta);
		
		
		rateOfFire += delta;
		if(Gdx.input.isTouched())
			fire(delta);		
		
		//UPDATING LIFE
		life -= delta;
		if(life < 0)
			kill();
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
	
	//SHOOTING
	private void fire(float delta){
		if(cursor.mode == Cursor.M_SHOOT &&
		   rateOfFire > 0){
		   rateOfFire = -stats.getRateOfFire();
			
			Bullet bullet = obtainBullet();
			final float bulletSpeed = 400;
			bullet.antyPlayer = false;
			bullet.init((float)Math.cos(angle2)*bulletSpeed, (float)Math.sin(angle2)*bulletSpeed, 
					x+gunX, y+gunY, (float)(angle2*180/Math.PI) + 90);
		}
		
	}
	public Bullet obtainBullet(){
		for(int i=0; i<bullets.size; i++)
			if(!bullets.get(i).visible)
				return bullets.get(i);
		
		Bullet b = new Bullet();
		bullets.add(b);
		Krakjam.gameScreen.agents.addActor(b);
		return obtainBullet();
	}

	public void cristalCollected(int ammount){
		numberOfCrystals += ammount;
	}
	
	private void scrollBackground(float delta){
		float speed = 3f;

		float screenx = ((this.x+parent.x) / Krakjam.SCALE),
			  screeny = ((this.y+parent.y) / Krakjam.SCALE);

		if(screenx < MARGIN_X/Krakjam.SCALE)
			this.parent.x += (MARGIN_X/Krakjam.SCALE-screenx)*speed*delta;
		if(screenx > (Krakjam.STAGE_W-MARGIN_X)/Krakjam.SCALE)
			this.parent.x -=(screenx-((Krakjam.STAGE_W-MARGIN_X)/Krakjam.SCALE))*speed*delta;
		if(screeny < MARGIN_Y/Krakjam.SCALE)
			this.parent.y +=  (MARGIN_Y/Krakjam.SCALE-screeny)*speed*delta;
		if(screeny > (Krakjam.STAGE_H-MARGIN_Y)/Krakjam.SCALE)
			this.parent.y -=(screeny-((Krakjam.STAGE_H-MARGIN_Y)/Krakjam.SCALE))*speed*delta;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		///drawDebug(batch);
		
		image.setPosition(x - image.getWidth()/2.0f, y-15);
		image.draw(batch);
		cursor.draw(batch, parentAlpha);
		
		Vector2 tmp = Vector2.tmp;
		
		Building base = Krakjam.gameScreen.base;
		
		float 	bx = base.x + base.width/2.0f,
				by = base.y + base.height/2.0f;
		
		tmp.set(bx, by).sub(x, y);
		
		final float FADE_DIST = 100.0f;
		final float ARROW_MIN_DIST = 400.0f;
		
		if(tmp.len() > ARROW_MIN_DIST) {
			float arrowAlpha = 1.0f;
			if(tmp.len() < ARROW_MIN_DIST+FADE_DIST) {
				arrowAlpha = 1.0f-(ARROW_MIN_DIST+FADE_DIST - tmp.len())/FADE_DIST;
			}
			tmp.nor();
			float angle=(float)Math.atan2(tmp.y, tmp.x);
			tmp.mul(70);
			arrow.setColor(1,1,1,arrowAlpha);
			arrow.setPosition(tmp.x+x-arrow.getWidth()/2.0f, tmp.y+y-arrow.getHeight()/2.0f);
			arrow.setOrigin(arrow.getWidth()/2.0f, arrow.getHeight()/2.0f);
			arrow.setRotation(angle*180.0f/(float)Math.PI-90);
			arrow.draw(batch);
		}
	}
	
	@Override
	public Actor hit(float x, float y) {
		return null;
	}
}