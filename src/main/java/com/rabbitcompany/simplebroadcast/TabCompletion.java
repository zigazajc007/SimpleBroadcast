package com.rabbitcompany.simplebroadcast;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if(!command.getName().equalsIgnoreCase("broadcast")) return null;
		if(args.length != 1) return new ArrayList<>();
		ArrayList<String> completion = new ArrayList<>(SimpleBroadcast.getInstance().getConf().getConfigurationSection("channels").getKeys(false));
		completion.add("reload");
		return completion;
	}

}
