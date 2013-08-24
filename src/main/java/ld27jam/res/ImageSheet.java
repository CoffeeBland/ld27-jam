package ld27jam.res;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class ImageSheet 
{
	private int mFrameWidth;
	public int getFrameWidth()
	{
		return mFrameWidth;
	}
	private int mFrameHeight;
	public int getFrameHeight()
	{
		return mFrameHeight;
	}
	
	private int mFrameAmountX;
	public int getFrameAmountX()
	{
		return mFrameAmountX;
	}
	private int mFrameAmountY;
	public int getFrameAmountY()
	{
		return mFrameAmountY;
	}

	private int mFrameX = 0;
	public int getFrameX()
	{
		return mFrameX;
	}
	public void setFrameX(int pFrameX)
	{
		mFrameX = pFrameX % getFrameAmountX();
	}
	
	private int mFrameY = 0;
	public int getFrameY()
	{
		return mFrameY;
	}
	public void setFrameY(int pFrameY)
	{
		mFrameY = pFrameY % getFrameAmountY();
	}
	
	private static ImageSheet SHEET_IN_USE = null;
	
	protected Image[][] mFrames;
	public Image getFrame(int pFrameX, int pFrameY)
	{
		return mFrames[pFrameX][pFrameY];
	}
	public Image getFrame()
	{
		return getFrame(getFrameX(), getFrameY());
	}
	public Image[][] getFrames()
	{
		return mFrames;
	}
	
	private void renderEmbedded(Vector2f pPosition)
	{
		getFrame().drawEmbedded(pPosition.x, pPosition.y);
	}
	
	public void render(Vector2f pPosition)
	{
		if (SHEET_IN_USE != null)
		{
			SHEET_IN_USE.getFrame().endUse();
			SHEET_IN_USE = null;
		}
		getFrame().startUse();
		
		renderEmbedded(pPosition);
		
		getFrame().endUse();
	}
	
	public void renderBatch(Vector2f pPosition, int pFrameNumX, int pFrameNumY)
	{
		if (SHEET_IN_USE != this)
		{
			if (SHEET_IN_USE != null)
				SHEET_IN_USE.getFrame().endUse();
			SHEET_IN_USE = this;
			this.getFrame().startUse();
		}
		
		renderEmbedded(pPosition);
	}
	public static void finishRenderBatch()
	{
		if (SHEET_IN_USE != null)
		{
			SHEET_IN_USE.getFrame().endUse();
			SHEET_IN_USE = null;
		}
	}
	
	public void update()
	{
	}
	
	public ImageSheet(ImageSheetDef pDef)
	{
		mFrameWidth = pDef.frameWidth;
		mFrameHeight = pDef.frameHeight;
		mFrameAmountX = pDef.frameAmountX;
		mFrameAmountY = pDef.frameAmountY;
		mFrames = pDef.frames;
	}
	public ImageSheet(String pRef, int pFrameWidth, int pFrameHeight) throws SlickException
	{
		mFrameWidth = pFrameWidth;
		mFrameHeight = pFrameHeight;
		
		mFrames = Images.getFrames(pRef, pFrameWidth, pFrameHeight);
		mFrameAmountX = mFrames.length;
		if (mFrames.length > 0)
			mFrameAmountY = mFrames[0].length;
		else
			mFrameAmountY = 0;
	}
	protected ImageSheet(int pFrameWidth, int pFrameHeight, int pFrameAmountX, int pFrameAmountY, Image[][] pFrames)
	{
		mFrameWidth = pFrameWidth;
		mFrameHeight = pFrameHeight;
		mFrameAmountX = pFrameAmountX;
		mFrameAmountY = pFrameAmountY;
		mFrames = pFrames;
	}

	public ImageSheet copy()
	{
		return new ImageSheet(getFrameWidth(), getFrameHeight(), getFrameAmountX(), getFrameAmountY(), getFrames());
	}
}
