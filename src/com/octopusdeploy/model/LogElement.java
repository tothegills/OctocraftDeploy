package com.octopusdeploy.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogElement {
	private String messageText;
	
	@JsonProperty("MessageText")
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	
	public String getMessageText() {
		return messageText;
	}
}
