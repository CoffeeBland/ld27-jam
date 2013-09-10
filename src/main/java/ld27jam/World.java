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
	public Color[][] lightingGrid;
	
	private ImageSheet tileSheet, wallSheet;
	public Character character;
	public Inventory inventory = new Inventory();
	private Hourglass hourglass = new Hourglass();
	private float shakeIntensity, shakeDuration, shake;
	public Vector2f revealDecal = new Vector2f(0, 16), endPoint = null, lastDeath = null;
	public Color minColor = new Color(20, 20, 30);
	public Color backColor = minColor.scaleCopy(0.6f);
	
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
		for (int x = minX; x < maxX && x > 0 && x < grid.length; x++)
			for (int y = minY; y < maxY && y > 0 && y < grid[x].length; y++)
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
	public void putLightAt(int x, int y, Color color, int colorRange)
	{
		propagateLightAt(x, y, color, x, y, colorRange * colorRange);
	}
	public void propagateLightAt(int x, int y, Color color, int originX, int originY, int colorRangeSquared)
	{
		Color diminishedColor = new Color(color);
		int distX = x - originX, distY = y - originY;
		float scale = (float)((colorRangeSquared * 3 - (distX * distX + distY * distY)) / (float)(colorRangeSquared * 3));
		diminishedColor.a = color.a * scale;
		if (diminishedColor.a <= 0)
			return;
		scale = (float) Math.pow(scale, 5);
		diminishedColor.r = color.r * scale;
		diminishedColor.g = color.g * scale;
		diminishedColor.b = color.b * scale;
		
		if (lightingGrid[x][y] == null)
			lightingGrid[x][y] = 
			diminishedColor;
		else if (lightingGrid[x][y].a >= diminishedColor.a &&
				 lightingGrid[x][y].r >= diminishedColor.r &&
				 lightingGrid[x][y].g >= diminishedColor.g &&
				 lightingGrid[x][y].b >= diminishedColor.b)
			return;
		else
		{
			lightingGrid[x][y].r = Math.max(lightingGrid[x][y].r, diminishedColor.r);
			lightingGrid[x][y].g = Math.max(lightingGrid[x][y].g, diminishedColor.g);
			lightingGrid[x][y].b = Math.max(lightingGrid[x][y].b, diminishedColor.b);
			lightingGrid[x][y].a = Math.max(lightingGrid[x][y].a, diminishedColor.a);
		}
		if (!grid[x][y].alwaysShow)
			return;

		if (x > 0)
			propagateLightAt(x - 1, y, color, originX, originY, colorRangeSquared);
		if (y > 0)
			propagateLightAt(x, y - 1, color, originX, originY, colorRangeSquared);
		if (x - 1 < lightingGrid.length)
			propagateLightAt(x + 1, y, color, originX, originY, colorRangeSquared);
		if (y - 1 < lightingGrid[x].length)
			propagateLightAt(x, y + 1, color, originX, originY, colorRangeSquared);
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
		lightingGrid = new Color[grid.length][grid[0].length];
		
		// Set init
		for	(int x = 0; x < xsize && startingPoint == null; x++)
			for (int y = 0; y < ysize && startingPoint == null; y++) 
				if (grid[x][y] == TileType.StartingPoint)
					startingPoint = new Vector2f(x + 1, y + 1);
		/*
		for (int x = (int) (startingPoint.x - 50); x < startingPoint.x + 50; x += 5)
			for (int y = (int) (startingPoint.y - 50); y < startingPoint.y + 50; y += 5)
			{
				putLightAt(x, y, new Color((float)Math.random(), (float)Math.random(), (float)Math.random()), 5);
			}
		for (int i = -3; i < 3; i++)
			changeTileTypeAt((int)startingPoint.x - 3, (int)startingPoint.y + i, TileType.ChestClosedEast);
		*/
		// Set end
		for	(int x = 0; x < xsize && endPoint == null; x++)
			for (int y = 0; y < ysize && endPoint == null; y++) 
				if (grid[x][y] == TileType.End)
					endPoint = new Vector2f(x + 1, y + 1);
		while (endPoint == null)
			System.out.println("GENERATION FAILURE");
		
		gd.level.dungeon = null;
		character = new Character(startingPoint);
		add(character);
		character.init(this, sbg);
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
		
		int projectionHeight = (int)Math.ceil((camera.y + WALL_HEIGHT + gc.getHeight()) / SCREEN_HALF_TILE.y) + 1,
			gridMax = grid.length + grid[0].length;
			
		for (int projection = (int)Math.floor(camera.y / SCREEN_HALF_TILE.y) - 1; 
			 projection < gridMax && projection < projectionHeight; 
			 projection++)
		{
			for (int x = Math.min(projection, grid.length - 1), y = Math.max(0, projection - (grid.length - 1));
				 x >= 0  && y < grid[x].length; 
				 y++, x--)
			{
				// Do not draw stuff outside the camera
				Vector2f tilePos = new Vector2f(x, y);
				Vector2f tilePosScreen = getScreenCoordinates(tilePos).sub(camera);
				if (tilePosScreen.x < - SCREEN_TILE_SIZE.x)
					break;
				if (tilePosScreen.x > gc.getWidth() + SCREEN_TILE_SIZE.x)
					continue;
				
				float blackness = tilePos.distance(character.getPosition()) / character.lightBase + character.lightVariation;
				float cR = Math.max(backColor.r, character.lightColor.r - blackness),
					  cG = Math.max(backColor.g, character.lightColor.g - blackness),
					  cB = Math.max(backColor.b, character.lightColor.b - blackness),
					  cA = (float) (1.1f - Math.pow(blackness, 3));
				Color colAtTile = lightingGrid[x][y];
				if (colAtTile != null)
				{
					cR = Math.max(cR, colAtTile.r);
					cG = Math.max(cG, colAtTile.g);
					cB = Math.max(cB, colAtTile.b);
					cA = Math.max(cA, colAtTile.a);
				}
				
				if (cA <= 0)
					continue;
				
				// Tile
				TileType tile = grid[x][y];
				if (tile != null && tile != TileType.None)
					renderTile(tile, tilePosScreen, characterPosDecal, cR, cG, cB, cA);
					
				region.getPosition().x = x;
				region.getPosition().y = y;
				
				// Entities
				renderEntities(gc, sbg, g, region, camera, cR, cG, cB, cA);
			}
		}
		
		hourglass.render(gc, character.sanity / character.maxSanity);
		inventory.render();
	}
	public void renderTile(TileType tile, Vector2f pos, Vector2f characterPosDecal, float cR, float cG, float cB, float cA)
	{
		if (tile.isFloor)
		{
			setTileColor(tileSheet, tile, cR, cG, cB, cA);
			tileSheet.render(pos);
		}
		else
		{
			if (!tile.alwaysShow)
			{
				float distX = (pos.x - characterPosDecal.x) / 2,
					  distY = pos.y - characterPosDecal.y;
				setTileColor(wallSheet, tile, cR, cG, cB, Math.min(((distX * distX + distY * distY) - 100) / 12000, cA));
			}
			else
				setTileColor(wallSheet, tile, cR, cG, cB, cA);
			pos.y -= WALL_HEIGHT - SCREEN_TILE_SIZE.y;
			wallSheet.render(pos);
		}
	}
	public void renderEntities(GameContainer gc, StateBasedGame sbg, Graphics g, AABB region, Vector2f camera, float cR, float cG, float cB, float cA) throws SlickException
	{
		for (Entity entity : spatialMap.get(region))
		{
			Vector2f bottomRight = entity.getBottomRightPoint();
			if (region.getPosition().x < bottomRight.x &&
				region.getRightX() >= bottomRight.x &&
				region.getPosition().y < bottomRight.y &&
				region.getBottomY() >= bottomRight.y)
			{
				entity.imageSheet.getColor().r = cR;
				entity.imageSheet.getColor().g = cG;
				entity.imageSheet.getColor().b = cB;
				entity.imageSheet.getColor().a = cA;
				entity.render(gc, sbg, g, camera);
			}
		}
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
	
	public void setTileColor(ImageSheet sheet, TileType tile, float cR, float cG, float cB, float cA)
	{
		sheet.setFrameX(tile.tileId);
		sheet.getColor().r = cR;
		sheet.getColor().g = cG;
		sheet.getColor().b = cB;
		sheet.getColor().a = cA;
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
