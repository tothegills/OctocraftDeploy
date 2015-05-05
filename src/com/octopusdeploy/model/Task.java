package com.octopusdeploy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task extends Resource {

	private boolean isCompleted;
	
	private String duration;
	
	private boolean finishedSuccessfully;
	
	@JsonProperty("IsCompleted")
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
	public boolean isCompleted() {
		return isCompleted;
	}
	
	@JsonProperty("Duration")
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getDuration() {
		return duration;
	}
	
	@JsonProperty("FinishedSuccessfully")
	public void setFinishedSuccessfully(boolean finishedSuccessfully) {
		this.finishedSuccessfully = finishedSuccessfully;
	}
	
	public boolean hasFinishedSuccessfully() {
		return finishedSuccessfully;
	}
}
