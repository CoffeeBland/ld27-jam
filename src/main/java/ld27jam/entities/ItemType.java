package ld27jam.entities;

public enum ItemType 
{
	Key(0, 0);
	
	public int imgIdX, imgIdY;
	private ItemType(int imgIdX, int imgIdY)
	{
		this.imgIdX = imgIdX;
		this.imgIdY = imgIdY;
	}
}
