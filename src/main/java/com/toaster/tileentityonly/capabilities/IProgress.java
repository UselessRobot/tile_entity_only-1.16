package com.toaster.tileentityonly.capabilities;

public interface IProgress {
	
	/**
	 * Increments progress by 1. Returns the new progress, which cannot be greater than the 
	 * maximum progress.
	 * 
	 * @param simulate
	 * 			If true, will not increment current progress, is simulated instead.
	 * @return New value of current progress.
	 */
	int progress(boolean simulate);

	/**
	 * Increases progress by the given maxAmount. Returns the new progress, which cannot be 
	 * greater than the maximum progress.
	 * 
	 * @param maxAmount
	 * 			The most progress will be increased by.
	 * @param simulate
	 * 			If true, will not increment current progress, is simulated instead.
	 * @return New value of current progress.
	 */
	int progress(int maxAmount, boolean simulate);

	/**
	 * Sets the progress to the given maxAmount, will not be greater than the maximum amount.
	 * Returns the new progress.
	 * 
	 * @param maxProgress
	 * 			The most progress it will be set to.
	 * @param simulate
	 * 			If true, will not set current progress, is simulated instead.
	 * @return new value of current progress.
	 */
	int setProgress(int maxProgress, boolean simulate);

	/**
	 * Sets the progress to 0, returns the new progress.
	 * 
	 * @param simulate
	 * 			If true, set current progress, is simulated instead.
	 * @return Will always return 0, the new progress.
	 */
	int resetProgress(boolean simulate);

	/**
	 * Returns a boolean for if progress is equal to or greater than maximum progress.
	 */
	boolean isFinished();
	
	/**
	 * Returns the current progress.
	 */
	int getProgress();

	/**
	 * returns the maximum progress.
	 */
	int getMaxProgress();
}