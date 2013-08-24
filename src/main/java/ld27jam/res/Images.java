package ld27jam.res;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images
{
	private Images() {}
	
	private static Map<String, Image> mImages = new HashMap<String, Image>();
	public static Image get(String pRef) throws SlickException
	{
		if (mImages.containsKey(pRef))
			return mImages.get(pRef);
		
		Image image = new Image(pRef);
		mImages.put(pRef, image);
		return image;
	}
	
	private static Map<String, Image[][]> mFrames = new HashMap<String, Image[][]>();
	public static Image[][] getFrames(String pRef, int pFrameWidth, int pFrameHeight) throws SlickException
	{
		if (mFrames.containsKey(pRef))
			return mFrames.get(pRef);

		Image image = get(pRef);
		int frameAmountX = image.getWidth() / pFrameWidth,
			frameAmountY = image.getHeight() / pFrameHeight;
		
		Image[][] frames = new Image[frameAmountX][frameAmountY];
		for (int x = 0; x < frameAmountX; x++)
			for (int y = 0; y < frameAmountY; y++)
				frames[x][y] = image.getSubImage(x * pFrameWidth, y * pFrameHeight, pFrameWidth, pFrameHeight);
		mFrames.put(pRef, frames);
		return frames;
	}
}
