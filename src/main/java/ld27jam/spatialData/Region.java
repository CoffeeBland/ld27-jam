package ld27jam.spatialData;

import java.util.HashSet;
import java.util.Set;

import org.newdawn.slick.geom.Vector2f;

public class Region extends AABB
{
	public Region()
	{
		
	}
	public Region(Vector2f pPosition, Vector2f pSize)
	{
		setPosition(pPosition);
		setSize(pSize);
	}
	public Region(float pX1, float pY1, float pX2, float pY2)
	{
		this(new Vector2f(pX1, pY1), new Vector2f(pX2 - pX1, pY2 - pY1));
	}

	private Set<Set<? extends Region>> mContainingTiles;
	public Set<Set<? extends Region>> getContainingTiles()
	{
		if (mContainingTiles == null)
			mContainingTiles = new HashSet<Set<? extends Region>>();
		return mContainingTiles;
	}
}