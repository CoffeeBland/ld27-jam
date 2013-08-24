package ld27jam.input;


public abstract class Activable 
{
	public abstract void activateExtension();
	public abstract void deactivateExtension();
	private boolean mIsActive;
	public boolean isActive()
	{
		return mIsActive;
	}
	
	private Object mPreviousActiveObject;
	private Object getPreviousActiveObject()
	{
		return mPreviousActiveObject;
	}
	private void setPreviousActiveObject(Object pObject)
	{
		mPreviousActiveObject = pObject;
	}
	
	public void activate()
	{
		if (isActive())
			return;
		mIsActive = true;
		
		activateExtension();
		
		setPreviousActiveObject(InputController.getActiveControl());
		InputController.setActiveControl(this);
		if (getPreviousActiveObject() != null && getPreviousActiveObject() instanceof Activable)
			((Activable)getPreviousActiveObject()).deactivate();
	}
	public void deactivate()
	{
		if (!isActive())
			return;
		mIsActive = false;
		
		deactivateExtension();
	}
	public void deactivateThisAndActivatePreviousObject()
	{
		if (getPreviousActiveObject() != null)
		{
			if (getPreviousActiveObject() instanceof Activable)
				((Activable)getPreviousActiveObject()).activate();
			else
				InputController.setActiveControl(getPreviousActiveObject());
			setPreviousActiveObject(null);
		}
		else
			deactivate();
	}
}
