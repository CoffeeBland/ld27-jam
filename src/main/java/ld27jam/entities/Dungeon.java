package ld27jam.entities;

import java.util.*;

import ld27jam.spatialData.Region;

import org.newdawn.slick.geom.Vector2f;

public class Dungeon {
	//size of the map
	public int xsize = 0;
	public int ysize = 0;
	public int[] lastRoom;
 
	//number of "objects" to generate on the map
	private int objects = 0;
	public static long oldseed = 0;
 
	private TileType[] dungeon_map = { };
	private HashMap<Vector2f, TileType> dungeon = new HashMap<Vector2f, TileType>();
	private HashMap<Vector2f, Vector2f> dungeonRooms = new HashMap<Vector2f, Vector2f>(); 
 
	public void setCell(int x, int y, TileType celltype){
		dungeon_map[x + xsize * y] = celltype;
	}
 
	public TileType getTileType(int x, int y) {
		for (Vector2f t : this.dungeon.keySet()) {
			if (t.x == x && t.y == y)
				return this.dungeon.get(t);
		}
		return TileType.None;
	}

	public int[] getRoom(int x, int y) {
		for (Vector2f t : this.dungeonRooms.keySet()) {
			Vector2f tSize = this.dungeonRooms.get(t);
			if (x >= t.x && y >= t.y && x <= t.x + tSize.x && y <= t.y + tSize.y)
				return new int[] { (int) t.x, (int) t.y, (int) tSize.x, (int) tSize.y };
		}
		return null;
	}
	
	private int getRand(int mean, int deviance){
		Date now = new Date();
		long seed = now.getTime() + oldseed;
	
		int rnd = (int) Math.abs( (new Random(seed)).nextGaussian() * mean + deviance );
		
		this.oldseed = seed;
		return rnd;
	}
 
	private boolean checkPoint(int x, int y)
	{
		return getTileType(x, y) == TileType.Floor;
	}
	
	public Vector2f getClosestFreeCell(int xs, int ys)
	{
		if (checkPoint(xs, ys) == true)
		{
		    return new Vector2f(xs, ys);
		}

		for (int d = 0; d<30; d++)
		{
		    for (int x = xs-d; x < xs+d+1; x++)
		    {
		        // Point to check: (x, ys - d) and (x, ys + d) 
		        if (checkPoint(x, ys - d) == true)
		        {
		            return new Vector2f(x, ys - d);
		        }

		        if (checkPoint(x, ys + d) == true)
		        {
		            return new Vector2f(x, ys - d);
		        }
		    }

		    for (int y = ys-d+1; y < ys+d; y++)
		    {
		        // Point to check = (xs - d, y) and (xs + d, y) 
		        if (checkPoint(xs - d, y) == true)
		        {
		            return new Vector2f(xs - d, y);
		        }

		        if (checkPoint(xs - d, y) == true)
		        {
		            return new Vector2f(xs - d, y);
		        }
		    }
		}
		return new Vector2f(xs, ys);
	}
	
	public void showDungeon() 
	{
		for (int y = 0; y < ysize; y++){
			for (int x = 0; x < xsize; x++){
				switch(getTileType(x, y).ordinal()){
					case 0:
						System.out.print("#");
						break;
					case 1:
						System.out.print(".");
						break;
					case 2:
						System.out.print(" ");
						break;
				};
			}
			System.out.println();
		}
	}
 
	public int[] createRoom(Vector2f start)
	{
		int width = Math.max(getRand(20, 10), 6);
		int height = Math.max(getRand(20, 12), 6);
		
		this.dungeonRooms.put(start, new Vector2f(width, height));
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// Lay the flooring
				this.dungeon.put(new Vector2f(start.x + x, start.y + y), TileType.Floor);
				// Wall the room
				int yModif = 0;
				int xModif = 0;
				if (y == 0)
					yModif = -1;
				if (y == height-1)
					yModif = 1;
				if (x == 0)
					xModif = -1;
				if (x == width-1)
					xModif = 1;
				if (xModif == -1 || yModif == -1)
				{
					this.dungeon.put(new Vector2f(x+xModif, y+yModif), TileType.Wall);
					this.dungeon.put(new Vector2f(x, y+yModif), TileType.Wall);
					this.dungeon.put(new Vector2f(x+xModif, y), TileType.Wall);
				}
				else if (xModif != 0 || yModif != 0)
					this.dungeon.put(new Vector2f(x+xModif,  y+yModif), TileType.Wall);
			}
		}
		
		// Start spacing the room away from the other one
		int[] dirValues = { -1, 1 };
		int xDir = dirValues[new Random(this.oldseed).nextInt(1)];
		int yDir = dirValues[new Random(this.oldseed).nextInt(1)];
		Region thisRoom = new Region(new Vector2f((int)start.x, (int)start.y), new Vector2f(width, height));
		if (this.lastRoom != null) {
			Region pastRoom = new Region(new Vector2f(lastRoom[0], lastRoom[1]), new Vector2f(lastRoom[2], lastRoom[3]));
					
			// Space it now
			while (thisRoom.containsRegion(pastRoom)) 
			{
				System.out.println("Room: x:" + thisRoom.getPosition().x + " - y:" + thisRoom.getPosition().y + " - w:" + thisRoom.getSize().x + " h:" + thisRoom.getSize().y);
				thisRoom.getPosition().x = thisRoom.getPosition().x + xDir;
				thisRoom.getPosition().y = thisRoom.getPosition().y + yDir;
			}
		}
		
		System.out.println();
		//System.out.println("Room: x:" + thisRoom.getPosition().x + " - y:" + thisRoom.getPosition().y + " - w:" + thisRoom.getSize().x + " h:" + thisRoom.getSize().y);
		return new int[] { (int)thisRoom.getPosition().x, (int)thisRoom.getPosition().y, width, height };
	}
	
	//and here's the one generating the whole map
	public void createDungeon(int inx, int iny, int inobj){
		this.xsize = inx;
		this.ysize = iny;
		this.objects = inobj;
		this.dungeon = new HashMap<Vector2f, TileType>();
		this.dungeonRooms = new HashMap<Vector2f, Vector2f>();
		/*int sideLength = (int) Math.sqrt(objects); 
		Vector2f[][] rooms = new Vector2f[sideLength][sideLength];
		for (int x = 0; x < sideLength; x++) {
			for (int y = 0; y < sideLength; y++) {
				rooms[x][y] = new Vector2f(getRand(20, 1), getRand(10, 12));
				System.out.println(rooms[x][y].x + " - " + rooms[x][y].y);
			}
		}*/
		
		lastRoom = createRoom(new Vector2f(0,0));
		int objectsDone = 1;
		while (objectsDone < this.objects) 
		{
			
			int roomBorderBlocks = (lastRoom[2]*2) + (lastRoom[3]*2);
			int doorLocation = new Random(this.oldseed).nextInt(roomBorderBlocks-1)+1;
			if (doorLocation >= 1 && doorLocation <= lastRoom[2])
			{
				// top edge
				lastRoom = createRoom(new Vector2f(doorLocation, lastRoom[1]-1));
				objectsDone++;
				continue;
			}
			else if (doorLocation >= lastRoom[2] + (lastRoom[3]*2))
			{
				// bottom edge
				lastRoom = createRoom(new Vector2f(doorLocation - lastRoom[2] + (lastRoom[3]*2), lastRoom[1] + lastRoom[3]));
				objectsDone++;
				continue;
			}
			else if (doorLocation >= lastRoom[2] && doorLocation <= lastRoom[2] + lastRoom[3])
			{
				// right edge
				lastRoom = createRoom(new Vector2f(lastRoom[0] + lastRoom[2], doorLocation - lastRoom[2]));
				objectsDone++;
				continue;
			}
			else if (doorLocation >= (lastRoom[2]*2) + lastRoom[3] && doorLocation <= (lastRoom[2]*2) + (lastRoom[3]*2))
			{
				// left edge
				lastRoom = createRoom(new Vector2f(lastRoom[0], doorLocation - (lastRoom[2]*2) + lastRoom[3]));
				objectsDone++;
				continue;
			}
		}
		
	}

}
