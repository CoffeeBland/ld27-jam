package ld27jam.entities;

public class Tile 
{
	TileType type;
	int x, y;
	
	public Tile (TileType type, int x, int y)
	{
		this.type = type;
		this.x = x;
		this.y = y;
	}
}
