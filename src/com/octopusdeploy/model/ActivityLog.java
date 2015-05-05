package com.octopusdeploy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActivityLog extends Resource {
	
	private ActivityLog[] children;
	private LogElement[] logElements;
	
	@JsonProperty("Children")
	public void setChildren(ActivityLog[] children) {
		this.children = children;
	}
	
	public ActivityLog[] getChildren() {
		return children;
	}
	
	@JsonProperty("LogElements")
	public void setLogElements(LogElement[] logElements) {
		this.logElements = logElements;
	}
	
	public LogElement[] getLogElements() {
		return logElements;
	}
}
