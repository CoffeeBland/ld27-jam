package ld27jam.input;

public abstract class ControlEvent 
{
	public Object associatedControl;
	
	public abstract void keyDown();
	public abstract void keyUp();
	public abstract void keyIsDown();
	
	private boolean mShouldBeRemoved = false;
	protected boolean shouldBeRemoved()
	{
		return mShouldBeRemoved;
	}
	protected void resetDeactivation()
	{
		mShouldBeRemoved = false;
	}
	protected void deactivate()
	{
		mShouldBeRemoved = true;
	}
	
	public ControlEvent(Object pAssociatedControl)
	{
		associatedControl = pAssociatedControl;
	}
	public ControlEvent()
	{
		associatedControl = null;
	}
}
