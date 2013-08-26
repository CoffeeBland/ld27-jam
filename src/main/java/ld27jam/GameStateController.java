package ld27jam;

import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.easyogg.OggClip;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ld27jam.states.*;

public class GameStateController extends StateBasedGame {

	public GameStateController(String name) {
		super(name);
	}

	GameContainer gc;
	@Override
    public void initStatesList(GameContainer gc) throws SlickException {
    	addState(new LogoState());
    	addState(new MenuState());
    	addState(new LoadingState());
    	addState(new GameOverState());
    	addState(new WinState());
        enterState(LogoState.ID);
        this.gc = gc;
        
		try
		{
	        new OggClip(new FileInputStream("res/audio/horrorambient.ogg")).loop();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    }
	
	@Override
	public void keyPressed(int key, char c)
	{
		if(key == Input.KEY_ENTER && gc.getInput().isKeyDown(Input.KEY_LALT))
			Main.toggleFullscreen();
		
		super.keyPressed(key, c);
	}
}
