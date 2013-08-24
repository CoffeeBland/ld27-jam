package ld27jam.spatialData;

import org.newdawn.slick.geom.Vector2f;

public abstract class AABB 
{
	private Vector2f mPosition = new Vector2f();
	public Vector2f getPosition()
	{
		return mPosition;
	}
	public void setPosition(Vector2f pPosition)
	{
		mPosition = pPosition;
	}
	
	private Vector2f mSize = new Vector2f();
	public Vector2f getSize()
	{
		return mSize;
	}
	public void setSize(Vector2f pSize)
	{
		mSize = pSize;
	}

	public Vector2f getHalfSize()
	{
		return getSize().copy().scale(0.5f);
	}
	public Vector2f getCenterPoint()
	{
		return getHalfSize().add(getPosition());
	}
	public float getRightX()
	{
		return getPosition().x + getSize().x;
	}
	public float getBottomY()
	{
		return getPosition().y + getSize().y;
	}
	
	public Vector2f getUpperLeftPoint()
	{
		return new Vector2f(getPosition().x, getPosition().y);
	}
	public Vector2f getUpperRightPoint()
	{
		return new Vector2f(getPosition().x + getSize().x, getPosition().y);
	}
	public Vector2f getBottomLeftPoint()
	{
		return new Vector2f(getPosition().x, getPosition().y + getSize().y);
	}
	public Vector2f getBottomRightPoint()
	{
		return new Vector2f(getPosition().x + getSize().x, getPosition().y + getSize().y);
	}
	
	public boolean containsPoint(Vector2f pPoint)
	{
		return pPoint.x > getPosition().x && 
			   pPoint.x < getPosition().x + getSize().x &&
			   pPoint.y > getPosition().y && 
			   pPoint.y < getPosition().y + getSize().y;
	}
	public boolean containsRegion(AABB pRegion)
	{
		return getPosition().x < pRegion.getPosition().x + pRegion.getSize().x &&
			   getPosition().x + getSize().x > pRegion.getPosition().x &&
			   getPosition().y < pRegion.getPosition().y + pRegion.getSize().y &&
			   getPosition().y + getSize().y > pRegion.getPosition().y;
	}
	public boolean fullyContainsRegion(AABB pRegion)
	{
		return getPosition().x < pRegion.getPosition().x &&
			   getPosition().x + getSize().x > pRegion.getPosition().x + pRegion.getSize().x &&
			   getPosition().y < pRegion.getPosition().y &&
			   getPosition().y + getSize().y > pRegion.getPosition().y + pRegion.getSize().y;
	}

	@Override
	public String toString()
	{
		return "(" + getPosition().x + ", " + getPosition().y + ") with size (" + getSize().x + ", " + getSize().y + ")";
	}
}
