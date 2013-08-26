package ld27jam.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class LoadingState extends BasicGameState
{
	public static final int ID = 12;

	public String string = "Generating level ... this may take a while";
	Thread loadingThread;
	
	private boolean showingIntro = false;
	private int showingIntroStep = 0;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		if (showingIntro)
		{
			String message = "";
			if (showingIntroStep == 1) 
				message = "Welcome dear player, this game is called\n"+
						  "10 seconds to insanity\n"+
						  "\n"+
						  "The goal is to roam the dungeon. You woke up in\n"+
						  "in search of the diamond room. Once reached you will go onward\n"+
						  "to the next level!\n\nTowards the surface!";
			else if (showingIntroStep == 2)
				message = "But beware!\n\n"+
						  "There will be traps, and locked door,\n"+
						  "and even monsters!\n\n"+
						  "To move use             : W A S D\n"+
						  "To paude the game press : ESC\n"+
						  "To loot a chest         : Go near it\n"+
						  "To unlock a door        : Hit it!\n" +
						  "To toggle fullscreen    : Left alt + enter";
			
			MenuState.uFontSmall.drawString(100, 100, message);
			drawCentered(MenuState.uFontSmall, gc.getHeight()-28, "Press enter to continue ...", gc);
		}
		else
		{
			drawCentered(MenuState.uFontSmall, gc.getHeight() / 2 - MenuState.uFontSmall.getHeight(string) / 2, string, gc);
		}
	}
	
	private void drawCentered(UnicodeFont font, int y, String text, GameContainer gc) 
	{
		font.drawString((gc.getWidth()/2)-(font.getWidth(text)/2), y, text);
	}
	
	@Override
	public void update(final GameContainer gc, final StateBasedGame sbg, int delta) throws SlickException 
	{
		if (loadingThread != null && !loadingThread.isAlive())
		{
			if (showingIntro == false)
				showingIntro = true;
			if (showingIntroStep == 3)
			{
				sbg.enterState(GameState.ID, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
			}
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
		Input input = gc.getInput();
		if (input.isKeyPressed(Input.KEY_ENTER))
		{
			showingIntroStep++;
		}
	}
	
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		if (sbg.getState(GameState.ID) == null)
			sbg.addState(new GameState());
		showingIntro = false;
		showingIntroStep = 0;
		loadingThread = null;
	}
	
	@Override
	public int getID() 
	{
		return ID;
	}

}
