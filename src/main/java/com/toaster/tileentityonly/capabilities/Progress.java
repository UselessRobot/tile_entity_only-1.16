package com.toaster.tileentityonly.capabilities;

public class Progress implements IProgress {
	protected int maxProgress;
	protected int currentProgress;
	
	public Progress() {
		this(100, 0);
	}
	
	public Progress(int max) {
		this(max, 0);
	}
	
	public Progress(int max, int current) {
		this.maxProgress = max;
		this.currentProgress = current;
	}
	
	@Override
	public int progress(boolean simulate) {
		return setProgress(currentProgress + 1, simulate);
	}
	
	@Override
	public int progress(int maxAmount, boolean simulate) {
		return setProgress(currentProgress + maxAmount, simulate);
	}
	
	@Override
	public int setProgress(int maxProgress, boolean simulate) {
		if(isFinished()) {
			return resetProgress(simulate);
		}
		
		int newCurrent = Math.min(maxProgress, this.maxProgress);
		if(!simulate) {
			currentProgress = newCurrent;
		}
		return newCurrent; 
	}
	
	@Override
	public int resetProgress(boolean simulate) {
		if(!simulate) {
			currentProgress = 0;
		}
		return 0;
	}
	
	@Override
	public boolean isFinished() {
		return currentProgress >= maxProgress;
	}
	
	protected void onProgressChanged() {
		
	}
	
	@Override
	public int getProgress() {
		return currentProgress;
	}
	
	@Override
	public int getMaxProgress() {
		return maxProgress;
	}
}