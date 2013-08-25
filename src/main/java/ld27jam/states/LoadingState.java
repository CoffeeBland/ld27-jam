package ld27jam.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class LoadingState extends BasicGameState
{
	public static final int ID = 12;

	public String string = "Loading level ... this may take a while";
	Thread loadingThread;
	int timer = 0;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		drawCentered(MenuState.uFontSmall, gc.getHeight() / 2 - MenuState.uFontSmall.getHeight(string) / 2, string, gc);
	}
	
	private void drawCentered(UnicodeFont font, int y, String text, GameContainer gc) 
	{
		font.drawString((gc.getWidth()/2)-(font.getWidth(text)/2), y, text);
	}
	
	@Override
	public void update(final GameContainer gc, final StateBasedGame sbg, int delta) throws SlickException 
	{
		if (loadingThread != null && !loadingThread.isAlive())
			sbg.enterState(GameState.ID, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
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
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		if (sbg.getState(GameState.ID) == null)
			sbg.addState(new GameState());
		timer = 0;
	}
	
	@Override
	public int getID() 
	{
		return ID;
	}

}
