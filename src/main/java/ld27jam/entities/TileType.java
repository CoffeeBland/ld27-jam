package ld27jam.entities;

public enum TileType 
{
	Floor(0, true, true),
	CorridorFloor(0, true, true),
	Wall(8, false, false, false),
	RockWallv1(9, false, false, false),
	RockWallv2(10, false, false, false),
	RockWallv3(11, false, false, false),
	None(0, false, true),
	StartingPoint(1, true, true),
	SpikeTrap(12, true, false),
	SpikeTrapOpened(13, true, false),
	ChestClosedWest(0, false, false),
	ChestClosedNorth(1, false, false),
	ChestClosedSouth(2, false, false),
	ChestClosedEast(3, false, false),
	ChestOpenedWest(4, false, false),
	ChestOpenedNorth(5, false, false),
	ChestOpenedSouth(6, false, false),
	ChestOpenedEast(7, false, false);
	
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
