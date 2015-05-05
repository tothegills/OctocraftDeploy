package com.octopusdeploy.event;

public class DeploymentProgressEvent extends BaseEvent {
	private int progress;
	
	public DeploymentProgressEvent(int progress) {
		this.progress = progress;
	}
	
	public int getProgress() {
		return progress;
	}
}
