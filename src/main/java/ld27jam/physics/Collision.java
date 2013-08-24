package ld27jam.physics;

import ld27jam.spatialData.AABB;

import org.newdawn.slick.geom.Vector2f;

public class Collision 
{
	public AABB collisionX;
	public AABB collisionY;
	
	public Vector2f distance;
	public boolean inCollisionTop;
	public boolean inCollisionRight;
	public boolean inCollisionBottom;
	public boolean inCollisionLeft;
	
	public Collision()
	{}
	public Collision(Vector2f pDistance)
	{ distance = pDistance; }
}
