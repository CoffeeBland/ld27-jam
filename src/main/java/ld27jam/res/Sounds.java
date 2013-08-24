package ld27jam.res;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Sounds 
{
	private Sounds(){}
	
	private static Map<String, Sound> mSounds = new HashMap<String, Sound>();

	public static Sound get(String pRef) throws SlickException
	{
		if(mSounds.containsKey(pRef))
			return mSounds.get(pRef);
		
		Sound sound = new Sound(pRef);
		mSounds.put(pRef, sound);
		return sound;
	}
}
