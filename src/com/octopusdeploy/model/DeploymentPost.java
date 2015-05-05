package com.octopusdeploy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeploymentPost {
	private String releaseId;
	private String environmentId;
	
	public DeploymentPost(String releaseId, String environmentId) {
		this.releaseId = releaseId;
		this.environmentId = environmentId;		
	}
		
	@JsonProperty("ReleaseId")
	public String getReleaseId() {
		return releaseId;
	}
	
	@JsonProperty("EnvironmentId")
	public String getEnvironmentId() {
		return environmentId;
	}
}
