package com.subfty.krkjam2013.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.Background;
import com.subfty.krkjam2013.util.Art;

public class Bullet extends Group {
	
	public float dx;
	public float dy;
	public float originx;
	public float originy;
	
	static private Sprite sprite;
	
	static public void load() {
		sprite = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("smugi");
	}
	
	public Bullet() {
	}
	
	public void init(float dx, float dy) {
		this.dx = dx;
		this.dy = dy;
		
		originx = dx;
		originy = dy;
	}
	
	@Override
	public void act(float delta) {
		originx += dx * delta;
		originy += dx * delta;
		
		Background bg = Krakjam.gameScreen.background;
		
		x = originx - bg.x;
		y = originy - bg.y;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

}
