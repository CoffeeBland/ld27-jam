package ld27jam.entities;

import java.util.HashSet;
import java.util.Set;

import ld27jam.helpers.FontFactory;
import ld27jam.res.ImageSheet;

import org.newdawn.slick.Font;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Inventory 
{
	public static Font whiteFont = FontFactory.get().getFont(16, java.awt.Color.white);
	
	public ImageSheet background;
	public Vector2f position = new Vector2f(16, 16), itemDecal = new Vector2f(4, 16);
	public Set<Item> items = new HashSet<Item>();
	
	public void render() throws SlickException
	{
		Vector2f pos = new Vector2f(position.x, position.y);
		
		// Background
		background.setFrameY(0);
		background.render(pos);
		pos.y += 32;

		background.setFrameY(1);
		for (int repeat = 1; repeat < items.size(); repeat++)
		{
			background.render(pos);
			pos.y += 32;
		}
		background.setFrameY(2);
		background.render(pos);
		
		// Items		
		pos.y = position.y;
		for (Item item : items)
		{
			item.render(pos.copy().add(itemDecal));
			pos.y += 32;
		}

	}
	
	public Inventory() throws SlickException
	{
		background = new ImageSheet("res/sprites/Inventory.png", 32, 32);
	}
}
