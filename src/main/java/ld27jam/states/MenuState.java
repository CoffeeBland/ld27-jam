package ld27jam.states;

import ld27jam.helpers.FontFactory;

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

public class MenuState extends BasicGameState {

	public static final int ID = 2;
	private GameContainer gc;
	private StateBasedGame sbg;
	public static UnicodeFont uFont, uFontSmall, uFontSmallGray;
	private boolean playSelected = true;
	
	//private Image gameLogo;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.gc = gc;
		this.sbg = sbg;
		//gameLogo = new Image("res/game_logo.png");
	}

	@Override
	public void keyReleased(int key, char c)
	{
		if (key == Input.KEY_ESCAPE)
			exitGame();
		if(key == Input.KEY_ENTER) 
		{
			if (playSelected)
			{
				sbg.enterState(LoadingState.ID, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500));
			}
			else
				exitGame();
		}
	}
	
	@Override
	public void keyPressed(int key, char c)
	{
		if(key == Input.KEY_W)
			playSelected = true;
		if(key == Input.KEY_S)
			playSelected = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		String title = "10 seconds to insanity";
		uFont.drawString((gc.getWidth()/2)-(uFont.getWidth(title)/2), 50, title);
		String credits = "A game by Guillaume, Frederic and Myriam";
		uFontSmall.drawString((gc.getWidth()/2)-(uFontSmall.getWidth(credits)/2), gc.getHeight()-28, credits);
		
		drawCentered(!playSelected ? uFontSmallGray : uFontSmall, 320, "Play", gc);
		drawCentered(playSelected ? uFontSmallGray : uFontSmall, 380, "Exit", gc);
	}
	
	private void drawCentered(UnicodeFont font, int y, String text, GameContainer gc) 
	{
		font.drawString((gc.getWidth()/2)-(font.getWidth(text)/2), y, text);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException 
	{
	}

	private void exitGame()
	{
		gc.exit();
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		uFont = FontFactory.get().getFont(48, java.awt.Color.WHITE);
		uFontSmall = FontFactory.get().getFont(18, java.awt.Color.WHITE);
		uFontSmallGray = FontFactory.get().getFont(18, java.awt.Color.GRAY);
	}

	@Override
	public int getID()
	{
		return ID;
	}

}
