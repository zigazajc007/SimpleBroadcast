package com.rabbitcompany.simplebroadcast.utils;

import com.rabbitcompany.simplebroadcast.SimpleBroadcast;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Colors {

	private static final Pattern hex_pattern = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})");

	public static String toHex(String message) {
		if (Bukkit.getVersion().contains("1.8") || Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.10") || Bukkit.getVersion().contains("1.11") || Bukkit.getVersion().contains("1.12") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.15")) return message;

		Matcher matcher = hex_pattern.matcher(message);
		while (matcher.find()) {
			String color = message.substring(matcher.start(), matcher.end());
			if (color.matches("(?<!\\\\)(#[a-fA-F0-9]{6})")) {
				message = message.replace(color, net.md_5.bungee.api.ChatColor.of(color).toString());
			}
			matcher = hex_pattern.matcher(message);
		}
		String color_prefix = SimpleBroadcast.getInstance().getConf().getString("color_prefix", ";");
		if (message.contains(color_prefix)) {
			for (HexColors color : HexColors.values()) {
				if (message.contains(color_prefix + color.name().toLowerCase())) {
					message = message.replace(color_prefix + color.name().toLowerCase(), net.md_5.bungee.api.ChatColor.of(color.hex).toString());
				}
			}
		}

		return message;
	}
}
