package ld27jam.entities;

import ld27jam.helpers.FontFactory;
import ld27jam.res.Images;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Hourglass 
{
	public Image image, splash;
	public Color splashColor = new Color(70, 60, 80);
	public int hourglassWidth = 32, hourglassHeight = 64;
	public Vector2f distanceToUpperRightCorner = new Vector2f(16, 16);
	
	public void render(GameContainer gc, float sanityRatio) throws SlickException
	{
		if (image == null)
			image = Images.get("res/sprites/Hourglass.png");
		if (splash == null)
			splash = Images.get("res/sprites/HourglassSplash.png");

		float x1, x2, y1, y2, sanityHeight;
		
		x2 = gc.getWidth() - distanceToUpperRightCorner.x;
		x1 = x2 - hourglassWidth;

		y1 = distanceToUpperRightCorner.y;
		y2 = y1 + hourglassHeight;
		
		splash.startUse();
		splashColor.a = 1 - sanityRatio;
		splash.drawEmbedded(x1 - hourglassWidth / 2, y1, x2 + hourglassWidth / 2, y2, 0, 0, splash.getWidth(), splash.getWidth(), splashColor);
		splash.endUse();
		
		image.startUse();
		
		sanityHeight = (1 - sanityRatio) * 52;

		image.drawEmbedded(x1, y1, x2, y2, hourglassWidth, 0, hourglassWidth * 2, hourglassHeight);
		
		image.drawEmbedded(x1, y1 + sanityHeight, x2, y2, hourglassWidth * 2, sanityHeight, hourglassWidth * 3, hourglassHeight);

		image.drawEmbedded(x1, y1, x2, y2, 0, 0, hourglassWidth, hourglassHeight);
		
		image.endUse();
		
		float timer = Math.round(sanityRatio * 100f) / 10f;
		FontFactory.get().getFont(16, java.awt.Color.white).drawString(x1 + 4, y2 + 8, ((Float)timer).toString());
	}
}
