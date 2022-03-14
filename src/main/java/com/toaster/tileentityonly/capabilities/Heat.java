package com.toaster.tileentityonly.capabilities;

public class Heat implements IHeat{
	protected int temperature;
	protected final int heatCapacity;
	protected int toNextDegree;
	protected final int maxTemp;
	
	public Heat() {
		this(22, 5, 5, Integer.MAX_VALUE);
	}
	
	public Heat(int temperature) {
		this(temperature, 5, 5, Integer.MAX_VALUE);
	}
	
	public Heat(int temperature, int heatCapacity) {
		this(temperature, heatCapacity, heatCapacity, Integer.MAX_VALUE);
	}
	
	public Heat(int temperature, int heatCapacity, int toNextDegree, int max) {
		this.temperature = temperature;
		this.heatCapacity = heatCapacity;
		this.toNextDegree = toNextDegree;
		this.maxTemp = max;
	}
	
	public int addTemp(int amount, boolean simulate) {
		int newTemp = Math.min(temperature + amount, maxTemp);
		if(!simulate) {
			temperature = newTemp;
		}
		return newTemp;
	}
	
	public int addHeat(int amount, boolean simulate) {
		int newTemp = addTemp(amount / heatCapacity, simulate);
		int newNextDegree = toNextDegree + (amount % heatCapacity);
		
		if(newNextDegree >= heatCapacity) {
			newNextDegree -= heatCapacity;
			
			newTemp = Math.min(newTemp + 1, maxTemp);
		}
		
		if(!simulate) {
			toNextDegree = newNextDegree;
			temperature = newTemp;
		}
		
		return (newTemp * heatCapacity) + newNextDegree;
	}
	
	protected void onHeatChanged() {
		
	}
	
	public int getAllHeat() {
		return (temperature / heatCapacity) + toNextDegree;
	}
	
	public int getTempAsHeat() {
		return temperature / heatCapacity;
	}
	
	public int getToNextDegree() {
		return toNextDegree;
	}
	
	public int getTemp() {
		return temperature;
	}
	
	public int getHeatCapacity() {
		return heatCapacity;
	}
}
