package ld27jam.res;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class AnimatedSpriteDef extends ImageSheetDef
{
	public int framesPerAnim;
	
	public AnimatedSpriteDef(String pRef, int pFrameWidth, int pFrameHeight, int pFramesPerAnim) throws SlickException
	{
		super(pRef, pFrameWidth, pFrameHeight);
		framesPerAnim = pFramesPerAnim;
	}
	public AnimatedSpriteDef(Image[][] pFrames, int pFramesPerAnim)
	{
		super(pFrames);
		framesPerAnim = pFramesPerAnim;
	}
	
	public static AnimatedSpriteDef getDefinition(AnimatedSprite pAnimatedSprite)
	{
		return new AnimatedSpriteDef(pAnimatedSprite.getFrames(), pAnimatedSprite.getFramesPerAnim());
	}
}
