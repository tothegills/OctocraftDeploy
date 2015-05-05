package com.octopusdeploy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Resource {
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	
	@JsonProperty("Id")
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}
}
