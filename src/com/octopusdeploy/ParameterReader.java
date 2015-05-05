package com.octopusdeploy;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;

public class ParameterReader {
	private World world;
	
	public ParameterReader() {
		world = Bukkit.getWorlds().get(0);
	}
	
	public String getServerUrl() {		
		return readSign(-1, 4, -2) + "/api/";
	}
	
	public String getApiKey() {
		return readSign(-1, 4, -1);
	}
	
	public String getProjectName() {
		return readSign(-1, 4, 1);
	}
	
	public String getEnvironmentName() {
		return readSign(-1, 4, 2);
	}
	
	private String readSign(int x, int y, int z) {
		Block block = world.getBlockAt(x, y, z);
		BlockState blockState = block.getState();
		Sign sign = (Sign) blockState;
		String[] lines = sign.getLines();
		
		StringBuffer str = new StringBuffer();
		for (int i = 1; i < lines.length; i++)
			str.append(lines[i]);
		
		return str.toString();
	}
}
