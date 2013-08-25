package ld27jam.states;

import ld27jam.GameDirector;
import ld27jam.World;
import ld27jam.input.InputController;
import ld27jam.res.Sounds;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class GameState extends BasicGameState {

	public static final int ID = 3;
	private StateBasedGame sbg;

	private World world;	
	private GameDirector gd;
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		this.sbg = sbg;

		gd = new GameDirector();
		world = new World(gd);
		world.init(gc, sbg);
		InputController.init(gc); 
	}

	@Override
	public void keyReleased(int key, char c)
	{
		if (key == Input.KEY_ESCAPE) {
			try
			{
				Sounds.startMusic("res/audio/ambient.mid", true);
			}
			catch(Exception ex){}
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.setColor(new Color(10, 10, 20));
		g.fillRect(0, 0, gc.getWidth(), gc.getWidth());
		world.render(gc, sbg, g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		InputController.update();
		
		world.update(gc, sbg, delta);
	}

	public void exitGame()
	{
		sbg.enterState(GameOverState.ID, new FadeOutTransition(Color.white, 700), new FadeInTransition(Color.white));
	}

	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		init(gc, sbg);
	}

	public World getWorld()
	{
		return world;
	}
	
	public int getID() {
		return ID;
	}

}
