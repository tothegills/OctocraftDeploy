package com.octopusdeploy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.octopusdeploy.effects.FireworkShow;
import com.octopusdeploy.effects.LightShow;
import com.octopusdeploy.effects.ProgressBar;
import com.octopusdeploy.event.DeploymentFailedEvent;
import com.octopusdeploy.event.DeploymentFinishedEvent;
import com.octopusdeploy.event.ResetDeploymentEvent;
import com.octopusdeploy.event.RunTaskLaterEvent;

public class Plugin extends JavaPlugin implements Listener {
	
	private static final int TICKS_TO_RESET = 200;
	
	@Override
	public void onEnable() {
		registerEvents(this);
				
		registerEvents(new ProgressBar());
				
		registerEvents(new DeployButton());
				
		registerEvents(new LightShow());
		
		registerEvents(new FireworkShow(this));
		
		registerEvents(new OctopusClient(this));
		
		new ResetDeploymentEvent().callEvent();
	}
		
	private void registerEvents(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}

	@EventHandler
	private void runTaskLater(RunTaskLaterEvent event) {
		Bukkit.getScheduler().runTaskLater(this, event.getRunnable(), event.getTicks());
	}
	
	@EventHandler
	private void resetDeployment(DeploymentFinishedEvent event) {
		resetDeployment();
	}

	@EventHandler
	private void resetDeployment(DeploymentFailedEvent event) {
		resetDeployment();
	}
	
	private void resetDeployment() {
		new RunTaskLaterEvent(new Runnable() {

			@Override
			public void run() {
				System.out.println("Resetting deployment");
				new ResetDeploymentEvent().callEvent();				
			}
			
		}, TICKS_TO_RESET).callEvent();
	}
	
	@EventHandler
	public void spawnPlayer(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		Location spawnLocation = new Location(player.getWorld(), -1, 5, 0);
		player.teleport(spawnLocation);
	}	
}
