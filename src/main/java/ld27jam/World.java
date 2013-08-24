package ld27jam;

import java.util.HashSet;
import java.util.Set;

import ld27jam.entities.Entity;
import ld27jam.entities.GridTile;
import ld27jam.helpers.Renderable;
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
	private GridTile[][] grid;
	private ImageSheet spriteSheet;

	public static Vector2f getScreenCoordinates(Vector2f pMapCoordinates)
	{
		return new Vector2f((pMapCoordinates.x - pMapCoordinates.y) * SCREEN_HALF_TILE.x,
							(pMapCoordinates.x + pMapCoordinates.y) * SCREEN_HALF_TILE.y);
	}
	public Vector2f getMapCoordinates(Vector2f pScreenCoordinates)
	{
		return null;
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		grid = new GridTile[8][12];
		for (int x = 0; x < 8; x++)
			for (int y = 0; y <12; y++)
				if (Math.random() < 0.5)
					grid[x][y] = GridTile.Test;
				else
					grid[x][y] = GridTile.Test2;
	}
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g, Vector2f camera) throws SlickException
	{
		AABB region = new Region(new Vector2f(), new Vector2f(1, 1));
		
		for (int projection = 0; projection < grid.length + grid[0].length; projection++)
		{
			for (int x = Math.min(projection, grid.length - 1), y = Math.max(0, projection - (grid.length - 1)); x >= 0 && y < grid[x].length; y++, x--)
			{
				GridTile tile = grid[x][y];
				if (tile != null)
				{
					spriteSheet.setFrameX(tile.tileX);
					spriteSheet.setFrameY(tile.tileY);
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
