package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.game.actor.aliens.Alien;
import com.subfty.krkjam2013.game.actor.buildings.Building;
import com.subfty.krkjam2013.util.Art;

public class Bullet extends Group {
	
	public float dx;
	public float dy;
	public float originx;
	public float originy;
	public float angle;
	public boolean antyPlayer; // true - niszczy gracza, false - alienów
	
	public final float bulletRadius = 10;
	private float lifeTime;
	
	private Building creatorBuilding = null;
	
	static private Sprite sprite[] = new Sprite[2];
	private Sprite mySprite;
	
	static public void load() {
		sprite[0] = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("smugi");
		sprite[0].setOrigin(sprite[0].getWidth()/2.0f, sprite[0].getHeight()/2.0f);
	}
	
	public Bullet() {
		this.visible = false;
	}
	
	public void init(float dx, float dy, float x, float y, float angle, int type, Building creator) {
		this.dx = dx;
		this.dy = dy;
		this.angle = angle;
		
		originx = x;
		originy = y;
		lifeTime = 10;
		this.visible = true;
		mySprite = sprite[type];
		
		creatorBuilding = creator;
	}
	public void kill(){
		this.visible = false;
	}
	
	@Override
	public void act(float delta) {
		if(!this.visible || Krakjam.gameScreen.pause) return;
		
		lifeTime -= delta;
		if(lifeTime < 0)
			kill();
		
		originx += dx * delta;
		originy += dy * delta;
		
		x = originx;
		y = originy;
		
		Array<Alien> aliens = Krakjam.gameScreen.aOverlord.aliens;
		
		if(antyPlayer == true) {
			Player p = Krakjam.gameScreen.player;
			
			Vector2 tmp = Vector2.tmp;
			tmp.set(p.x, p.y);
			tmp.sub(x, y);
			if(tmp.len() < bulletRadius + p.radius) {
				Background bg = Krakjam.gameScreen.background;
				FlyingPoints points = new FlyingPoints(p.x-p.width/2.0f+bg.x, p.y+p.height+bg.y, 1);
				stage.addActor(points);
				
				Krakjam.gameScreen.player.life -= 20;
				Krakjam.gameScreen.player.shooted();
				
				// dla gracza daje ten sam dzwiek
				Krakjam.art.hitAlien.play();
				kill();
			}
		} else {
			for(int i=0; i<aliens.size; i++) {
				Alien a = aliens.get(i);
				
				if(a.visible == false)
					continue;
				
				Vector2 tmp = Vector2.tmp;
				tmp.set(a.x, a.y);
				tmp.sub(x, y);
				if(tmp.len() < bulletRadius + a.radius) {
					Background bg = Krakjam.gameScreen.background;
					FlyingPoints points = new FlyingPoints(a.x-a.width/2.0f+bg.x, a.y+a.height+bg.y, 10);
					stage.addActor(points);
					a.shoot();
					
					Player p = Krakjam.gameScreen.player;
					p.addExp(10);				
					
					Krakjam.art.hitAlien.play();
					
					kill();
					break;
				}
			}	
		}
		
		Array<Building> buildings = Krakjam.gameScreen.background.getBuildings();
		
		for(int i=0; i<buildings.size; i++) {
			Building b = buildings.get(i);
			
			if(b == creatorBuilding)
				continue;
			
			// test jako prostokaty
			float 	minx = x - bulletRadius, 
					maxx = x + bulletRadius, 
					miny = y - bulletRadius, 
					maxy = y + bulletRadius;
			
			if(maxx > b.x && maxy > b.y && minx < b.x + b.width && miny < b.y + b.height) {
				float 	cx = b.x + b.width/2.0f, 
						cy = b.y + b.height/2.0f;
				float radius = b.width/2.0f;
				
				Vector2 tmp = Vector2.tmp;
				tmp.set(cx, cy);
				tmp.sub(x, y);
				
				if(tmp.len() < radius + bulletRadius) {
					kill();
					break;
				}
					
			}
		}	
		
		Player p = Krakjam.gameScreen.player;
		
		if(Vector2.tmp.set(x, y).sub(p.x, p.y).len() > 5000) {
			kill();
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		mySprite.setRotation(angle);
		mySprite.setPosition(x - mySprite.getWidth()/2.0f, y - mySprite.getHeight()/2.0f);
		mySprite.draw(batch);
	}

}
