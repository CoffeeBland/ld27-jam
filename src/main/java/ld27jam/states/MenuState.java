package ld27jam.states;

import java.io.File;

import ld27jam.GameStateController;
import ld27jam.helpers.FontFactory;
import ld27jam.helpers.KeyListenerImpl;
import ld27jam.helpers.MouseListenerImpl;
import ld27jam.helpers.SavingHelper;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MenuState extends BasicGameState {

	public static final int ID = 2;
	private GameContainer gc;
	private StateBasedGame sbg;
	UnicodeFont uFont = FontFactory.get().getFont(32, java.awt.Color.BLACK);
	UnicodeFont uFontSmall = FontFactory.get().getFont(18, java.awt.Color.WHITE);

	private Image gameLogo;

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		this.gc = gc;
		this.sbg = sbg;

		//gameLogo = new Image("res/game_logo.png");

		final StateBasedGame tSbg = sbg;

		Input tI = gc.getInput();

	}

	@Override
	public void keyReleased(int key, char c) 
	{
		if (key == Input.KEY_ESCAPE)
			exitGame();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int d) throws SlickException 
	{
	}

	private void exitGame()
	{
		gc.exit();
	}

	private GameContainer getGameContainer()
	{
		return gc;
	}

	private StateBasedGame getStateBasedGame()
	{
		return sbg;
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
	}

	@Override
	public int getID()
	{
		return ID;
	}

}
