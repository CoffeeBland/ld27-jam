package ld27jam.input;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.GameContainer;

public class InputController 
{	
	private InputController(){}
	
	private static KeyboardController keyboardController;
	private static GamepadController gamepadController;
	private static Object mActiveControl;
	public static void setActiveControl(Object pActiveControl)
	{
		mActiveControl = pActiveControl;
	}
	public static Object getActiveControl()
	{
		return mActiveControl;
	}
	
	public static Set<KeyMapping> pressedKeys = new HashSet<KeyMapping>();
	
	public static void update()
	{
		for (KeyMapping key : pressedKeys)
			key.whileDown();
	}
	public static void keyDown(int pController, int pKeyCode)
	{
		for (KeyMapping key : KeyMapping.values())
			if (key.getKey() == pKeyCode)
			{
				key.activation();
				pressedKeys.add(key);
			}
	}
	public static void keyUp(int pControlleur, int pKeyCode)
	{
		for (KeyMapping key : KeyMapping.values())
			if (key.getKey() == pKeyCode)
			{
				key.deactivation();
				pressedKeys.remove(key);
			}
	}
	
	public static void init(GameContainer pGC)
	{
		keyboardController = new KeyboardController();
		keyboardController.setInput(pGC.getInput());
		pGC.getInput().addKeyListener(keyboardController);
		
		gamepadController = new GamepadController();
		gamepadController.setInput(pGC.getInput());
		pGC.getInput().addControllerListener(gamepadController);
	}
}