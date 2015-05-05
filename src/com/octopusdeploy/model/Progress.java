package com.octopusdeploy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Progress {
	private int progressPercentage;
	private String estimatedTimeRemaining;
	
	@JsonProperty("ProgressPercentage")
	public void setProgressPercentage(int progressPercentage) {
		this.progressPercentage = progressPercentage;
	}
	
	public int getProgressPercentage() {
		return progressPercentage;
	}
	
	@JsonProperty("EstimatedTimeRemaining")
	public void setEstimatedTimeRemaining(String estimatedTimeRemaining) {
		this.estimatedTimeRemaining = estimatedTimeRemaining;
	}
	
	public String getEstimatedTimeRemaining() {
		return estimatedTimeRemaining;
	}	
}
