package ld27jam;

import java.util.HashSet;
import java.util.Set;

import ld27jam.entities.Entity;
import ld27jam.entities.Hourglass;
import ld27jam.entities.Inventory;
import ld27jam.entities.Item;
import ld27jam.entities.ItemType;
import ld27jam.entities.Tile;
import ld27jam.entities.TileType;
import ld27jam.res.ImageSheet;
import ld27jam.spatialData.AABB;
import ld27jam.spatialData.Region;
import ld27jam.spatialData.SpatialMap;
import ld27jam.entities.Character;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class World
{
	public GameDirector gd;
	
	public static final Vector2f SCREEN_TILE_SIZE = new Vector2f(32, 16);
	public static final Vector2f SCREEN_HALF_TILE = new Vector2f(16, 8);
	public static final int WALL_HEIGHT = 80;
	
	private Set<Entity> entities = new HashSet<Entity>();
	public SpatialMap<Entity> spatialMap = new SpatialMap<Entity>();
	public TileType[][] grid;
	private ImageSheet tileSheet, wallSheet;
	public Character character;
	public Inventory inventory = new Inventory();
	private Hourglass hourglass = new Hourglass();
	private float shakeIntensity, shakeDuration, shake;
	private Vector2f revealDecal = new Vector2f(0, 16);
	
	public float getShake()
	{
		return shake;
	}
	public void setShake(float intensity, float duration)
	{
		shakeIntensity = intensity;
		shakeDuration = duration;
	}
	
	public void add(Entity entity)
	{
		entities.add(entity);
		spatialMap.put(entity);
	}
	public void remove(Entity entity)
	{
		entities.remove(entity);
		spatialMap.remove(entity);
	}
	
	public Set<Entity> getEntitiesInRegion(AABB region)
	{
		return spatialMap.get(region);
	}
	public Set<Tile> getTilesInRegion(AABB region)
	{
		Set<Tile> tiles = new HashSet<Tile>();
		
		int minX = (int) Math.floor(region.getPosition().x),
		    maxX = (int) Math.ceil(region.getRightX()),
		    minY = (int) Math.floor(region.getPosition().y),
		    maxY = (int) Math.ceil(region.getBottomY());
		for (int x = minX; x < maxX; x++)
			for (int y = minY; y < maxY; y++)
				tiles.add(new Tile(grid[x][y], x, y));
		return tiles;
	}
	
	public void changeTileTypeAt(int x, int y, TileType type)
	{
		grid[x][y] = type;
	}
	public void propagateTileChangeAt(int x, int y, TileType oldType, TileType newType)
	{
		if (grid[x][y] == oldType)
			grid[x][y] = newType;
		else
			return;
		if (x > 0)
			propagateTileChangeAt(x - 1, y, oldType, newType);
		if (y > 0)
			propagateTileChangeAt(x, y - 1, oldType, newType);
		if (x + 1 < grid.length)
			propagateTileChangeAt(x + 1, y, oldType, newType);
		if (y + 1 < grid[x].length)
			propagateTileChangeAt(x, y + 1, oldType, newType);
	}
	
	public static Vector2f getScreenCoordinates(Vector2f mapCoordinates)
	{
		return new Vector2f((mapCoordinates.x - mapCoordinates.y) * SCREEN_HALF_TILE.x,
							(mapCoordinates.x + mapCoordinates.y) * SCREEN_HALF_TILE.y);
	}
	public Vector2f getMapCoordinates(Vector2f pScreenCoordinates)
	{
		return null;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		int xsize = gd.level.getWidth();
		int ysize = gd.level.getHeight();
		Vector2f startingPoint = null;
		grid = gd.level.dungeon.grid;
		for	(int x = 0; x < xsize && startingPoint == null; x++)
		{
			for (int y = 0; y < ysize && startingPoint == null; y++) 
			{
				if (grid[x][y] == TileType.StartingPoint)
					startingPoint = new Vector2f(x + 1, y + 1);
			}
		}
		gd.level.dungeon = null;
		character = new Character(startingPoint);
		add(character);
		character.init(this);
		inventory.items.add(new Item(ItemType.Key));
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		AABB region = new Region(new Vector2f(), new Vector2f(1, 1));
		
		Vector2f halfScreen = new Vector2f(gc.getWidth() / 2, gc.getHeight() / 2);
		
		Vector2f camera = getScreenCoordinates(new Vector2f(character.getPosition().x, character.getPosition().y)).add(SCREEN_HALF_TILE).sub(halfScreen);
		camera.x = Math.round(camera.x + getShake());
		camera.y = Math.round(camera.y + getShake());
		
		Vector2f characterPos = World.getScreenCoordinates(character.getPosition()).sub(camera),
				 characterPosDecal = characterPos.copy().add(revealDecal);
		
		int projectionHeight = (int)Math.ceil((camera.y + WALL_HEIGHT + gc.getHeight()) / SCREEN_HALF_TILE.y) + 1;
		for (int projection = (int)Math.floor(camera.y / SCREEN_HALF_TILE.y) - 1; 
			 projection < grid.length + grid[0].length && projection < projectionHeight; 
			 projection++)
		{
			for (int x = Math.min(projection, grid.length - 1), y = Math.max(0, projection - (grid.length - 1));
				 x >= 0 && y < grid[x].length; 
				 y++, x--)
			{
				// Do not draw stuff outside the camera
				Vector2f tilePos = new Vector2f(x, y);
				Vector2f tilePosScreen = getScreenCoordinates(tilePos).sub(camera);
				if (tilePosScreen.x < - SCREEN_TILE_SIZE.x)
					break;
				if (tilePosScreen.x > gc.getWidth() + SCREEN_TILE_SIZE.x)
					continue;

				float blackness = tilePosScreen.distance(characterPos) / character.lightBase + character.lightVariation;
				// Tile
				TileType tile = grid[x][y];
				if (tile != null && tile != TileType.None)
				{
					float a = 1.5f - blackness * blackness * blackness;
					if (tile.isFloor)
					{
						tileSheet.setFrameX(tile.tileId);
						if (tile == TileType.EndRoomFloor)
						{
							tileSheet.getColor().r = 1;
							tileSheet.getColor().g = 1;
							tileSheet.getColor().b = 1;
						}
						else
						{
							tileSheet.getColor().r = Math.max(20f / 255f, character.lightColor.r - blackness);
							tileSheet.getColor().g = Math.max(20f / 255f, character.lightColor.g - blackness);
							tileSheet.getColor().b = Math.max(30f / 255f, character.lightColor.b - blackness);
						}
						tileSheet.getColor().a = a;
							
						tileSheet.render(tilePosScreen);
					}
					else
					{
						wallSheet.setFrameX(tile.tileId);
						if (tile == TileType.EndRoomWall || tile == TileType.End)
						{
							wallSheet.getColor().r = 1;
							wallSheet.getColor().g = 1;
							wallSheet.getColor().b = 1;
						}
						else
						{
							wallSheet.getColor().r = Math.max(20f / 255f, character.lightColor.r - blackness);
							wallSheet.getColor().g = Math.max(20f / 255f, character.lightColor.g - blackness);
							wallSheet.getColor().b = Math.max(30f / 255f, character.lightColor.b - blackness);
						}
						
						if (!tile.alwaysShow)
						{
							float distX = (tilePosScreen.x - characterPosDecal.x) / 2,
								  distY = tilePosScreen.y - characterPosDecal.y;
							if (distY < 0)
								distY *= distY;
							wallSheet.getColor().a = Math.min(((distX * distX + distY * distY) - 100) / 10000, a);
						}
						else
							wallSheet.getColor().a = a;
						tilePosScreen.y -= WALL_HEIGHT - SCREEN_TILE_SIZE.y;
						wallSheet.render(tilePosScreen);
					}
				}
				
				region.getPosition().x = x;
				region.getPosition().y = y;

				// Entities
				for (Entity entity : spatialMap.get(region))
				{
					Vector2f bottomRight = entity.getBottomRightPoint();
					if (region.getPosition().x < bottomRight.x &&
						region.getRightX() >= bottomRight.x &&
						region.getPosition().y < bottomRight.y &&
						region.getBottomY() >= bottomRight.y)
					{
						Color color = new Color(Math.max(20f / 255f, character.lightColor.r - blackness),
												Math.max(20f / 255f, character.lightColor.g - blackness),
												Math.max(30f / 255f, character.lightColor.b - blackness));
						entity.render(gc, sbg, g, camera, color);
					}
				}
			}
		}
		
		hourglass.render(gc, character.sanity / character.maxSanity);
		inventory.render();
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		if (shakeDuration > 0)
			shakeDuration--;
		else
		{
			shakeDuration = 0;
			shakeIntensity = 0;
		}
		shake = (float)((Math.random() - 0.5f) * shakeIntensity);
		
		for (Entity entity : entities)
			entity.update(gc, sbg, delta, this);
	}
	
	public void load(int level)
	{
		gd.level = new GameLevel(level);
	}
	
	public World(GameDirector gd) throws SlickException
	{
		this.gd = gd;
		tileSheet = new ImageSheet(gd.spriteSheetLocation, (int)SCREEN_TILE_SIZE.x, (int)SCREEN_TILE_SIZE.y);
		wallSheet = new ImageSheet(gd.wallSheetLocation, (int)SCREEN_TILE_SIZE.x, WALL_HEIGHT);
		
	}
}
