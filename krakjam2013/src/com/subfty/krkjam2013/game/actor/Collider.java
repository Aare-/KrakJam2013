package com.subfty.krkjam2013.game.actor;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.actor.buildings.Building;

public abstract class Collider extends Group {
	public float radius = 20;
	protected float dx = 0;
	protected float dy = 0;
	protected float lastdx = 0;
	protected float lastdy = 0;
	protected float width = 100;
	protected float height = 100;
	
	protected boolean byway = false;
	protected float bywayX = 0;
	protected float bywayY = 0;
	
	public void init() {
		byway = false;
	}
	
	public void onTouchBuilding(Building building) {
		
	}
	
	public void resolveCollisions() {		
		LinkedList<Collider> colliders = Krakjam.gameScreen.colliders;
		for(Collider c: colliders) {
			if(c != this && c.visible) {
				Vector2 tmp = Vector2.tmp;
				tmp.set(this.x, this.y).sub(c.x, c.y);
				float d = tmp.len();
				float min = radius + c.radius;
				if(d < min) {
					float conflict = min - d;
					float a = (float) Math.atan2(c.y - y, c.x - x);
					float w = 0.5f;
					dx -= Math.cos(a)*(conflict)*w;
					dy -= Math.sin(a)*(conflict)*w;
					c.dx += Math.cos(a)*(conflict)*w;
					c.dy += Math.sin(a)*(conflict)*w;
					
					if(c == Krakjam.gameScreen.player) {
						Krakjam.gameScreen.player.life -= Gdx.graphics.getDeltaTime()*10;
						Krakjam.gameScreen.player.shooted();
					}
				}
			}
		}
		
		Array<Building> buildings = Krakjam.gameScreen.background.getBuildings();
		
		x += dx;
		
		float minx = x - radius, maxx = x + radius;
		float miny = y - radius, maxy = y + radius;
		
		float intminx, intmaxx, intmaxy, intminy;
		
		float delta = 0.0f;
		
		float extRadius = radius + 3;
		
		// kolizje dla x
		for(Building b: buildings) {
			intminx = Math.max(minx, b.x);
			intminy = Math.max(miny, b.y);
			intmaxx = Math.min(maxx, b.x+b.width);
			intmaxy = Math.min(maxy, b.y+b.height);
			
			float cx = b.x + b.width/2.0f;
			float cy = b.y + b.height/2.0f;
			
			if(intminx < intmaxx && intminy < intmaxy && 
					intmaxx-intminx < intmaxy-intminy) {
				byway = true;
				if(cx < x) {
					x = b.x + b.width+radius+delta;
				} else {
					x = b.x - radius-delta;
				}
				
				if(dy > 0) {
					bywayY = b.y + b.height + extRadius;
				} else {
					bywayY = b.y - extRadius;
				}
				
				bywayX = x;
				
				onTouchBuilding(b);
			}
		}
		
		y += dy;
		
		minx = x - radius; maxx = x + radius;
		miny = y - radius; maxy = y + radius;
		
		// kolizje dla y
		for(Building b: buildings) {
			intminx = Math.max(minx, b.x);
			intminy = Math.max(miny, b.y);
			intmaxx = Math.min(maxx, b.x+b.width);
			intmaxy = Math.min(maxy, b.y+b.height);
			
			float cx = b.x + b.width/2.0f;
			float cy = b.y + b.height/2.0f;
			
			if(intminx < intmaxx && intminy < intmaxy) {
				byway = true;
				if(cy < y) {
					y = b.y + b.height+radius+delta;
				} else {
					y = b.y - radius - delta;
				}
				
				if(dx > 0) {
					bywayX = b.x + b.width+ extRadius;
				} else {
					bywayX = b.x - extRadius;
				}
				
				bywayY = y;
				
				onTouchBuilding(b);
			}
		}
		
		lastdx = dx;
		lastdy = dy;
		
		dx = dy = 0;
	}
	
	public void drawDebug(SpriteBatch batch) {
		batch.end();
		
		Krakjam.shapeRenderer.setColor(1, 0, 0, 1);
		Krakjam.shapeRenderer.begin(ShapeType.FilledCircle);
		Krakjam.shapeRenderer.filledCircle(x, y, radius);
		Krakjam.shapeRenderer.end();
		
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		Krakjam.shapeRenderer.setColor(1,1,1,0.3f);
		Krakjam.shapeRenderer.begin(ShapeType.FilledRectangle);
		Krakjam.shapeRenderer.filledRect(x - width/2.0f, y, width, height);
		Krakjam.shapeRenderer.end();
		
		Gdx.gl.glDisable(GL10.GL_BLEND);
		
		batch.begin();
	}
}
