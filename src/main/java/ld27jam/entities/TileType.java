package ld27jam.entities;

public enum TileType 
{
	Floor(0, true, true),
	CorridorFloor(0, true, true),
	Wall(8, false, false, false),
	None(0, false, true),
	StartingPoint(1, true, true),
	SpikeTrap(10, true, false),
	SpikeTrapOpened(11, true, false),
	ChestClosedTL(0, false, false),
	ChestClosedTR(1, false, false),
	ChestClosedBL(2, false, false),
	ChestClosedBR(3, false, false),
	ChestOpenedTL(4, false, false),
	ChestOpenedTR(5, false, false),
	ChestOpenedBL(6, false, false),
	ChestOpenedBR(7, false, false);
	
	public int tileId;
	public boolean canWalkOn, isFloor, isWall, alwaysShow;
	
	private TileType(Integer tileId, boolean canWalkOn, boolean isFloor, boolean alwaysShow)
	{
		this.tileId = tileId;
		this.canWalkOn = canWalkOn;
		this.isFloor = isFloor;
		this.alwaysShow = alwaysShow;
	}
	private TileType(Integer tileId, boolean canWalkOn, boolean isFloor)
	{
		this(tileId, canWalkOn, isFloor, true);
	}
}
