package ld27jam.input;

import org.newdawn.slick.ControllerListener;
import org.newdawn.slick.Input;

public class GamepadController implements ControllerListener 
{
	@Override
	public void inputEnded() 
	{}
	@Override
	public void inputStarted() 
	{}

	@Override
	public boolean isAcceptingInput() 
	{ return true; }
	@Override
	public void setInput(Input pInput) 
	{}

	@Override
	public void controllerButtonPressed(int pController, int pBoutton) 
	{
		InputController.keyDown(pController, pBoutton);
	}
	@Override
	public void controllerButtonReleased(int pController, int pBoutton) 
	{
		InputController.keyUp(pController, pBoutton);
	}

	@Override
	public void controllerDownPressed(int pController) 
	{}
	@Override
	public void controllerDownReleased(int pController)
	{}

	@Override
	public void controllerLeftPressed(int pController)
	{}
	@Override
	public void controllerLeftReleased(int pController)
	{}

	@Override
	public void controllerRightPressed(int pController)
	{}
	@Override
	public void controllerRightReleased(int pController) 
	{}

	@Override
	public void controllerUpPressed(int pController) 
	{}
	@Override
	public void controllerUpReleased(int pController)
	{}

	protected GamepadController()
	{
	}
}
