package ld27jam.states;

import ld27jam.GameDirector;
import ld27jam.World;
import ld27jam.entities.Ghoul;
import ld27jam.entities.TileType;
import ld27jam.input.InputController;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState {

	public static final int ID = 3;
	private StateBasedGame sbg;

	private World world;	
	private GameDirector gd;
	
	private boolean isReinit = false;
	private boolean paused = false;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		this.sbg = sbg;
		gd = new GameDirector();
		world = new World(gd);
		world.init(gc, sbg);
		
		if (!isReinit)
			InputController.init(gc); 
		else
			isReinit = true;
	}
	public void reinit()
	{
		world.character.sanity = world.character.maxSanity;
		world.character.invincibility = 0;
		world.setShake(10, 60);
		Vector2f startingPoint = null;
		for	(int x = 0; x < world.grid.length && startingPoint == null; x++)
		{
			for (int y = 0; y < world.grid[0].length && startingPoint == null; y++) 
			{
				if (world.grid[x][y] == TileType.StartingPoint)
					startingPoint = new Vector2f(x + 1, y + 1);
			}
		}
		world.character.init(world, sbg);
		world.character.setPosition(startingPoint);
		world.spatialMap.update(world.character);
		
		if (world.lastDeath != null)
			try 
			{
				world.add(new Ghoul(world.lastDeath));
			} 
			catch (SlickException e)
			{
				e.printStackTrace();
			}
	}

	@Override
	public void keyPressed(int key, char c)
	{
		if (key == Input.KEY_ESCAPE)
			paused = !paused;
	}
	double elapsedAvg = 0;
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.setColor(world.backColor.scaleCopy(0.6f));
		g.fillRect(0, 0, gc.getWidth(), gc.getWidth());
		
		world.render(gc, sbg, g);
		
		if (paused)
		{
			MenuState.drawCentered(MenuState.uFont, 200, "PAUSED", gc);
			MenuState.drawCentered(MenuState.uFontSmall, 290, "Press ESC to resume", gc);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		if (!paused)
		{
			InputController.update();
			world.update(gc, sbg, delta);
		}
	}

	public void exitGame()
	{
		sbg.enterState(GameOverState.ID, new FadeOutTransition(Color.white, 700), new FadeInTransition(Color.white));
	}

	public World getWorld()
	{
		return world;
	}
	
	public int getID() {
		return ID;
	}

}
