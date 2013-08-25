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
	public static final int ID = 50;
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	
	public String string = "Lording ... this may take a while";
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
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		timer++;
		if (timer == 500)
		{
			sbg.getState(GameState.ID).init(gc, sbg);
			sbg.enterState(GameState.ID, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
		}
	}
	
	int timer = 0;
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
