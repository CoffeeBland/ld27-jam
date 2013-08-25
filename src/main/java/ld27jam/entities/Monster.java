package ld27jam.entities;

import ld27jam.World;
import ld27jam.res.ImageSheet;

import org.newdawn.slick.geom.Vector2f;

public class Monster extends Entity
{
	public Monster(Vector2f position, Vector2f size, boolean canBeCollided, Vector2f visualDecal, ImageSheet imageSheet) 
	{
		super(position, size, canBeCollided, visualDecal, imageSheet);
	}

	@Override
	public void checkForWalkableTypeResolution(World world, Tile tile) {
		// TODO Auto-generated method stub
		
	}
}
