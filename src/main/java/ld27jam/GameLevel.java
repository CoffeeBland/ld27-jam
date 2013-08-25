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
		RoomTemplate[] templates = new RoomTemplate[7];
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
										"   ffff ffff   \n" +
										"    fffffff    \n" +
										"   ffff ffff   \n" +
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
		
		this.dungeon = new Dungeon();
		this.dungeon.createDungeon(300, 300, templates);
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
