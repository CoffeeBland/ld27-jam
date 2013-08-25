package ld27jam.entities;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class RoomTemplate {

	String stringTemplate;
	char[][] template;
	private Vector2f exitsOffset;
	
	public RoomTemplate(String template)
	{
		this.stringTemplate = template;
		String[] lines = template.split("\n");
		this.template = new char[lines[0].length()][lines.length];
		for (int y = 0; y < lines.length; y++) 
		{
			for (int x = 0; x < lines[y].length(); x++) 
			{
				this.template[x][y] = lines[y].charAt(x);
			}
		}
	}
	
	public int getHeight()
	{
		return this.template[0].length;
	}
	
	public int getWidth()
	{
		return this.template.length;
	}
	
	public TileType getTileAtCell(int x, int y)
	{
		char tile = template[x][y];
		switch (tile) 
		{
			case 'W':
				return TileType.Wall;
			case 'f':
				return TileType.Floor;
			case '+':
				return TileType.Floor;
			default:
				return TileType.None;
		}
	}
	
	public ArrayList<Vector2f> exits()
	{
		ArrayList<Vector2f> exitsArray = new ArrayList<Vector2f>();
		
		for (int x_in_room = 0; x_in_room < this.getWidth(); x_in_room++) 
		{
			for (int y_in_room = 0; y_in_room < this.getHeight(); y_in_room++) 
			{
				if (this.template[x_in_room][y_in_room] == '+') 
					exitsArray.add(new Vector2f(x_in_room + this.exitsOffset.x, y_in_room + this.exitsOffset.y));
			}
		}
		
		return exitsArray;
	}
	
	public void setExitsOffset(Vector2f off)
	{
		this.exitsOffset = off;
	}
	
}
