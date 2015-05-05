package com.octopusdeploy.effects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.octopusdeploy.event.DeploymentFinishedEvent;
import com.octopusdeploy.event.ResetDeploymentEvent;

public class LightShow implements Listener {
	private World world;
	
	private int X = 44;
	private int Y = 4;
	
	public LightShow() {
		this.world = Bukkit.getWorlds().get(0);
	}

	@EventHandler
	private void startTheShow(DeploymentFinishedEvent event) {
		draw(Material.BEACON);
	}
	
	@EventHandler
	private void stopTheShow(ResetDeploymentEvent event) {
		draw(Material.AIR);
	}
	
	private void draw(Material material) {
		for (int z = -16; z <= 16; z+= 4) {
			Block block = world.getBlockAt(X, Y, z);
			block.setType(material);			
		}
	}
}
