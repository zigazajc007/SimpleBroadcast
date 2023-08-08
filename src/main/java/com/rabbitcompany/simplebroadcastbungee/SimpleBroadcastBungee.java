package com.rabbitcompany.simplebroadcastbungee;

import com.rabbitcompany.simplebroadcastbungee.listeners.PluginMessageListener;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

public class SimpleBroadcastBungee extends Plugin {

	private static SimpleBroadcastBungee instance;

	public static SimpleBroadcastBungee getInstance(){
		return instance;
	}

	@Override
	public void onEnable(){
		instance = this;
		getProxy().registerChannel("my:simplebroadcast");

		getProxy().getPluginManager().registerListener(this, new PluginMessageListener());

		info(ChatColor.GREEN + "Enabled");
	}

	@Override
	public void onDisable() {
		info(ChatColor.RED + "Disabled");
	}

	private void info(String message) {
		String text = "\n\n";
		text += ChatColor.GRAY + "[]====[" + message + " " + ChatColor.RED + "SimpleBroadcast-Bungee" + ChatColor.GRAY + "]====[]\n";
		text += ChatColor.GRAY + "|\n";
		text += ChatColor.GRAY + "| " + ChatColor.RED + "Information:\n";
		text += ChatColor.GRAY + "|\n";
		text += ChatColor.GRAY + "|   " + ChatColor.BLUE + "Name: " + ChatColor.AQUA + "SimpleBroadcast-Bungee\n";
		text += ChatColor.GRAY + "|   " + ChatColor.BLUE + "Developer: " + ChatColor.AQUA + "Black1_TV\n";
		text += ChatColor.GRAY + "|   " + ChatColor.BLUE + "Version: " + ChatColor.AQUA + getDescription().getVersion() + "\n";
		text += ChatColor.GRAY + "|   " + ChatColor.BLUE + "Website: " + ChatColor.AQUA + "https://rabbit-company.com\n";
		text += ChatColor.GRAY + "|\n";
		text += ChatColor.GRAY + "| " + ChatColor.RED + "Sponsors:\n";
		text += ChatColor.GRAY + "|\n";
		text += ChatColor.GRAY + "|   " + ChatColor.BLUE + "- " + ChatColor.YELLOW + "https://rabbitserverlist.com\n";
		text += ChatColor.GRAY + "|\n";
		text += ChatColor.GRAY + "| " + ChatColor.RED + "Support:\n";
		text += ChatColor.GRAY + "|\n";
		text += ChatColor.GRAY + "|   " + ChatColor.BLUE + "Discord: " + ChatColor.AQUA + "ziga.zajc007\n";
		text += ChatColor.GRAY + "|   " + ChatColor.BLUE + "Mail: " + ChatColor.AQUA + "ziga.zajc007@gmail.com\n";
		text += ChatColor.GRAY + "|   " + ChatColor.BLUE + "Discord: " + ChatColor.AQUA + "https://discord.gg/hUNymXX\n";
		text += ChatColor.GRAY + "|\n";
		text += ChatColor.GRAY + "[]=========================================[]\n";

		getLogger().info(text);
	}
}
