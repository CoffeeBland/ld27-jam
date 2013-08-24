package ld27jam.entities;

import org.newdawn.slick.geom.Vector2f;

import ld27jam.spatialData.AABB;

public class Tile extends AABB
{
	public TileType type;
	
	public Tile(TileType type, int x, int y)
	{
		this.type = type;
		setPosition(new Vector2f(x, y));
		setSize(new Vector2f(1, 1));
	}
}
