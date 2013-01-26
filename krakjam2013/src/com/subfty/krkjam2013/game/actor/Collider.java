package com.subfty.krkjam2013.game.actor;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.Krakjam;

public abstract class Collider extends Actor {
	public float radius = 10;
	private float dx = 0;
	private float dy = 0;
	
	public void resolveCollisions() {
		/*
		 * for( e in ALL ) {
				if( e!=this && !e.killed && e.repels && isClose(e) ) {
					var pt = getPoint();
					var ept = e.getPoint();
					var d = Lib.distance(pt.x, pt.y, ept.x, ept.y);
					var min = radius+e.radius;
					if( d < min ) {
						var conflict = min - d;
						var center = {x:pt.x + (ept.x-pt.x)*0.5, y:pt.y + (ept.y-pt.y)*0.5}
						var a = Math.atan2(ept.y-pt.y, ept.x-pt.x);
						var w = weight / (weight+e.weight);
						var ew = e.weight / (weight+e.weight);
						dx -= Math.cos(a)*(conflict/Game.GRID)*ew;
						dy -= Math.sin(a)*(conflict/Game.GRID)*ew;
						e.dx += Math.cos(a)*(conflict/Game.GRID)*w;
						e.dy += Math.sin(a)*(conflict/Game.GRID)*w;
						if( e==game.hero )
							onHeroContact();
					}
				}
			}
		 */
		
		LinkedList<Collider> colliders = Krakjam.gameScreen.colliders;
		for(Collider c: colliders) {
			if(c != this) {
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
					c.dx -= Math.cos(a)*(conflict)*w;
					c.dy -= Math.sin(a)*(conflict)*w;
				}
			}
		}
		
		x += dx;
		y += dy;
		
		dx = dy = 0;
	}
	
	public void drawDebug(SpriteBatch batch) {
		batch.end();
		
		Krakjam.shapeRenderer.begin(ShapeType.FilledCircle);
		Krakjam.shapeRenderer.filledCircle(x, y, radius);
		Krakjam.shapeRenderer.end();
		
		batch.begin();
	}
}