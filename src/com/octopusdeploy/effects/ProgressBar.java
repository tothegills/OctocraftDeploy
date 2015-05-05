package com.octopusdeploy.effects;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.octopusdeploy.event.DeploymentFailedEvent;
import com.octopusdeploy.event.DeploymentFinishedEvent;
import com.octopusdeploy.event.DeploymentProgressEvent;
import com.octopusdeploy.event.DeploymentStartedEvent;
import com.octopusdeploy.event.ResetDeploymentEvent;

public class ProgressBar implements Listener {
	
	private static final Material PROGRESS_BACKGROUND_MATERIAL = Material.GOLD_BLOCK;
	private static final DyeColor PROGRESS_COLOR = DyeColor.LIME;
	private static final DyeColor SUCCESS_COLOR = DyeColor.GREEN;
	private static final DyeColor FAIL_COLOR = DyeColor.RED;
		
	private World world;
	private final int X = 10;
	private final int Y = 5;
	private final int LENGTH = 10;
	
	public ProgressBar() {
		world = Bukkit.getWorlds().get(0);
	}
	
	@EventHandler
	public void drawEmptyProgress(DeploymentStartedEvent event) {
		drawBar(PROGRESS_BACKGROUND_MATERIAL);
	}
	
	@EventHandler
	private void drawProgress(DeploymentProgressEvent event) {
		int progress = event.getProgress() * (LENGTH * 2) / 100;
		for (int z = -LENGTH; z <= progress - LENGTH; z++) {
			Block block = world.getBlockAt(X, Y, z);
			block.setType(Material.WOOL);
			block.setData(PROGRESS_COLOR.getData());
		}				
	}
	
	@EventHandler
	private void drawFinishedDeployment(DeploymentFinishedEvent event) {
		drawBar(Material.WOOL, SUCCESS_COLOR.getData());
	}

	@EventHandler
	private void drawFailedDeployment(DeploymentFailedEvent event) {
		drawBar(Material.WOOL, FAIL_COLOR.getData());
	}
	
	@EventHandler
	private void resetProgress(ResetDeploymentEvent event) {
		drawBar(Material.AIR);		
	}

	private void drawBar(Material material) {
		drawBar(material, (byte) 0);
	}
	
	private void drawBar(Material material, byte data) {
		for (int z = -LENGTH; z <= LENGTH; z++) {
			Block block = world.getBlockAt(X, Y, z);
			block.setType(material);
			if (data > 0)
				block.setData(data);			
		}
	}
}
