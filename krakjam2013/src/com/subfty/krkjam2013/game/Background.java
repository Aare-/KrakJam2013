package com.subfty.krkjam2013.game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.SerializationException;
import com.subfty.krkjam2013.Krakjam;
import com.subfty.krkjam2013.game.actor.buildings.Building;
import com.subfty.krkjam2013.util.Art;

public class Background extends Group{
	Logger l = new Logger("DEBUG", Logger.DEBUG);
	
	private class SpecialTile{
		public float x;
		public float y;
		public final float width;
		public final float height;
		public TextureRegion sprite;
		
		public SpecialTile(int atlas, String sprite, int id, float width, float height){
			this.sprite = Krakjam.art.atlases[atlas].findRegion(sprite, id);
			this.width = width;
			this.height = height;
		}
	}
	
	private final String SAVE_FILE_PATH = "save";
	private Hashtable <Long , SpecialTile> markers = new Hashtable <Long , SpecialTile>();
	
	public final static float TILE_SIZE = 50;
	private final long SEED = -123412431;
	private Random random;
	
	private Sprite bgSprites[][]= new Sprite[(int) (Krakjam.STAGE_W / TILE_SIZE + 3)]
			  								[(int) (Krakjam.STAGE_H / TILE_SIZE + 3)]; 
	private Sprite tmp = new Sprite();
	
	private TextureRegion regions[];
	
	private Array<Building> buildings = new Array<Building>();
	private GameScreen gs;
	
    //INIT
	public Background(GameScreen gs){
		this.gs = gs;
		random = new Random(this.SEED);
		init();
		
		
	}
	public Background(long SEED){
		random = new Random(SEED);
		init();
	}
	private void init(){
		//READING PREVIOUSLY SAVED DATA
		Json json = new Json();
		
//		FileInputStream fos;
//		try {
//			fos = new FileInputStream("t.tmp");
//		
//			ObjectInputStream ois;
//		
//			ois = new ObjectInputStream(fos);
//		
//			markers = (Hashtable<Long, SpecialTile>) ois.readObject();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try{
			try{
			FileHandle f = Gdx.files.local(SAVE_FILE_PATH);
			String s= f.readString();
			//l.info("string read: "+s);
			
			markers = json.readValue(Hashtable.class, s);
			}catch(GdxRuntimeException gdr){
				markers = null;
				//l.error("runtime exception");
			}
		}catch(SerializationException sxc){
			markers = null;
			//l.error("serializatiopn exception");
		}
		
		if(markers == null)
			markers = new Hashtable<Long, SpecialTile>();
		
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++){
				bgSprites[i][j] = new Sprite();
				bgSprites[i][j].setSize(TILE_SIZE*1.01f, TILE_SIZE*1.01f);
			}
		
		regions = new TextureRegion[2];
		regions[0] = Krakjam.art.atlases[Art.A_BACKGROUND].findRegion("rock",1);
		regions[1] = Krakjam.art.atlases[Art.A_BACKGROUND].findRegion("rock",2);
		
		buildings.clear();
		
		act(0);
	}
	
	public void saveData(){
		Json json = new Json();
		//FileHandle f = Gdx.files.local(SAVE_FILE_PATH);
		String data = "";
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("t.tmp");
		
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(fos);
	
			oos.writeObject(markers);
			oos.close();	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//f.writeString(data, false);
		
		
		json.toJson(markers, Gdx.files.local(SAVE_FILE_PATH));
		//json.to
		//l.info("json: "+json.toJson(markers));
	}
	
	public void act(float delta) {
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++){
				random.setSeed(SEED + 
							   (int)(-gs.agents.x / TILE_SIZE) +
							   (int)(-gs.agents.y / TILE_SIZE) * bgSprites.length +
							   i + j*bgSprites.length);
				
				bgSprites[i][j].setRegion(regions[0]);
				bgSprites[i][j].setX((i-1) * TILE_SIZE + gs.agents.x % TILE_SIZE);
				bgSprites[i][j].setY((j-1) * TILE_SIZE + gs.agents.y % TILE_SIZE);
				
				bgSprites[i][j].setColor(255.0f/255.0f - 60.0f*random.nextFloat()/255.0f, 
										 45.0f/255.0f  + 50.0f*random.nextFloat()/255.0f, 
										 23.0f/255.0f  + 50.0f*random.nextFloat()/255.0f, 1);
			}
	}
	
	private Array<SpecialTile> arr = new Array<SpecialTile>();
	public void draw(SpriteBatch batch, float parentAlpha) {
		arr.clear();
		for(int i=0; i<bgSprites.length; i++)
			for(int j=0; j<bgSprites[i].length; j++){
				bgSprites[i][j].draw(batch, parentAlpha);
				
				SpecialTile st = markers.get(new Long(getToHashTable((int)(Math.floor(-gs.agents.x / TILE_SIZE)+i), 
																	 (int)(Math.floor(-gs.agents.y / TILE_SIZE)+j))));
				arr.add(st);
				
			}
		
		for(int i=0; i<arr.size; i++){
			SpecialTile st = arr.get(i);
			if(st != null){
				tmp.setRegion(st.sprite);
				tmp.setPosition(st.x+gs.agents.x, st.y+gs.agents.y);
				tmp.setSize(st.width, 
							st.height);
				tmp.draw(batch, parentAlpha*1f);
			}
		}
		
	}
	
	//SPECIAL BACKGROUND TILES
	public void addMarker(int x, int y, int atlas, String sprite, int id, float width, float height){
		SpecialTile st = new SpecialTile(atlas, sprite, id, width, height);
		st.x = x *Background.TILE_SIZE;
		st.y = y * Background.TILE_SIZE;
		markers.put(getToHashTable(x, y), st);
	}
	
	//HELPERS
	private long getToHashTable(int x, int y){
		return (long)((x+10000)*20000 + (y+10000));
	}
	
	//OCCUPATION
	public boolean isOccupied(int x, int y){
		for(int i=0; i<buildings.size; i++)
			if(bContains(buildings.get(i), x, y))
				return true;
		
		return false;
	}
	private boolean bContains(Building b, int x, int y){
		if(b.tileX <= x && b.tileX + b.tileWidth > x &&
		   b.tileY <= y && b.tileY + b.tileHeight > y)
			return true;
		return false;
	}
	
	public Array<Building> getBuildings() {
		return buildings;
	}
	
	public void registerBuilding(Building building){
		buildings.add(building);
	}
	public void unregisterBuilding(Building building){
		buildings.removeValue(building, true);
	}
}
