package ld27jam;

import java.util.*; 

import ld27jam.entities.Dungeon;
import ld27jam.entities.RoomTemplate;

//import ld27jam.entities.Dungeon;

public class MainGeneratorTest{
	
	public static long oldseed = 0;
	
	public static int getRand(int deviance, int mean, long seed)
	{
		return (int) Math.abs( (new Random(seed)).nextGaussian() * mean + deviance ); 
	}
	
	public static void main(String[] args){
		//initial stuff used in making the map
		int x = 120; int y = 120;
 
		//convert a string to a int, if there's more then one arg
		Dungeon generator = new Dungeon();
 
		RoomTemplate[] templates = new RoomTemplate[7];
		templates[0] = new RoomTemplate("W+WWWWWW\n" +
										"Wffffff+\n" +
										"WffffffW\n" +
										"WWWWfffW\n" +
										"   WfffW\n" +
										"   +fffW\n" +
										"   WW+WW");
		templates[1] = new RoomTemplate("WWW+WWW\n" +
										"Wfffff+\n" +
										"WfffffW\n" +
										"+fffffW\n" +
										"WWW+WWW");
		templates[2] = new RoomTemplate("WWW+W    \n" +
										"WfffW    \n" +
										"WfffW    \n" +
										"+fffW    \n" +
										"WfffWWWWW\n" +
										"Wfffffff+\n" +
										"WfffffffW\n" +
										"W+WWWWWWW");
		templates[3] = new RoomTemplate("WWW+WWWWWWW\n" +
										"WfffffffffW\n" +
										"Wfffffffff+\n" +
										"WfffffffffW\n" +
										"WfffffffffW\n" +
										"+fffffffffW\n" +
										"WfffffffffW\n" +
										"WWWWW+WWWWW");
				
		templates[4] = new RoomTemplate("W+WW       WW+W\n" +
										"WffWW     WWffW\n" +
										"+fffWWW WWWfff+\n" +
										"WfffffWWWfffffW\n" +
										"WWWffffWffffWWW\n" +
										"  WWfffffffWW  \n" +
										"WWWffffWffffWWW\n" +
										"WfffffWWWfffffW\n" +
										"+fffWWW WWWfff+\n" +
										"WffWW     WWffW\n" +
										"W+WW       WW+W");
		templates[5] = new RoomTemplate("WWW+WWW\n" +
										"WfffffW\n" +
										"WfffffW\n" +
										"+fffffW\n" +
										"WfffffW\n" +
										"WfffffW\n" +
										"WfffffW\n" +
										"Wfffff+\n" +
										"WfffffW\n" +
										"WfffffW\n" +
										"WW+WWWW");
		templates[6] = new RoomTemplate("WWW+WWWWWWWW\n" +
										"WffffffffffW\n" +
										"Wffffffffff+\n" +
										"WffffffWWWWW\n" +
										"WffffffW    \n" +
										"WffffffW    \n" +
										"WfffWWWW    \n" +
										"+fffW       \n" +
										"WfffW       \n" +
										"WW+WW");	
		
		//then we create a new dungeon map
		generator.createDungeon(x, y, templates);	
		generator.showDungeon();
	}
	
}
