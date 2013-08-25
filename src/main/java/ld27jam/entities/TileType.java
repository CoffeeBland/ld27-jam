package ld27jam.entities;

public enum TileType 
{
	Floor(2, true, true),
	Wall(9, false, false),
	None(0, false, true),
	StartingPoint(0, true, true),
	SpikeTrapClosed(0, true, true),
	SpikeTrapOpened(4, true, true),
	ChestClosedTL(0, false, false),
	ChestClosedTR(1, false, false),
	ChestClosedBL(2, false, false),
	ChestClosedBR(3, false, false),
	ChestOpenedTL(4, false, false),
	ChestOpenedTR(5, false, false),
	ChestOpenedBL(6, false, false),
	ChestOpenedBR(7, false, false);
	
	public int tileId;
	public boolean canWalkOn, isFloor;
	
	private TileType(Integer tileId, boolean canWalkOn, boolean isFloor)
	{
		this.tileId = tileId;
		this.canWalkOn = canWalkOn;
		this.isFloor = isFloor;
	}
}
