package ld27jam;

import java.util.*; 

//import ld27jam.entities.Dungeon;

public class MainGeneratorTest{
	
	public static long oldseed = 0;
	
	public static int getRand(int deviance, int mean, long seed)
	{
		return (int) Math.abs( (new Random(seed)).nextGaussian() * mean + deviance ); 
	}
	
	public static void main(String[] args){
		int[] test = new int[200];
		for (int i = 0; i < 1000; i++) {
			Date now = new Date();
			long seed = now.getTime() + oldseed;
		
			int rnd = getRand(30, 10, seed);
			test[rnd]++;
			oldseed = seed;
		}
		for (int i = 0; i < test.length; i++) {
			System.out.println(i + " - " + test[i]);
		}
		/*//initial stuff used in making the map
		int x = 120; int y = 120; int dungeon_objects = 33;
 
		//convert a string to a int, if there's more then one arg
		if (args.length >= 1)
			dungeon_objects = Integer.parseInt(args[0]);
		if (args.length >= 2)
			x = Integer.parseInt(args[1]);
		if (args.length >= 3)
			y = Integer.parseInt(args[2]);
		Dungeon generator = new Dungeon();
 
		//then we create a new dungeon map
		if (generator.createDungeon(x, y, dungeon_objects)){
			//always good to be able to see the results..
			generator.showDungeon();
		}*/
	}
	
}
