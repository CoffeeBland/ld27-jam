package ld27jam.entities;

public enum TileType 
{
	None(0, false, true),
	CorridorFloor(0, true, true),
	CorridorWall(8, false, false, false),
	StartingPoint(1, true, true),
	Floorv1(0, true, true),
	Floorv2(1, true, true),
	Floorv3(2, true, true),
	Floorv4(3, true, true),
	Floorv5(4, true, true),
	Floorv6(5, true, true),
	RockWallv1(12, false, false, false),
	RockWallv2(13, false, false, false),
	RockWallv3(14, false, false, false),
	RockWallv4(15, false, false, false),
	RockWallv5(16, false, false, false),
	RockWallv6(17, false, false, false),
	RockMossWallv1(18, false, false, false),
	RockMossWallv2(19, false, false, false),
	RockMossWallv3(20, false, false, false),
	RockMossWallv4(21, false, false, false),
	RockMossWallv5(22, false, false, false),
	RockMossWallv6(23, false, false, false),
	SpikeTrap(10, true, false),
	SpikeTrapOpened(11, true, false),
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
