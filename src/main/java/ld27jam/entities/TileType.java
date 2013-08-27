package ld27jam.entities;

public enum TileType 
{
	None(0, false, false, false),
	StartingPoint(6, true, true, false),
	
	CorridorFloor(0, true, true, false),
	Floor(0, true, true, false),
	EndRoomFloor(7, true, true, false),
	End(29, true, false, false),
	
	CorridorWall(8, false, false, true, false),
	EndRoomWall(24, false, false, true, false),
	
	Floor1(0, true, true, false),
	Floor2(1, true, true, false),
	Floor3(2, true, true, false),
	Floor4(3, true, true, false),
	Floor5(4, true, true, false),
	Floor6(5, true, true, false),
	
	TileFloor1(10, true, true, false),
	TileFloor2(11, true, true, false),
	TileFloor3(12, true, true, false),
	TileFloor4(13, true, true, false),
	
	RockWall1(12, false, false, true, false),
	RockWall2(13, false, false, true, false),
	RockWall3(14, false, false, true, false),
	RockWall4(15, false, false, true, false),
	RockWall5(16, false, false, true, false),
	RockWall6(17, false, false, true, false),
	RockMossWall1(18, false, false, true, false),
	RockMossWall2(19, false, false, true, false),
	RockMossWall3(20, false, false, true, false),
	RockMossWall4(21, false, false, true, false),
	RockMossWall5(22, false, false, true, false),
	RockMossWall6(23, false, false, true, false),
	
	OpenedDoorWE(9, true, true, false),
	OpenedDoorNS(8, true, true, false),
	Door(8, false, false, false),
	DoorWE(26, false, false, false),
	DoorNS(25, false, false, false),
	LockedDoor(9, false, false, false),
	LockedDoorWE(28, false, false, false),
	LockedDoorNS(27, false, false, false),
	
	SpikeTrap(10, true, false, false),
	SpikeTrapOpened(11, true, false, false),
	
	ChestClosedWest(0, false, false, false),
	ChestClosedNorth(1, false, false, false),
	ChestClosedSouth(2, false, false, false),
	ChestClosedEast(3, false, false, false),
	
	ChestOpenedWest(4, false, false, false),
	ChestOpenedNorth(5, false, false, false),
	ChestOpenedSouth(6, false, false, false),
	ChestOpenedEast(7, false, false, false);
	
	public int tileId;
	public boolean canWalkOn, isFloor, isWall, alwaysShow;
	
	private TileType(Integer tileId, boolean canWalkOn, boolean isFloor, boolean isWall, boolean alwaysShow)
	{
		this.tileId = tileId;
		this.canWalkOn = canWalkOn;
		this.isFloor = isFloor;
		this.isWall = isWall;
		this.alwaysShow = alwaysShow;
	}
	private TileType(Integer tileId, boolean canWalkOn, boolean isFloor, boolean isWall)
	{
		this(tileId, canWalkOn, isFloor, isFloor, true);
	}
}
