package ld27jam.input;

import java.util.HashSet;
import java.util.Set;

public enum KeyMapping 
{
	Movement(44),
	Offense(45),
	Defense(46),
	Other(47),
	Action(48),
	Jump(57),
	Left(203),
	Right(205),
	Up(200),
	Down(208),
	Start(28),
	Cancel(1);

	private boolean mIsDown = false;
	public boolean isDown()
	{
		return mIsDown;
	}

	private int mKey;
	public int getKey()
	{
		return mKey;
	}
	public void setKey(int pKey)
	{
		mKey = pKey;
	}

	private int mDefaultKey;
	public int getDefaultKey()
	{
		return mDefaultKey;
	}
	
	private Set<ControlEvent> mEvents = new HashSet<ControlEvent>();
	public void subscribe(ControlEvent pEvent)
	{
		mEvents.add(pEvent);
	}
	public void unsubscribe(ControlEvent pEvent)
	{
		mEvents.remove(pEvent);
	}

	public void activation()
	{
		Set<ControlEvent> toRemove = new HashSet<ControlEvent>();
		for (ControlEvent event : mEvents)
			if (event.associatedControl == null || InputController.getActiveControl() == event.associatedControl)
			{	
				event.keyDown();
				if (event.shouldBeRemoved())
					toRemove.add(event);
			}
		for (ControlEvent event : toRemove)
		{
			unsubscribe(event);
			event.resetDeactivation();
		}
		mIsDown = true;
	}
	public void deactivation()
	{
		Set<ControlEvent> toRemove = new HashSet<ControlEvent>();
		for (ControlEvent event : mEvents)
			if (event.associatedControl == null || InputController.getActiveControl() == event.associatedControl)
			{
				event.keyUp();
				if (event.shouldBeRemoved())
					toRemove.add(event);
			}
		for (ControlEvent event : toRemove)
		{
			unsubscribe(event);
			event.resetDeactivation();
		}
		mIsDown = false;
	}
	public void whileDown()
	{
		Set<ControlEvent> toRemove = new HashSet<ControlEvent>();
		for (ControlEvent event : mEvents)
			if (event.associatedControl == null || InputController.getActiveControl() == event.associatedControl)
			{	
				event.keyIsDown();
				if (event.shouldBeRemoved())
					toRemove.add(event);
			}
		for (ControlEvent event : toRemove)
		{
			unsubscribe(event);
			event.resetDeactivation();
		}
	}

	private KeyMapping(int pDefaultKey)
	{
		setKey(pDefaultKey);
		mDefaultKey = pDefaultKey;
	}
}