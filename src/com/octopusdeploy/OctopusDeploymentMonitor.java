package com.octopusdeploy;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octopusdeploy.event.DeploymentFailedEvent;
import com.octopusdeploy.event.DeploymentFinishedEvent;
import com.octopusdeploy.event.DeploymentProgressEvent;
import com.octopusdeploy.model.ActivityLog;
import com.octopusdeploy.model.Deployment;
import com.octopusdeploy.model.LogElement;
import com.octopusdeploy.model.Task;
import com.octopusdeploy.model.TaskDetails;

public class OctopusDeploymentMonitor implements Runnable {

	private ObjectMapper objectMapper = new ObjectMapper();
	private ParameterReader parameterReader = new ParameterReader();
	private WebClient webClient;
	private Deployment deployment;
	private Set<String> logIdPrinted = new HashSet<String>();
	
	public OctopusDeploymentMonitor(Deployment deployment) {
		this.deployment = deployment;
		String serverUrl = parameterReader.getServerUrl();
		String apiKey = parameterReader.getApiKey();
		
		webClient = new WebClient(serverUrl, apiKey);
		
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);		
	}
	
	@Override
	public void run() {
		try {
			TaskDetails taskDetails = getTaskDetails(deployment.getTaskId());

			updateLogs(taskDetails.getActivityLog());
			
			Task task = taskDetails.getTask();
			if (task.isCompleted()) {
				if (task.hasFinishedSuccessfully())
					new DeploymentFinishedEvent().callEvent();
				else
					new DeploymentFailedEvent().callEvent();
				return;
			}
			
			updateProgress(taskDetails);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	private void updateProgress(TaskDetails taskDetails) {
		String durationString = taskDetails.getTask().getDuration();
		if (durationString == null)
			return;
		
		String remainingString = taskDetails.getProgress().getEstimatedTimeRemaining();
		if (remainingString == null)
			return;
		
		int duration = parseNumber(durationString);
		int remaining = parseNumber(remainingString);
		
		int totalTime = duration + remaining;
		if (totalTime == 0)
			return;
		
		int progress = 100 * duration / totalTime;			
		new DeploymentProgressEvent(progress).callEvent();
		
	}
	
	private void updateLogs(ActivityLog activityLog) {
		if (!logIdPrinted.contains(activityLog.getId())) {
			for (LogElement logElement : activityLog.getLogElements()) {
				String message = logElement.getMessageText();
				if (message == null)
					continue;
				printLog(message);
				logIdPrinted.add(activityLog.getId());
			}
		}
		UpdateLogs(activityLog.getChildren());
		
	}
	
	private void UpdateLogs(ActivityLog[] activityLogs) {
		for (ActivityLog activityLog : activityLogs)
			updateLogs(activityLog);
	}
	
	private TaskDetails getTaskDetails(String taskId) throws ClientProtocolException, IOException {
		String path = "tasks/" + taskId + "/details";
		
		HttpResponse response = webClient.Get(path);

		return objectMapper.readValue(response.getEntity().getContent(), TaskDetails.class);
	}
	
	private int parseNumber(String string) {
		String[] splits = string.split(" ");
		for (String split : splits) {
			try {
				int parsed = Integer.parseInt(split);
				return parsed;
			}
			catch (Exception e) {				
			}			
		}
		return 0;
	}
	
	private void printLog(String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(message);
		}
	}
}
