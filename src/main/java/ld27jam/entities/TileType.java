package ld27jam.entities;

public enum TileType 
{
	Test(0, 0, true),
	Test2(1, 0, false);
	
	public int tileX, tileY;
	public boolean canWalkOn;
	
	private TileType(int tileX, int tileY, boolean canWalkOn)
	{
		this.tileX = tileX;
		this.tileY = tileY;
		this.canWalkOn = canWalkOn;
	}
}
