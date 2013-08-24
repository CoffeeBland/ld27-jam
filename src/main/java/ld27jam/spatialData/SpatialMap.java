package ld27jam.spatialData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class SpatialMap<T extends Region>
{
	protected static final float TILE_SIZE = 2;
	
	Map<Vector2f, Set<T>> mMap = new HashMap<Vector2f, Set<T>>();
	
	public Set<T> get(Vector2f pPoint, float pRadius)
	{
		Set<T> set = get(new Region(pPoint.x - pRadius, pPoint.y - pRadius, pPoint.x + pRadius, pPoint.y + pRadius));
		
		float radiusSquared = pRadius * pRadius;
		Vector2f tmp;
		for (T obj : set)
		{
			Vector2f[] points =
			{
				obj.getUpperLeftPoint(),
				obj.getUpperRightPoint(),
				obj.getBottomLeftPoint(),
				obj.getBottomRightPoint()
			};
			for (Vector2f point : points)
			{
				tmp = new Vector2f(point.x - pPoint.x, point.y - pPoint.y);
				if (tmp.x * tmp.x + tmp.y * tmp.y < radiusSquared)
					continue;
			}
			set.remove(obj);
		}
		return set;
	}
	public Set<T> get(AABB pRegion)
	{
		Vector2f tl = new Vector2f((float)(Math.floor(pRegion.getPosition().x / TILE_SIZE) * TILE_SIZE), 
								   (float)(Math.floor(pRegion.getPosition().y / TILE_SIZE) * TILE_SIZE)), 
				 br = new Vector2f(pRegion.getRightX(), pRegion.getBottomY());
		Set<T> objects = new HashSet<T>();
		for (float x = tl.x; x < br.x; x += TILE_SIZE)
			for (float y = tl.y; y < br.y; y += TILE_SIZE)
				for (T object : getAtTile(new Vector2f(x, y)))
					if (pRegion.containsRegion(object))
						objects.add(object);
		return objects;
	}
	protected Set<T> getAtTile(Vector2f pTile)
	{
		if (mMap.containsKey(pTile))
			return mMap.get(pTile);
		return new HashSet<T>();
	}
	
	public void put(T pObject)
	{
		Vector2f tl = new Vector2f((float)(Math.floor(pObject.getPosition().x / TILE_SIZE) * TILE_SIZE), 
								   (float)(Math.floor(pObject.getPosition().y / TILE_SIZE) * TILE_SIZE));
		Vector2f br = new Vector2f((float)(Math.ceil(pObject.getRightX() / TILE_SIZE) * TILE_SIZE), 
						   		   (float)(Math.ceil(pObject.getBottomY() / TILE_SIZE) * TILE_SIZE));
		for (float x = tl.x; x < br.x; x += TILE_SIZE)
			for (float y = tl.y; y < br.y; y += TILE_SIZE)
				putAtTile(new Vector2f(x, y), pObject);
	}
	protected void putAtTile(Vector2f pTile, T pObject)
	{
		Set<T> set;
		if (!mMap.containsKey(pTile))
		{
			set = new HashSet<T>();
			mMap.put(pTile, set);
		}
		else
			set = mMap.get(pTile);
		set.add(pObject);
		pObject.getContainingTiles().add(set);
	}
	
	public void remove(T pObject)
	{
		Vector2f tl = new Vector2f((float)(Math.floor(pObject.getPosition().x / TILE_SIZE) * TILE_SIZE), 
				   				   (float)(Math.floor(pObject.getPosition().y / TILE_SIZE) * TILE_SIZE)), 
				 br = new Vector2f((float)(Math.ceil(pObject.getRightX() / TILE_SIZE) * TILE_SIZE), 
						   		   (float)(Math.ceil(pObject.getBottomY() / TILE_SIZE) * TILE_SIZE));
		for (float x = tl.x; x < br.x; x += TILE_SIZE)
			for (float y = tl.y; y < br.y; y += TILE_SIZE)
				removeAtTile(new Vector2f(x, y), pObject);
		pObject.getContainingTiles().clear();
	}
	protected void removeAtTile(Vector2f pTile, T pObject)
	{
		if (!mMap.containsKey(pTile))
			return;
		
		Set<T> set = mMap.get(pTile);
		set.remove(pObject);
		if (set.size() == 0)
			mMap.remove(pTile);
	}
	
	public void update(T pObject)
	{
		for (Set<? extends Region> set : pObject.getContainingTiles())
			set.remove(pObject);
		pObject.getContainingTiles().clear();
		put(pObject);
	}

	public void render(AABB pScreen, Graphics pG)
	{
		Vector2f tl = new Vector2f((float)(Math.floor(pScreen.getPosition().x / TILE_SIZE) * TILE_SIZE), 
				   				   (float)(Math.floor(pScreen.getPosition().y / TILE_SIZE) * TILE_SIZE)), 
				 br = new Vector2f(pScreen.getPosition().x + pScreen.getSize().x, pScreen.getPosition().y + pScreen.getSize().y);
		for (float x = tl.x; x < br.x; x += TILE_SIZE)
			for (float y = tl.y; y < br.y; y += TILE_SIZE)
				if (mMap.containsKey(new Vector2f(x, y)))
				{
					pG.setColor(new Color(255, 255, 255, 0.5f));
					
					pG.drawLine(x - pScreen.getPosition().x, 
								y - pScreen.getPosition().y, 
								x + TILE_SIZE - pScreen.getPosition().x, 
								y - pScreen.getPosition().y);
					
					pG.drawLine(x - pScreen.getPosition().x, 
								y - pScreen.getPosition().y, 
								x - pScreen.getPosition().x, 
								y + TILE_SIZE - pScreen.getPosition().y);
					
					pG.drawLine(x + TILE_SIZE - pScreen.getPosition().x, 
								y - pScreen.getPosition().y, 
								x + TILE_SIZE - pScreen.getPosition().x, 
								y + TILE_SIZE - pScreen.getPosition().y);
					
					pG.drawLine(x - pScreen.getPosition().x, 
								y + TILE_SIZE - pScreen.getPosition().y, 
								x + TILE_SIZE - pScreen.getPosition().x, 
								y + TILE_SIZE - pScreen.getPosition().y);
					
					pG.drawLine(x - pScreen.getPosition().x, 
								y - pScreen.getPosition().y, 
								x + TILE_SIZE - pScreen.getPosition().x, 
								y + TILE_SIZE - pScreen.getPosition().y);
				}
	}
}
