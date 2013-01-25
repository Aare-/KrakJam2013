package com.subfty.krkjam2013;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.subfty.krkjam2013.util.Art;

public class Alien extends com.badlogic.gdx.scenes.scene2d.Actor{
	public Vector2 position = new Vector2();
	public float speed = 10;
	private Sprite sprite;
	
	 public void load() {
		 sprite = Krakjam.art.atlases[Art.A_ENTITIES].createSprite("alien");
	}
	
	public void tick(float delta) {
		Vector2 tmp = Vector2.tmp;
		tmp.set(Krakjam.getPlayerPos()).sub(position).nor().mul(delta*speed);
		position.add(tmp);
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		tick(Gdx.graphics.getDeltaTime());
		
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}

	@Override
	public Actor hit(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}
}
