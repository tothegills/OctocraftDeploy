package com.octopusdeploy;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.octopusdeploy.event.DeploymentFailedEvent;
import com.octopusdeploy.event.DeploymentFinishedEvent;
import com.octopusdeploy.event.DeploymentStartedEvent;
import com.octopusdeploy.model.*;

public class OctopusClient implements Listener {

	private static final String PROJECTS_PATH = "projects";
	private static final String ENVIRONMENTS_PATH = "environments";
	private static final String RELEASES_PATH = "releases";
	private static final String DEPLOYMENTS_PATH = "deployments";
	
	private Plugin plugin;
	private boolean deploying = false;	
	private ObjectMapper objectMapper = new ObjectMapper();
	private ParameterReader parameterReader = new ParameterReader();
	private WebClient webClient;
	private int monitorTaskId;
	
	public OctopusClient(Plugin plugin) {
		this.plugin = plugin;
		String serverUrl = parameterReader.getServerUrl();
		String apiKey = parameterReader.getApiKey();
		
		webClient = new WebClient(serverUrl, apiKey);
		
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@EventHandler
	private void startDeployment(DeploymentStartedEvent event) {
		if (deploying)
			return;
		
		try {
			String projectName = parameterReader.getProjectName();
			Project project = getResource(projectName, PROJECTS_PATH, Project.class);
					
			Release release = createRelease(project);
			
			String environmentName = parameterReader.getEnvironmentName();
			Environment environment = getResource(environmentName, ENVIRONMENTS_PATH, Environment.class);
			
			Deployment deployment = createDeployment(release, environment);
			
			OctopusDeploymentMonitor deploymentMonitor = new OctopusDeploymentMonitor(deployment);
			monitorTaskId = Bukkit.getScheduler().runTaskTimer(plugin, deploymentMonitor, 10, 10).getTaskId();
			
			deploying = true;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			new DeploymentFinishedEvent().callEvent();
		}		
	}
	
	@EventHandler
	private void endDeployment(DeploymentFinishedEvent event) {
		stopDeployment();
	}

	@EventHandler
	private void endDeployment(DeploymentFailedEvent event) {
		stopDeployment();
	}
	
	private void stopDeployment() {
		deploying = false;
		Bukkit.getScheduler().cancelTask(monitorTaskId);		
	}
		
	private <T extends Resource> T getResource(String name, String path, Class<T> resourceType) throws Exception {
		HttpResponse response = webClient.Get(path);
		List<T> resources;

		JsonNode root = objectMapper.readTree(response.getEntity().getContent());
		
		JsonParser parser = root.get("Items").traverse();
		
		JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, resourceType);
		
		resources = objectMapper.readValue(parser, type);		
		
		for (T resource : resources) {
			if (resource.getName().equalsIgnoreCase(name))
				return resource;
		}
		
		throw new Exception("Resource not found");		
	}	
	
	private Release createRelease(Project project) throws ClientProtocolException, IOException {				
		ReleasePost releasePost = new ReleasePost(project.getId(), "1.i");
		String content = new String(); 
		String json = objectMapper.writeValueAsString(releasePost);
		HttpResponse response = webClient.Post(RELEASES_PATH, json);
		
		content = EntityUtils.toString(response.getEntity());			
		return objectMapper.readValue(content, Release.class);	
	}
	
	private Deployment createDeployment(Release release, Environment environment) throws ClientProtocolException, IOException {
		DeploymentPost deploymentPost = new DeploymentPost(release.getId(), environment.getId());
		String content = new String(); 
		
		String json = objectMapper.writeValueAsString(deploymentPost);
		HttpResponse response = webClient.Post(DEPLOYMENTS_PATH, json);
		
		content = EntityUtils.toString(response.getEntity());		
		return objectMapper.readValue(content, Deployment.class);
	}
}
