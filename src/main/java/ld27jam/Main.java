package ld27jam;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {

	private static boolean fullscreen;
	private static AppGameContainer app;
	private static GameStateController gsc;

	public static void main(String[] args) {
		System.out.println("Starting v0.1 of ld27Jam by Paul (kiasaki and dagothig)");
		fullscreen = false;
		try {
			gsc = new GameStateController("LD27 10 seconds to insanity - Kiasaki - Dagothig");
			app = new AppGameContainer(gsc);
			if (!fullscreen){
				app.setDisplayMode(800,600, false);
			}else{
				app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
			}
			app.setForceExit(true);
	        app.setShowFPS(false);
	        app.setTargetFrameRate(60);
			app.setIcons(new String[]{"res/icons/icon16.png", "res/icons/icon32.png"});
	        app.start();
	    } catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public static void toggleFullscreen(){
		try {
			if (fullscreen){
				app.setDisplayMode(800,600, false);
				fullscreen = false;
			}else{
				app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
				fullscreen = true;
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
