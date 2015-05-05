package com.octopusdeploy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskDetails {
	private Task task;
	private Progress progress;
	private ActivityLog activityLog;
	
	@JsonProperty("Task")
	public void setTask(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}
	
	@JsonProperty("Progress")
	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	
	public Progress getProgress() {
		return progress;
	}
	
	@JsonProperty("ActivityLog")
	public void setActivityLog(ActivityLog activityLog) {
		this.activityLog = activityLog;
	}
	
	public ActivityLog getActivityLog() {
		return activityLog;
	}
}
