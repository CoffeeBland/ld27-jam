package ld27jam.res;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class ImageSheetDef 
{
	public int frameWidth;
	public int frameHeight;
	public int frameAmountX;
	public int frameAmountY;
	
	public Image[][] frames;
	
	public ImageSheetDef(String pRef, int pFrameWidth, int pFrameHeight) throws SlickException
	{
		frames = Images.getFrames(pRef, pFrameWidth, pFrameHeight);
		
		frameWidth = pFrameWidth;
		frameHeight = pFrameHeight;
		
		frameAmountX = frames.length;
		if (frames.length > 0)
			frameAmountY = frames[0].length;
		else
			frameAmountY = 0;
	}
	public ImageSheetDef(Image[][] pFrames)
	{
		frames = pFrames;

		frameAmountX = frames.length;
		if (frames.length > 0)
			frameAmountY = frames[0].length;
		else
			frameAmountY = 0;
		
		if (frameAmountY > 0)
		{
			frameWidth = frames[0][0].getWidth();
			frameHeight = frames[0][0].getHeight();
		}
	}
	
	
	public static ImageSheetDef getDefinition(ImageSheet pImageSheet)
	{
		return new ImageSheetDef(pImageSheet.getFrames());
	}
}
