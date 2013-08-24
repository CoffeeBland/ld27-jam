package ld27jam.res;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AnimatedSprite extends ImageSheet
{
	private int mFramesPerAnim;
	public int getFramesPerAnim()
	{
		return mFramesPerAnim;
	}
	public void setFramesPerAnim(int pFramesPerAnim)
	{
		mFramesPerAnim = pFramesPerAnim;
	}
	
	private int frameCounter = 0;
	
	@Override
	public void update()
	{
		super.update();
		
		frameCounter++;
		while (frameCounter >=getFramesPerAnim())
		{
			frameCounter -= getFramesPerAnim();
			setFrameX(getFrameX() + 1);
		}
	}
	
	public AnimatedSprite(AnimatedSpriteDef pDef)
	{
		super(pDef);
		setFramesPerAnim(pDef.framesPerAnim);
	}
	public AnimatedSprite(String pRef, int pFrameWidth, int pFrameHeight, int pFramesPerAnim) throws SlickException 
	{
		super(pRef, pFrameWidth, pFrameHeight);
		setFramesPerAnim(pFramesPerAnim);
	}
	private AnimatedSprite(int pFrameWidth, int pFrameHeight, int pFrameAmountX, int pFrameAmountY, Image[][] pFrames, int pFramesPerAnim)
	{
		super(pFrameWidth, pFrameHeight, pFrameAmountX, pFrameAmountY, pFrames);
		setFramesPerAnim(pFramesPerAnim);
	}

	@Override
	public AnimatedSprite copy()
	{
		return new AnimatedSprite(getFrameWidth(), getFrameHeight(), getFrameAmountX(), getFrameAmountY(), mFrames, mFramesPerAnim);
	}
}
