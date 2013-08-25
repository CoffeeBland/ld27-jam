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
 
		RoomTemplate[] templates = new RoomTemplate[8];
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
				"   fffffffff   \n" +
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
		
		//then we create a new dungeon map
		generator.createDungeon(x, y, templates);	
		generator.showDungeon();
	}
	
}
