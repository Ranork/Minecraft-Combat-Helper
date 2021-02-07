package com.ranork.combathelper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CombatHelper extends JavaPlugin {

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("§4    _   _  __   _ _____ ___  ___  _  _ ");
		Bukkit.getConsoleSender().sendMessage("§4   /_\\ | |/ /  /_\\_   _| _ \\/ _ \\| \\| |");
		Bukkit.getConsoleSender().sendMessage("§4  / _ \\| ' <  / _ \\| | |   / (_) | .` |        CombatHelper Plugini Baslatildi.");
		Bukkit.getConsoleSender().sendMessage("§4 /_/ \\_\\_|\\_\\/_/ \\_\\_| |_|_\\\\___/|_|\\_|");
		Bukkit.getConsoleSender().sendMessage("§4                                       ");
		Bukkit.getConsoleSender().sendMessage("§4Dev by Ranork");

		PluginManager pm = getServer().getPluginManager();
		CombatListener listener = new CombatListener(this); 
		pm.registerEvents(listener, this);
		
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("## CombatHelper Plugini Durduruldu ##");
	}
	
}
