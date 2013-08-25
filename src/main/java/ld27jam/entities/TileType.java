package ld27jam.entities;

public enum TileType 
{
	None(0, false, false),
	StartingPoint(1, true, true),
	
	CorridorFloor(0, true, true),
	Floor(0, true, true),
	EndRoomFloor(0, true, true),
	
	CorridorWall(8, false, false, false),
	EndRoomWall(12, false, false, false),
	
	Floor1(0, true, true),
	Floor2(1, true, true),
	Floor3(2, true, true),
	Floor4(3, true, true),
	Floor5(4, true, true),
	Floor6(5, true, true),
	
	RockWall1(12, false, false, false),
	RockWall2(13, false, false, false),
	RockWall3(14, false, false, false),
	RockWall4(15, false, false, false),
	RockWall5(16, false, false, false),
	RockWall6(17, false, false, false),
	RockMossWall1(18, false, false, false),
	RockMossWall2(19, false, false, false),
	RockMossWall3(20, false, false, false),
	RockMossWall4(21, false, false, false),
	RockMossWall5(22, false, false, false),
	RockMossWall6(23, false, false, false),
	
	Door(0, true, true),
	
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
