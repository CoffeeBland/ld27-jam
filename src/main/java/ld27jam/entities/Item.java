package ld27jam.entities;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import ld27jam.res.ImageSheet;

public class Item 
{
	public static ImageSheet itemSheet;
	
	public ItemType type;
	
	public void render(Vector2f position) throws SlickException
	{
		if (itemSheet == null)
			itemSheet = new ImageSheet("res/sprites/ItemSheet.png", 24, 24);
		
		itemSheet.setFrameX(type.imgIdX);
		itemSheet.setFrameY(type.imgIdY);
		itemSheet.render(position);
	}
	
	public Item(ItemType type)
	{
		this.type = type;
	}
}
