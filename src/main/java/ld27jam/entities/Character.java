package ld27jam.entities;

import ld27jam.World;
import ld27jam.input.ControlEvent;
import ld27jam.input.KeyMapping;
import ld27jam.res.AnimatedSprite;
import ld27jam.states.GameOverState;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class Character extends Entity
{
	public float lightVariation = 0.1f, lightBase = 300, lightMoment = 0, speed = 0.15f;
	public Color lightColor = new Color(255, 230, 180);
	public boolean isMovingDiagonally;
	public float sanity = 10;
	public float maxSanity = 10;

	public int animUp = 5, animUpLeft = 6, animLeft = 7, animDownLeft = 0, animDown = 1, animDownRight = 2, animRight = 3, animUpRight = 4;
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta, World world) throws SlickException
	{
		sanity -= 1f / (world.gd.level.timeStageDurations[(int) Math.floor(10 - sanity)] * 60f);
		if (sanity <= 0.0001f)
			sbg.enterState(GameOverState.ID, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 800));
		else
		{
			float sanityRatio = ((30 - Math.min(maxSanity /  sanity, 50)) / 30);
			lightMoment += 0.005f;
			lightVariation = (float) Math.sin(lightMoment) * 0.05f;
			lightColor.r = sanityRatio * 0.4f + 0.6f;
			lightColor.g = sanityRatio * 0.95f;
			lightColor.b = sanityRatio * 0.3f + 0.45f;
			lightBase = sanityRatio * 100 + 200;
		}	
		
		super.update(gc, sbg, delta, world);
		isMovingDiagonally = true;
		// Up
		if (KeyMapping.Up.isDown())
			if (KeyMapping.Left.isDown())
				imageSheet.setFrameY(animUpLeft);
			else if (KeyMapping.Right.isDown())
				imageSheet.setFrameY(animUpRight);
			else
			{
				imageSheet.setFrameY(animUp);
				isMovingDiagonally = false;
			}
		// Down
		else if (KeyMapping.Down.isDown())
			if (KeyMapping.Left.isDown())
				imageSheet.setFrameY(animDownLeft);
			else if (KeyMapping.Right.isDown())
				imageSheet.setFrameY(animDownRight);
			else
			{
				imageSheet.setFrameY(animDown);
				isMovingDiagonally = false;
			}
		// Left
		else if (KeyMapping.Left.isDown())
		{
			imageSheet.setFrameY(animLeft);
			isMovingDiagonally = false;
		}
		// Right
		else if (KeyMapping.Right.isDown())
		{
			imageSheet.setFrameY(animRight);
			isMovingDiagonally = false;
		}
	}
	
	public void init (final World world)
	{
		final Character character = this;
		KeyMapping.Left.subscribe(new ControlEvent()
		{
			@Override
			public void keyDown() 
			{
			}
			@Override
			public void keyUp()
			{
			}
			@Override
			public void keyIsDown() 
			{
				if (isMovingDiagonally)
					move(new Vector2f(-speed * 0.6f, speed * 0.6f), world);
				else
					move(new Vector2f(-speed, speed), world);
				world.spatialMap.update(character);
			}
		});
		KeyMapping.Right.subscribe(new ControlEvent()
		{
			@Override
			public void keyDown() 
			{
			}
			@Override
			public void keyUp()
			{
			}
			@Override
			public void keyIsDown() 
			{
				if (isMovingDiagonally)
					move(new Vector2f(speed * 0.6f, -speed * 0.6f), world);
				else
					move(new Vector2f(speed, -speed), world);
				world.spatialMap.update(character);
			}
		});
		KeyMapping.Up.subscribe(new ControlEvent()
		{
			@Override
			public void keyDown() 
			{
			}
			@Override
			public void keyUp()
			{
			}
			@Override
			public void keyIsDown() 
			{
				if (isMovingDiagonally)
					move(new Vector2f(-speed * 0.6f, -speed * 0.6f), world);
				else
					move(new Vector2f(-speed, -speed), world);
				world.spatialMap.update(character);
			}
		});
		KeyMapping.Down.subscribe(new ControlEvent()
		{
			@Override
			public void keyDown() 
			{
			}
			@Override
			public void keyUp()
			{
			}
			@Override
			public void keyIsDown() 
			{
				if (isMovingDiagonally)
					move(new Vector2f(speed * 0.6f, speed * 0.6f), world);
				else
					move(new Vector2f(speed, speed), world);
				world.spatialMap.update(character);
			}
		});
	}
	
	public Character(Vector2f position) throws SlickException
	{
		super(position, new Vector2f(0.8f, 0.8f), true, new Vector2f(-14, -64), new AnimatedSprite("res/sprites/CharacterStill.png", 64, 80, 8));
	}
}
