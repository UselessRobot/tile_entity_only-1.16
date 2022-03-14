package com.toaster.tileentityonly.capabilities;

public interface IHeat {

	public int addTemp(int amount, boolean simulate);
	
	public int addHeat(int amount, boolean simulate);
	
	public int getAllHeat();
	
	public int getTempAsHeat();
	
	public int getToNextDegree();
	
	public int getTemp();
	
	public int getHeatCapacity();
}
