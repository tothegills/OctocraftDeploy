package com.octopusdeploy.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

import com.octopusdeploy.Plugin;
import com.octopusdeploy.event.DeploymentFinishedEvent;
import com.octopusdeploy.event.ResetDeploymentEvent;

public class FireworkShow implements Listener {
	
	private Plugin plugin;
	private World world;
	private int X = 44;
	private int Y = 4;
	
	private int fireWorkTaskId;

	public FireworkShow(Plugin plugin) {
		world = Bukkit.getWorlds().get(0);
		this.plugin = plugin;
	}
	
	@EventHandler
	private void launchFireworks(DeploymentFinishedEvent event) {
		LaunchFireworks launchFireworks = new LaunchFireworks();
		fireWorkTaskId = Bukkit.getScheduler().runTaskTimer(plugin, launchFireworks, 0, 20).getTaskId();
	}
	
	@EventHandler
	private void finishFireworks(ResetDeploymentEvent event) {
		Bukkit.getScheduler().cancelTask(fireWorkTaskId);
	}
	
	private void launchFirework(Location location)
	{
		Entity entity = location.getWorld().spawnEntity(location, EntityType.FIREWORK);
		Firework firework = (Firework) entity;
		FireworkMeta fireworkMeta = firework.getFireworkMeta();
		
		FireworkGenerator fireworkGenerator = new FireworkGenerator(1);
		fireworkGenerator.setMeta(fireworkMeta, FireworkGenerator.COLORS);
		
		firework.setFireworkMeta(fireworkMeta);				
	}
	
	private class LaunchFireworks implements Runnable {

		@Override
		public void run() {
			for (int z = -16; z <= 16; z+= 4) {
				Location location = new Location(world, X, Y, z);
				launchFirework(location);
			}
		}
	}
}
