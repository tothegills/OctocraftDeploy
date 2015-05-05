package com.octopusdeploy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Deployment extends Resource {

	private String taskId;
	
	@JsonProperty("TaskId")
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	public String getTaskId() {
		return taskId;
	}
}
