package com.octopusdeploy.effects;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;


public class FireworkGenerator
	{
		public final static Color[] COLORS = {Color.AQUA,Color.BLUE,Color.FUCHSIA, Color.GREEN,Color.LIME,Color.ORANGE,Color.PURPLE,Color.SILVER,Color.TEAL,Color.YELLOW};

		private final int amount;
		
		public FireworkGenerator(int amount)
		{
			this.amount = amount;
		}
				
		public ItemStack generateItem()
		{
			ItemStack itemStack = new ItemStack(Material.FIREWORK, amount);		
			FireworkMeta fireworkMeta = (FireworkMeta) itemStack.getItemMeta();
			setMeta(fireworkMeta, COLORS);
			
			itemStack.setItemMeta(fireworkMeta);
			
			return itemStack;
		}
		
		public ItemStack generateItem(Color[] colors)
		{
			ItemStack itemStack = new ItemStack(Material.FIREWORK, amount);		
			FireworkMeta fireworkMeta = (FireworkMeta) itemStack.getItemMeta();
			setMeta(fireworkMeta, colors);
			
			itemStack.setItemMeta(fireworkMeta);
			
			return itemStack;		
		}
		
		public void setMeta(FireworkMeta fireworkMeta, Color[] colors)
		{
			Random randomGen = new Random();
			Builder effectBuilder = FireworkEffect.builder();
			
			Type[] types = Type.values();
			int random = randomGen.nextInt(types.length);		
			
			effectBuilder.with(types[random]);
			
			
			effectBuilder.withColor(generateColors(colors, 1 + randomGen.nextInt(colors.length)));
			
			if (randomGen.nextBoolean())
				effectBuilder.withFade(generateColors(colors, 1 + randomGen.nextInt(colors.length)));
			
			effectBuilder.trail(randomGen.nextBoolean());
			effectBuilder.flicker(randomGen.nextBoolean());
			
			
			fireworkMeta.addEffects(effectBuilder.build());
			fireworkMeta.setPower(1 + randomGen.nextInt(3));		
			
		}
		
		private Set<Color> generateColors(Color[] colors, int number)
		{
			Set<Color> results = new HashSet<Color>();
			
			Random randomGen = new Random();
			for (int i = 0; i < number; i++)
			{
				int random = randomGen.nextInt(colors.length);
				results.add(colors[random]);
			}
			
			return results;
		}
}
