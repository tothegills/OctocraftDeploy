package com.octopusdeploy.event;

public class RunTaskLaterEvent extends BaseEvent {

	private Runnable runnable;
	private long ticks;
	
	public RunTaskLaterEvent(Runnable runnable, long ticks) {
		this.runnable = runnable;
		this.ticks = ticks;
	}
	
	public Runnable getRunnable() {
		return runnable;
	}
	
	public long getTicks() {
		return ticks;
	}
}
