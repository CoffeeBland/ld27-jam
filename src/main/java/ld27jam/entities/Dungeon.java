package ld27jam.entities;

import java.io.EOFException;
import java.util.*;

import org.newdawn.slick.geom.Vector2f;

public class Dungeon {
	//size of the map
	public int xsize = 0;
	public int ysize = 0;

	public static long oldseed = 0;
	public TileType[][] grid = {};
	private boolean startingPointSet = false;
 
	public void setCell(int x, int y, TileType celltype){
		grid[x][y] = celltype;
	}
 
	public TileType getTileType(int x, int y) {
		return grid[x][y];
	}
		
	public static int getRand(int min, int max)
	{
		Date now = new Date();
		long seed = now.getTime() + oldseed;
		oldseed = seed;
 
		Random randomizer = new Random(seed);
		int n = max - min + 1;
		int i = randomizer.nextInt(n);
		if (i < 0)
			i = -i;
		return min + i;
	}
 
	private boolean checkPoint(int x, int y)
	{
		return getTileType(x, y).isFloor || getTileType(x, y) == TileType.CorridorFloor;
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
		for (int y = 0; y < ysize-1; y++)
		{
			for (int x = 0; x < xsize-1; x++)
			{
				if (this.grid[x][y] == TileType.SpikeTrap)
					System.out.print("x");
				else if (!this.grid[x][y].canWalkOn && this.grid[x][y].isWall)
					System.out.print("O");
				else if (this.grid[x][y].canWalkOn)
					System.out.print(".");
				else
					System.out.print(" ");
			}
			System.out.println();
		}
	}
 
	public Vector2f wheresEmptyFrom(int x, int y) 
	{
		if(this.northFrom(x,y) == TileType.None) return new Vector2f(x,   y-1);
		if(this.southFrom(x,y) == TileType.None) return new Vector2f(x,   y+1);
		if(this.westFrom(x,y)  == TileType.None) return new Vector2f(x-1, y  );
		if(this.eastFrom(x,y)  == TileType.None) return new Vector2f(x+1, y  );
		return null;
	}
	
	private TileType eastFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.grid[x+1][y] : null);
	}
	private TileType westFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.grid[x-1][y] : null);
	}
	private TileType northFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.grid[x][y-1] : null);
	}
	private TileType southFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.grid[x][y+1] : null);
	}
	
	private TileType northeastFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.grid[x+1][y-1] : null);
	}
	private TileType southeastFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.grid[x+1][y+1] : null);
	}
	private TileType northwestFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.grid[x-1][y-1] : null);
	}
	private TileType southwestFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.grid[x-1][y+1] : null);
	}
	
	private static TileType[] rockWalls = new TileType[] 
	{
		TileType.RockWall1,
		TileType.RockWall2,
		TileType.RockWall3,
		TileType.RockWall4,
		TileType.RockWall5,
		TileType.RockWall6
	};
	private static TileType[] mossWalls = new TileType[] 
	{
		TileType.RockMossWall1,
		TileType.RockMossWall2,
		TileType.RockMossWall3,
		TileType.RockMossWall4,
		TileType.RockMossWall5,
		TileType.RockMossWall6
	};
	public static TileType getWallType()
	{
		if (Math.random() < 0.75)
			return rockWalls[getRand(0, rockWalls.length - 1)];
		else
			return mossWalls[getRand(0, mossWalls.length - 1)];
	}
	
	private static TileType[] floors = new TileType[] 
	{
		TileType.Floor1,
		TileType.Floor2,
		TileType.Floor3,
		TileType.Floor4,
		TileType.Floor5,
		TileType.Floor6,
	};
	public static TileType getFloorType()
	{
		if (Math.random() < 0.75)
		{
			return floors[getRand(0, 1)];
		}
		else
		{
			return floors[getRand(2, floors.length-1)];
		}
	}
	
	private void surroundEveryFloorWithWall()
	{
	    for (int x = 0; x < grid.length; x++) 
	    {
			for (int y = 0; y < grid[x].length; y++) 
			{
				TileType tt = grid[x][y] == TileType.CorridorFloor ? getWallType() : getWallType(); 
				if(grid[x][y].canWalkOn) 
				{
		          if(northFrom(x,y) == TileType.None)     grid[x  ][y-1]   = tt;
		          if(southFrom(x,y) == TileType.None)     grid[x  ][y+1]   = tt;
		          if(westFrom(x,y) == TileType.None)      grid[x-1][y  ]   = tt;
		          if(eastFrom(x,y) == TileType.None)      grid[x+1][y  ]   = tt;
		          if(northeastFrom(x,y) == TileType.None) grid[x+1][y-1]   = tt;
		          if(southeastFrom(x,y) == TileType.None) grid[x+1][y+1]   = tt;
		          if(northwestFrom(x,y) == TileType.None) grid[x-1][y-1]   = tt;
		          if(southwestFrom(x,y) == TileType.None) grid[x-1][y+1]   = tt;
		        }
				// TODO else if door put wall
			}
		}
	}
	public boolean isClearFromTo(int x1, int y1, int x2, int y2) 
	{
		int start_x = Math.min(x1,x2);
		int end_x = Math.max(x1,x2);
		int start_y = Math.min(y1,y2);
		int end_y = Math.max(y1,y2);

		if (end_y - start_y + end_x - start_x > 20)
			return false;
		
		for(int x = start_x; x <= end_x; x++) {
			for(int y = start_y; y <= end_y; y++) {
				if (this.grid[x][y] != TileType.None && this.grid[x][y] != TileType.CorridorFloor) return false;
			}
		}
		return true;
	}
	public void drawCorridorFromTo(int x1, int y1, int x2, int y2)
	{
		int h_mod, v_mod, x, y;
	    h_mod = x1 < x2 ? 1 : -1;
	    v_mod = y1 < y2 ? 1 : -1;
	    x = x1;
	    y = y1;

	    while( x != x2 || y != y2) {
	      this.grid[x][y] = TileType.CorridorFloor;
	      if(x != x2 && Math.random() > 0.5) {
	        x += h_mod;
	      } else if(y != y2) {
	        y += v_mod;
	      }
	    }
	    this.grid[x][y] = TileType.CorridorFloor;
	}

	public TileType[][] drawRoom(TileType[][] containerGrid, int xStart, int yStart, RoomTemplate tmpl)
	{
		for (int x = 0; x < tmpl.getWidth(); x++) {
			for (int y = 0; y < tmpl.getHeight(); y++) {
				containerGrid[xStart + x][yStart + y] = tmpl.getTileAtCell(x, y);
			}
		}
		return containerGrid;
	}
	
	// and here's the one generating the whole map
	public void createDungeon(int inx, int iny, RoomTemplate[] templates){
		this.xsize = inx;
		this.ysize = iny;
		this.grid = new TileType[inx+1][iny+1];
		
		int spacing = 3;
		int tallest_height = 0; 
		int widest_width = 0;
		for (RoomTemplate t : templates) 
		{
			if (t.getWidth() > widest_width) widest_width = t.getWidth();
			if (t.getHeight() > tallest_height) tallest_height = t.getHeight();
		}
		
		// fill dungeon with white space
		for (int x = 0; x < this.grid.length; x++) 
		{
			for (int y = 0; y < this.grid[x].length; y++) 
			{
				this.grid[x][y] = TileType.None;
			}
		}
		
		int y = 0;
	    ArrayList<Vector2f> room_exits = new ArrayList<Vector2f>();
	    
	    while (y < this.ysize)
	    {
	    	ArrayList<RoomTemplate> rooms = new ArrayList<RoomTemplate>();
	        int room_for_x = this.xsize;
	        
	        while( room_for_x > widest_width ) 
	        {
	        	RoomTemplate a_room;
	        	if (y > this.ysize/2-20 && y < this.ysize/2+20 && room_for_x > this.xsize/2-20 && room_for_x < this.xsize/2+20 && startingPointSet == false)
	        	{
	        		a_room = RoomTemplate.getStartingRoom();
	        		this.startingPointSet = true;
	        	}
	        	else
	        		a_room = templates[getRand(0, templates.length-1)];
	        	rooms.add(a_room);
	        	room_for_x -= (a_room.getWidth() + spacing + 1);
	        }

	        // tallest height just for this row
	        int row_tallest_height = 0;
	        for (RoomTemplate row_room : rooms) 
	        {
				if (row_room.getHeight() > row_tallest_height) row_tallest_height = row_room.getHeight();
			}

	        // 0 spaces on each end, at least 3 spaces in between each room
	        int[] arr = new int[rooms.size()+2];
	        for (int j = 0; j < arr.length; j++) 
	        {
				if (j == 0 || j == arr.length-1)
					arr[j] = 0;
				else
					arr[j] = spacing;
			}
	        int[] gaps = arr;
	        
	        int num_unused_spaces = this.xsize - (spacing * rooms.size());
	        for (RoomTemplate row_room : rooms) 
	        {
				num_unused_spaces -= row_room.getWidth();
			}

	        // randomly distribute extra spaces into gaps
	        for (int i = 0; i < num_unused_spaces; i++) 
	        {
	          gaps[getRand(0, gaps.length-1)] += 1;
	        }

	        // shift ahead past first gap
	        int x = gaps[0];
	        int index = 0;
	        for (RoomTemplate row_room : rooms) 
	        {
				int extra_y_offset = getRand(0, row_tallest_height - row_room.getHeight());
				
				for (int x_in_room = 0; x_in_room < row_room.getWidth(); x_in_room++) 
				{
					for (int y_in_room = 0; y_in_room < row_room.getHeight(); y_in_room++) 
					{
						this.grid[Math.min(x + x_in_room, this.xsize)][Math.min(y + y_in_room + extra_y_offset, this.ysize)] = row_room.getTileAtCell(x_in_room, y_in_room);
					}
				}
				
				
				// collect exits
				row_room.setExitsOffset(new Vector2f(x, y + extra_y_offset));
				room_exits.addAll(row_room.exits());
				
				// shift past this room and then next gap
		        x += row_room.getWidth() + gaps[index+1];
		        index++;
			}
	        
	        
	        y += tallest_height;
	        // 3 is our spacing + 1 to start a the right place
	        y += spacing + 1;
	    }// end loop rows
	    
	    ArrayList<Vector2f[]> usable_room_exit_pairs = new ArrayList<Vector2f[]>();
	    ArrayList<Vector2f> used_exits = new ArrayList<Vector2f>();
	    for (Vector2f exit : room_exits) {
			Vector2f wef = wheresEmptyFrom((int)exit.x, (int)exit.y);
			if (wef != null) usable_room_exit_pairs.add(new Vector2f[] {exit, wef});
		}
	 
	    Collections.shuffle(usable_room_exit_pairs, new Random(oldseed));
	    
	    for (Vector2f[] exit : usable_room_exit_pairs) 
	    {
	    	for (Vector2f[] otherExit : usable_room_exit_pairs) 
	    	{
				if (!used_exits.contains(otherExit) && otherExit[0].x != exit[0].x && otherExit[0].y != exit[0].y)
				{
					Vector2f other_orig = otherExit[0];
					Vector2f other_outer_exit = otherExit[1];
					Vector2f this_orig = exit[0];
					Vector2f this_exit = exit[1];
					if( isClearFromTo((int)this_exit.x, (int)this_exit.y, (int)other_outer_exit.x, (int)other_outer_exit.y)) {
						// We can now do a corridor here
						drawCorridorFromTo((int)this_exit.x, (int)this_exit.y, (int)other_outer_exit.x, (int)other_outer_exit.y);
			            this.grid[(int)this_orig.x][(int)this_orig.y] = TileType.CorridorFloor;
			            this.grid[(int)other_orig.x][(int)other_orig.y] = TileType.CorridorFloor;
			            used_exits.add(this_orig);
			            used_exits.add(other_orig);
			            break;
			        }
				}
			}
		}
	    
	    TileType[][] smallDungeon = new TileType[this.xsize+30*2][this.ysize+30*2];
	    // fill new dungeon
	    for (int fx = 0; fx < smallDungeon.length; fx++) 
 		{
 			for (int fy = 0; fy < smallDungeon[fx].length; fy++) 
 			{
 				smallDungeon[fx][fy] = TileType.None;
 			}
 		}
	    // copy current dungeon
	    for (int fx = 0; fx < this.grid.length; fx++) {
			for (int fy = 0; fy < this.grid[fx].length; fy++) {
				// Here we can randomize few traps
				if (getTileType(fx, fy) == TileType.CorridorFloor && Math.random() < 0.04)
					smallDungeon[fx+30][fy+30] = TileType.SpikeTrap;
				else
					smallDungeon[fx+30][fy+30] = getTileType(fx, fy);
			}
		}
	    this.grid = smallDungeon;
	    // x3 room + space for final room
	    this.xsize = this.xsize*3 + 30*6;
	    this.ysize = this.ysize*3 + 30*6;
	    this.grid = new TileType[this.xsize][this.ysize];
	    // fill dungeon with white space
 		for (int fx = 0; fx < this.grid.length; fx++) 
 		{
 			for (int fy = 0; fy < this.grid[fx].length; fy++) 
 			{
 				this.grid[fx][fy] = TileType.None;
 			}
 		}
 		// place final room
 		int finalRoomSide = getRand(0, 3);
 		int finalRoomOffset = getRand(0, 85);
 		switch (finalRoomSide) {
			case 0://north
				drawRoom(smallDungeon, finalRoomOffset, 15, RoomTemplate.getFinishingRoom());
				break;
			case 1:// east
				drawRoom(smallDungeon, this.xsize/3-16, finalRoomOffset, RoomTemplate.getFinishingRoom());
				break;
			case 2:// south
				drawRoom(smallDungeon, finalRoomOffset, this.ysize/3-16, RoomTemplate.getFinishingRoom());
				break;
			case 3:// west
				drawRoom(smallDungeon, 15, finalRoomOffset, RoomTemplate.getFinishingRoom());
				break;
		}
 		
 		// Supersize the grid
 		for (int x2 = 0; x2 < smallDungeon.length-30*2; x2++) 
	    {
			for (int y2 = 0; y2 < smallDungeon[x2].length-30*2; y2++) 
			{
				for (int x3 = 0; x3 < 3; x3++) 
				{
					for (int y3 = 0; y3 < 3; y3++) 
					{
						// Here we can randomize few chests
						if (smallDungeon[x2+30][y2+30].isFloor && Math.random() < 0.001)
							this.grid[x2*3+x3][y2*3+y3] = TileType.ChestClosedSouth;
						else if (smallDungeon[x2+30][y2+30] == TileType.Floor || smallDungeon[x2+30][y2+30] == TileType.CorridorFloor)
							this.grid[x2*3+x3][y2*3+y3] = getFloorType();
						else
							this.grid[x2*3+x3][y2*3+y3] = smallDungeon[x2+30][y2+30];
					}
				}
			}
		}
 		
 		surroundEveryFloorWithWall();
	}

}
