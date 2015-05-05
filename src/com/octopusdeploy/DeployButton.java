package com.octopusdeploy;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.octopusdeploy.event.DeploymentStartedEvent;

public class DeployButton implements Listener {

	@EventHandler
	public void launchDeployment(PlayerInteractEvent event) {
		Block clickedBlock = event.getClickedBlock();
		if (clickedBlock == null)
			return;
		
		if (clickedBlock.getType() != Material.STONE_BUTTON)
			return;
		
		System.out.println("Starting deployment");
		
		new DeploymentStartedEvent().callEvent();
	}
}
