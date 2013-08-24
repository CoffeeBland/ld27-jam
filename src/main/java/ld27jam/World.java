package ld27jam;

import java.util.HashSet;
import java.util.Set;

import ld27jam.entities.Entity;
import ld27jam.entities.Tile;
import ld27jam.entities.TileType;
import ld27jam.res.ImageSheet;
import ld27jam.spatialData.AABB;
import ld27jam.spatialData.Region;
import ld27jam.spatialData.SpatialMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class World
{
	public static final Vector2f SCREEN_TILE_SIZE = new Vector2f(32, 16),
								 SCREEN_HALF_TILE = new Vector2f(16, 8);
	
	private Set<Entity> entities = new HashSet<Entity>();
	private SpatialMap<Entity> spatialMap = new SpatialMap<Entity>();
	private Tile[][] grid;
	private ImageSheet spriteSheet;
	
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
		for (int x = minX; x < maxX; minX++)
			for (int y = minY; y < maxY; minY++)
				tiles.add(grid[x][y]);
		
		return tiles;
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
		grid = new Tile[8][12];
		for (int x = 0; x < 8; x++)
			for (int y = 0; y <12; y++)
				if (Math.random() < 0.9)
					grid[x][y] = new Tile(TileType.Test, x, y);
				else
					grid[x][y] = new Tile(TileType.Test2, x, y);
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g, Vector2f camera) throws SlickException
	{
		AABB region = new Region(new Vector2f(), new Vector2f(1, 1));
		
		for (int projection = 0; projection < grid.length + grid[0].length; projection++)
		{
			for (int x = Math.min(projection, grid.length - 1), y = Math.max(0, projection - (grid.length - 1)); x >= 0 && y < grid[x].length; y++, x--)
			{
				Tile tile = grid[x][y];
				if (tile != null)
				{
					spriteSheet.setFrameX(tile.type.tileX);
					spriteSheet.setFrameY(tile.type.tileY);
					spriteSheet.render(getScreenCoordinates(new Vector2f(x, y)).sub(camera));
				}
				
				region.getPosition().x = x;
				region.getPosition().y = y;
				for (Entity entity : spatialMap.get(region))
					if (region.containsPoint(entity.getBottomRightPoint()))
						entity.render(gc, sbg, g, camera);
			}
		}
	}
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		for (Entity entity : entities)
			entity.update(gc, sbg, delta, this);
	}
	
	public World(GameDirector gd) throws SlickException
	{
		spriteSheet = new ImageSheet(gd.spriteSheetLocation, (int)SCREEN_TILE_SIZE.x, (int)SCREEN_TILE_SIZE.y);
	}
}
