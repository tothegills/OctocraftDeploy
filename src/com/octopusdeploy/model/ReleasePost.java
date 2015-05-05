package com.octopusdeploy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReleasePost {
	private String projectId;
	private String version;
	
	public ReleasePost(String projectId, String version) {
		this.projectId = projectId;
		this.version = version;
	}
	
	@JsonProperty("ProjectId")
	public String getProjectId() {
		return projectId;
	}
	
	@JsonProperty("Version")
	public String getVersion() {
		return version;
	}
}
