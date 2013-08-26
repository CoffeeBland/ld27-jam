package ld27jam.entities;

import ld27jam.World;
import ld27jam.res.ImageSheet;
import ld27jam.res.Sounds;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Ghoul extends Entity
{
	public Ghoul(Vector2f position) throws SlickException 
	{
		super(position, new Vector2f(0.8f, 0.8f), false, new Vector2f(-14, -64), new ImageSheet("res/sprites/Ghoul.png", 64, 80));
	}
	
	private float charDist;
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta, World world) throws SlickException 
	{
		super.update(gc, sbg, delta, world);

		charDist = world.character.getPosition().distance(getPosition());
		float drain = 0.04f - charDist / 80;
		if (drain > 0.01f)
		{
			imageSheet.setFrameX(2);
			Sounds.get("res/audio/Scream.ogg").play((float)(1.4 + Math.random() * 0.6), drain * 20f);
			Sounds.get("res/audio/Scream2.ogg").play((float)(1.4 + Math.random() * 0.6), drain * 20f);
		}
		else if (drain > -0.1f)
		{
			imageSheet.setFrameX(1);
			if (drain > 0)
			{
				Sounds.get("res/audio/Scream.ogg").play((float)(0.9 + Math.random() * 0.4), Math.max(0, drain) * 20f);
				Sounds.get("res/audio/Scream2.ogg").play((float)(0.9 + Math.random() * 0.4), Math.max(0, drain) * 20f);
			}
			else
				Sounds.get("res/audio/Scream.ogg").play((float)(0.2 + Math.random() * 0.4), Math.max(0, drain + 0.1f));
		}
		else
			imageSheet.setFrameX(0);
		world.character.drainSanity(Math.max(drain, 0), world);
		
		float distX = getPosition().x - world.character.getPosition().x,
			  distY = getPosition().y - world.character.getPosition().y,
			  pseudoAngle = (distY - distX) / (Math.abs(distX) + Math.abs(distY));
		if (pseudoAngle <= -0.35)
			imageSheet.setFrameY(0);
		else if (pseudoAngle >= 0.35)
			imageSheet.setFrameY(2);
		else
				imageSheet.setFrameY(1);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g, Vector2f camera, Color color) throws SlickException
	{
		float dist = Math.max((0.05f - charDist / 80) * 100, 0);
		super.render(gc, sbg, g, camera.copy().add(new Vector2f((float)(Math.random() - 0.5) * dist, (float)(Math.random() - 0.5) * dist)), color);
	}
}
