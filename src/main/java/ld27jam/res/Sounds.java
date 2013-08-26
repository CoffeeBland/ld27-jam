package ld27jam.res;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

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
	
	private static Map<String, Sequence> sequences = new HashMap<String, Sequence>();
	private static Sequencer sequencer = null;
	public static void startMusic(String pRef, boolean pLooping) throws Exception
	{
		Sequence sequence;
		if (sequences.containsKey(pRef))
			sequence = sequences.get(pRef);
		else
		{
			sequence = MidiSystem.getSequence(new File(pRef));
			sequences.put(pRef, sequence);
		}
	    
		if (sequencer == null)
	        sequencer = MidiSystem.getSequencer();
		if (!sequencer.isOpen())
			sequencer.open();
	    sequencer.setSequence(sequence);
	    if (pLooping)
	    	sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
	    else
	    	sequencer.setLoopCount(0);
	    
	    // Start playing
	    sequencer.setTickPosition(0);
	    sequencer.start();
	}
	public static void stopMusic() throws Exception
	{
		sequencer.stop();
	    sequencer.close();
	}
}
