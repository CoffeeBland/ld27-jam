package ld27jam.states;

import ld27jam.res.Images;
import ld27jam.res.Sounds;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class WinState extends BasicGameState
{
	public static final int ID = 13;

	Thread loadingThread;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	Image onwardImg;
	String text = "Press enter to continue, escape to exit";
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		if (onwardImg == null)
			onwardImg = Images.get("res/sprites/Onward.png");
		onwardImg.startUse();
		onwardImg.drawEmbedded(gc.getWidth() / 2 - onwardImg.getWidth() / 2, gc.getHeight() / 2 - onwardImg.getHeight() / 2);
		onwardImg.endUse();

		MenuState.drawCentered(MenuState.uFontSmall, gc.getHeight() / 2 + onwardImg.getHeight() / 2 + 16, text, gc);
	}
	
	@Override
	public void update(final GameContainer gc, final StateBasedGame sbg, int delta) throws SlickException 
	{
		if (loadingThread != null && !loadingThread.isAlive())
		{
			if (gc.getInput().isKeyDown(Input.KEY_ENTER))
				sbg.enterState(GameState.ID, new FadeOutTransition(Color.white, 500), new FadeInTransition(Color.white, 500));
			else if (gc.getInput().isKeyDown(Input.KEY_ESCAPE))
				sbg.enterState(MenuState.ID, new FadeOutTransition(Color.white, 500), new FadeInTransition(Color.white, 500));
		}
		else if (loadingThread == null)
		{
			loadingThread = new Thread(new Runnable()
			{
				@Override
				public void run() 
				{
			        try
			        {
						sbg.getState(GameState.ID).init(gc, sbg);
					} 
			        catch (SlickException e) 
					{
						e.printStackTrace();
					}
				}
			});
			loadingThread.run();
		}
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		if (sbg.getState(GameState.ID) == null)
			sbg.addState(new GameState());
		loadingThread = null;
		
		try { Sounds.startMusic("res/audio/gameover.mid", true); } 
		catch (Exception e) { e.printStackTrace(); }
	}
	@Override
	public void leave(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		try { Sounds.stopMusic(); } 
		catch (Exception e) { e.printStackTrace(); }
	}
	
	@Override
	public int getID() 
	{
		return ID;
	}
}
