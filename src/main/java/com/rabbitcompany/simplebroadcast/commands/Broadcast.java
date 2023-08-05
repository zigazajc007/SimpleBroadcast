package com.rabbitcompany.simplebroadcast.commands;

import com.rabbitcompany.simplebroadcast.SimpleBroadcast;
import com.rabbitcompany.simplebroadcast.utils.Colors;
import com.rabbitcompany.simplebroadcast.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Broadcast implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(args[0].equals("reload")){
			if(!sender.hasPermission("broadcast.reload")){
				sender.sendMessage(Message.getMessage("permission"));
				return true;
			}
			SimpleBroadcast.getInstance().mkdir();
			SimpleBroadcast.getInstance().loadYamls();
			sender.sendMessage(Message.getMessage("reload"));
			return true;
		}

		if(args.length < 2){
			sender.sendMessage(Message.getMessage("broadcast_syntax"));
			return true;
		}

		String channel = args[0];
		String message = Arrays.stream(args).skip(1).collect(Collectors.joining());
		Set<String> channels = SimpleBroadcast.getInstance().getConf().getConfigurationSection("channels").getKeys(false);

		if(!channels.contains(channel)){
			sender.sendMessage(Message.getMessage("invalid_channel"));
			return true;
		}

		String format = SimpleBroadcast.getInstance().getConf().getString("channels." + channel + ".format", "&7[&6Broadcast&7] {message}");
		String senderPermission = SimpleBroadcast.getInstance().getConf().getString("channels." + channel + ".senderPermission", null);
		String receiverPermission = SimpleBroadcast.getInstance().getConf().getString("channels." + channel + ".receiverPermission", null);

		if(senderPermission != null && !sender.hasPermission(senderPermission)){
			sender.sendMessage(Message.getMessage("permission"));
			return true;
		}

		String senderUUID = UUID.randomUUID().toString();
		if(sender instanceof Player){
			Player player = (Player) sender;
			senderUUID = player.getUniqueId().toString();
		}

		int amount = 0;
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			if(senderUUID.equals(player.getUniqueId().toString())) continue;
			if(receiverPermission != null && !player.hasPermission(receiverPermission)) continue;
			player.sendMessage(Colors.toHex(Message.chat(format.replace("{message}", message))));
			amount++;
		}

		sender.sendMessage(Message.getMessage("message_broadcasted_amount").replace("{amount}", String.valueOf(amount)));
		return true;
	}

}
