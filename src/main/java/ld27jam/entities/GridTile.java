package ld27jam.entities;

public enum GridTile 
{
	Test(1, 0);
	
	public int tileX, tileY;
	
	private GridTile(int tileX, int tileY)
	{
		this.tileX = tileX;
		this.tileY = tileY;
	}
}
