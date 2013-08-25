package ld27jam.entities;

import java.lang.reflect.Array;
import java.util.*;

import ld27jam.spatialData.Region;

import org.newdawn.slick.geom.Vector2f;

public class Dungeon {
	//size of the map
	public int xsize = 0;
	public int ysize = 0;

	public static long oldseed = 0;
	private TileType[][] dungeon = {};
	private boolean startingPointSet = false;
 
	public void setCell(int x, int y, TileType celltype){
		dungeon[x][y] = celltype;
	}
 
	public TileType getTileType(int x, int y) {
		return dungeon[x][y];
	}
	
	private int getRand(int mean, int deviance){
		Date now = new Date();
		long seed = now.getTime() + oldseed;
	
		int rnd = (int) Math.abs( (new Random(seed)).nextGaussian() * mean + deviance );
		
		this.oldseed = seed;
		return rnd;
	}
	
	private int getRandTrue(int min, int max)
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
		for (int y = 0; y < ysize-1; y++)
		{
			for (int x = 0; x < xsize-1; x++)
			{
				switch(getTileType(x, y).ordinal()){
					case 0:
						System.out.print(".");
						break;
					case 1:
						System.out.print("O");
						break;
					case 2:
						System.out.print(" ");
						break;
				};
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
				? this.dungeon[x+1][y] : null);
	}
	private TileType westFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.dungeon[x-1][y] : null);
	}
	private TileType northFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.dungeon[x][y-1] : null);
	}
	private TileType southFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.dungeon[x][y+1] : null);
	}
	
	private TileType northeastFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.dungeon[x+1][y-1] : null);
	}
	private TileType southeastFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.dungeon[x+1][y+1] : null);
	}
	private TileType northwestFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.dungeon[x-1][y-1] : null);
	}
	private TileType southwestFrom(int x, int y) 
	{
		return (x > 0 && y > 0 && x < this.xsize && y < this.ysize
				? this.dungeon[x-1][y+1] : null);
	}
	
	private void surroundEveryFloorWithWall()
	{
	    for (int x = 0; x < dungeon.length; x++) 
	    {
			for (int y = 0; y < dungeon[x].length; y++) 
			{
				if(dungeon[x][y] == TileType.Floor) 
				{
		          if(northFrom(x,y) == TileType.None)     dungeon[x  ][y-1]   = TileType.Wall;
		          if(southFrom(x,y) == TileType.None)     dungeon[x  ][y+1]   = TileType.Wall;
		          if(westFrom(x,y) == TileType.None)      dungeon[x-1][y  ]   = TileType.Wall;
		          if(eastFrom(x,y) == TileType.None)      dungeon[x+1][y  ]   = TileType.Wall;
		          if(northeastFrom(x,y) == TileType.None) dungeon[x+1][y-1]   = TileType.Wall;
		          if(southeastFrom(x,y) == TileType.None) dungeon[x+1][y+1]   = TileType.Wall;
		          if(northwestFrom(x,y) == TileType.None) dungeon[x-1][y-1]   = TileType.Wall;
		          if(southwestFrom(x,y) == TileType.None) dungeon[x-1][y+1]   = TileType.Wall;
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

		for(int x = start_x; x <= end_x; x++) {
			for(int y = start_y; y <= end_y; y++) {
				if (this.dungeon[x][y] != TileType.None) return false;
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
	      this.dungeon[x][y] = TileType.Floor;
	      if(x != x2 && Math.random() > 0.5) {
	        x += h_mod;
	      } else if(y != y2) {
	        y += v_mod;
	      }
	    }
	    this.dungeon[x][y] = TileType.Floor;
	}

	// and here's the one generating the whole map
	public void createDungeon(int inx, int iny, RoomTemplate[] templates){
		this.xsize = inx;
		this.ysize = iny;
		this.dungeon = new TileType[inx+1][iny+1];
		
		int spacing = 3;
		int tallest_height = 0; 
		int widest_width = 0;
		for (RoomTemplate t : templates) 
		{
			if (t.getWidth() > widest_width) widest_width = t.getWidth();
			if (t.getHeight() > tallest_height) tallest_height = t.getHeight();
		}
		
		// fill dungeon with white space
		for (int x = 0; x < this.dungeon.length; x++) 
		{
			for (int y = 0; y < this.dungeon[x].length; y++) 
			{
				this.dungeon[x][y] = TileType.None;
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
	        		a_room = templates[getRandTrue(0, templates.length-1)];
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
	          gaps[getRandTrue(0, gaps.length-1)] += 1;
	        }

	        // shift ahead past first gap
	        int x = gaps[0];
	        int index = 0;
	        for (RoomTemplate row_room : rooms) 
	        {
				int extra_y_offset = getRandTrue(0, row_tallest_height - row_room.getHeight());
				
				for (int x_in_room = 0; x_in_room < row_room.getWidth(); x_in_room++) 
				{
					for (int y_in_room = 0; y_in_room < row_room.getHeight(); y_in_room++) 
					{
						this.dungeon[Math.min(x + x_in_room, this.xsize)][Math.min(y + y_in_room + extra_y_offset, this.ysize)] = row_room.getTileAtCell(x_in_room, y_in_room);
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
					if( isClearFromTo((int)this_exit.x, (int)this_exit.y, (int)other_outer_exit.x, (int)other_outer_exit.y) ) {
			            drawCorridorFromTo((int)this_exit.x, (int)this_exit.y, (int)other_outer_exit.x, (int)other_outer_exit.y);
			            this.dungeon[(int)this_orig.x][(int)this_orig.y] = TileType.Floor;
			            this.dungeon[(int)other_orig.x][(int)other_orig.y] = TileType.Floor;
			            used_exits.add(this_orig);
			            used_exits.add(other_orig);
			        }
				}
			}
		}
	    
	    
	    
	    TileType[][] smallDungeon = new TileType[this.xsize][this.ysize];
	    for (int fx = 0; fx < smallDungeon.length; fx++) {
			for (int fy = 0; fy < smallDungeon[fx].length; fy++) {
				smallDungeon[fx][fy] = getTileType(fx, fy);
			}
		}
	    this.dungeon = smallDungeon;
	    this.xsize = this.xsize*3;
	    this.ysize = this.ysize*3;
	    this.dungeon = new TileType[this.xsize][this.ysize];
	    // fill dungeon with white space
 		for (int fx = 0; fx < this.dungeon.length; fx++) 
 		{
 			for (int fy = 0; fy < this.dungeon[fx].length; fy++) 
 			{
 				this.dungeon[fx][fy] = TileType.None;
 			}
 		}
 		for (int x2 = 0; x2 < smallDungeon.length; x2++) 
	    {
			for (int y2 = 0; y2 < smallDungeon[x2].length; y2++) 
			{
				for (int x3 = 0; x3 < 3; x3++) 
				{
					for (int y3 = 0; y3 < 3; y3++) 
					{
						this.dungeon[x2*3+x3][y2*3+y3] = smallDungeon[x2][y2];
					}
				}
			}
		}
 		
 		surroundEveryFloorWithWall();
	    
	}

}
