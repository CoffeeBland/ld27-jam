package ld27jam;

public class GameDirector 
{
	public String spriteSheetLocation = "res/sprites/test.png";
	public String wallSheetLocation = "res/sprites/WallSheet.png";
	public GameLevel level;
	
	
	
	public GameDirector() 
	{
		this.level = new GameLevel(1);
	}
}
