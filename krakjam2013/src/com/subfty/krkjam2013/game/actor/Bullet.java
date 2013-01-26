package com.subfty.krkjam2013.game.actor;

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
	
	public final float bulletRadius = 10;
	
	static private Sprite sprite;
	
	static public void load() {
		sprite = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("smugi");
		sprite.setOrigin(sprite.getWidth()/2.0f, sprite.getHeight()/2.0f);
	}
	
	public Bullet() {
	}
	
	public void init(float dx, float dy, float x, float y, float angle) {
		this.dx = dx;
		this.dy = dy;
		this.angle = angle;
		
		originx = x;
		originy = y;
	}
	
	@Override
	public void act(float delta) {
		originx += dx * delta;
		originy += dy * delta;
		
		x = originx;
		y = originy;
		
		Array<Alien> aliens = Krakjam.gameScreen.aOverlord.aliens;
		
		for(int i=0; i<aliens.size; i++) {
			Alien a = aliens.get(i);
			
			if(a.visible == false)
				continue;
			
			Vector2 tmp = Vector2.tmp;
			tmp.set(a.x, a.y);
			tmp.sub(x, y);
			if(tmp.len() < bulletRadius + a.radius) {
				Background bg = Krakjam.gameScreen.background;
				FlyingPoints points = new FlyingPoints(a.x-a.width/2.0f+bg.x, a.y+a.height+bg.y, 1);
				stage.addActor(points);
				a.shoot();
				
				Krakjam.gameScreen.agents.removeActor(this);
				break;
			}
		}
		
		Array<Building> buildings = Krakjam.gameScreen.background.getBuildings();
		
		for(int i=0; i<buildings.size; i++) {
			Building b = buildings.get(i);
			
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
					Krakjam.gameScreen.agents.removeActor(this);
					break;
				}
					
			}
		}
	
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setRotation(angle);
		sprite.setPosition(x - sprite.getWidth()/2.0f, y - sprite.getHeight()/2.0f);
		sprite.draw(batch);
	}

}
