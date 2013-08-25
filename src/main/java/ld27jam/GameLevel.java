package ld27jam;

import ld27jam.entities.Dungeon;

public class GameLevel 
{
	public int difficulty;
	public int timeStage;
	public int timeLeftToStage;
	public int[] timeStageDurations = {1, 2, 4, 6, 8, 11, 14, 17, 20, 30};
	public Dungeon dungeon;
	
	public GameLevel(int level)
	{
		this.difficulty = level;
		changeTimeStage(1);
		
		// Good now generate that maze
		this.dungeon = new Dungeon();
		this.dungeon.createDungeon(300, 300, 100);
	}
	
	public int getHeight() 
	{
		return dungeon.ysize;
	}
	
	public int getWidth() 
	{
		return dungeon.xsize;
	}
	
	public int getCell(int x, int y)
	{
		return dungeon.getCell(x, y);
	}
	
	public void changeTimeStage(int newTimeStage) 
	{
		this.timeStage = newTimeStage;
		resetTimeLeft();
	}
	
	public void resetTimeLeft() 
	{
		// in ms
		this.timeLeftToStage = this.timeStageDurations[this.timeStage-1] * 1000;
	}
}
