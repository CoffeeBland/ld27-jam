package ld27jam.input;

import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

public class KeyboardController implements KeyListener
{
	@Override
	public void inputEnded() 
	{}
	@Override
	public void inputStarted() 
	{}
	
	@Override
	public boolean isAcceptingInput() 
	{return true;}
	@Override
	public void setInput(Input pInput) 
	{}

	@Override
	public void keyPressed(int pKeyNum, char pKey) 
	{
		InputController.keyDown(-1, pKeyNum);
	}
	@Override
	public void keyReleased(int pKeyNum, char pKey) 
	{
		InputController.keyUp(-1, pKeyNum);
	}
	
	protected KeyboardController()
	{
	}
}
