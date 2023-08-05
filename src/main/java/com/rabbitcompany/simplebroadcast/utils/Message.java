package com.rabbitcompany.simplebroadcast.utils;

import com.rabbitcompany.simplebroadcast.SimpleBroadcast;
import org.bukkit.ChatColor;

public class Message {
	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static String getPrefix() {
		return Message.chat(SimpleBroadcast.getInstance().getMess().getString("prefix"));
	}

	public static String getMessage(String config) {
		String message;

		message = SimpleBroadcast.getInstance().getMess().getString(config);

		if (message != null) {
			return chat(message.replace("{prefix}", getPrefix()));
		} else {
			return chat("&cValue: &6" + config + "&c is missing in Messages file! Please add it or delete Messages file.");
		}
	}

}
