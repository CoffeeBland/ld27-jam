package ld27jam;

import ld27jam.entities.Dungeon;
import ld27jam.entities.RoomTemplate;
import ld27jam.entities.TileType;

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
		RoomTemplate[] templates = new RoomTemplate[9];
		templates[0] = new RoomTemplate(" +      \n" +
										" ffffff+\n" +
										" ffffff \n" +
										"    fff \n" +
										"    fff \n" +
										"   +fff \n" +
										"     +  ");
		templates[1] = new RoomTemplate("   +   \n" +
										" fffff+\n" +
										" fffff \n" +
										"+fffff \n" +
										"   +   ");
		templates[2] = new RoomTemplate("   +     \n" +
										" fff     \n" +
										" fff     \n" +
										"+fff     \n" +
										" fff     \n" +
										" fffffff+\n" +
										" fffffff \n" +
										" +       ");
		templates[3] = new RoomTemplate("   +       \n" +
										" fffffffff \n" +
										" fffffffff+\n" +
										" fffffffff \n" +
										" fffffffff \n" +
										"+fffffffff \n" +
										" fffffffff \n" +
										"     +     ");
				
		templates[4] = new RoomTemplate(" +           + \n" +
										" ff         ff \n" +
										"+fff       fff+\n" +
										" fffff   fffff \n" +
										"   fffffffff   \n" +
										"   ffffffff   \n" +
										"   fffffffff   \n" +
										" fffff   fffff \n" +
										"+fff       fff+\n" +
										" ff         ff \n" +
										" +           + ");
		templates[5] = new RoomTemplate("   +   \n" +
										" fffff \n" +
										" fffff \n" +
										"+fffff \n" +
										" fffff \n" +
										" fffff \n" +
										" fffff \n" +
										" fffff+\n" +
										" fffff \n" +
										" fffff \n" +
										"  +    ");
		templates[6] = new RoomTemplate("   +        \n" +
										" ffffffffff \n" +
										" ffffffffff+\n" +
										" ffffff     \n" +
										" ffffff     \n" +
										" ffffff     \n" +
										" fff        \n" +
										"+fff        \n" +
										" fff        \n" +
										"  +  ");
		templates[7] = new RoomTemplate(" +        + \n" +
										"fff      fff\n" +
										" fff    fff \n" +
										" fff    fff \n" +
										" fff    fff \n" +
										" fff    fff \n" +
										"  fff  fff  \n" +
										"   ffffff   \n" +
										"   +    +   ");
		templates[7] = new RoomTemplate("      +      \n" +
										"     fff    \n" +
										"    fffff   \n" +
										"   fffffff  \n" +
										" +fffffffff+\n" +
										"   fffffff  \n" +
										"    fffff   \n" +
										"     fff    \n" +
										"      +     ");
		templates[8] = new RoomTemplate("      +      \n" +
										"     fff    \n" +
										"     fff    \n" +
										"     fff    \n" +
										"     fff    \n" +
										"     fff    \n" +
										"  fffffffff \n" +
										" fffffffffff\n" +
										"  f+ff ff+f ");
		
		this.dungeon = new Dungeon();
		this.dungeon.createDungeon(100, 100, templates);
	}
	
	public int getHeight() 
	{
		return dungeon.ysize;
	}
	
	public int getWidth() 
	{
		return dungeon.xsize;
	}
	
	public TileType getCell(int x, int y)
	{
		return dungeon.getTileType(x, y);
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
