package ld27jam.entities;

public enum TileType 
{
	Test(0, 0, true, null),
	Test2(1, 0, false, 0);
	
	public int tileX, tileY;
	public Integer wallId = null;
	public boolean canWalkOn;
	
	private TileType(int tileX, int tileY, boolean canWalkOn, Integer wallId)
	{
		this.tileX = tileX;
		this.tileY = tileY;
		this.canWalkOn = canWalkOn;
		this.wallId = wallId;
	}
}
