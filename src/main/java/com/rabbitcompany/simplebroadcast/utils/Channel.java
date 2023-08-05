package com.rabbitcompany.simplebroadcast.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.rabbitcompany.simplebroadcast.SimpleBroadcast;
import org.bukkit.Bukkit;

public class Channel {

	public static void send(String sender, String subchannel, String... data) {
		if (SimpleBroadcast.getInstance().getConf().getBoolean("bungee_enabled", false)) {
			ByteArrayDataOutput output = ByteStreams.newDataOutput();
			output.writeUTF(sender);
			output.writeUTF(subchannel);
			for (String da : data) output.writeUTF(da);
			Bukkit.getServer().sendPluginMessage(SimpleBroadcast.getInstance(), "my:simplebroadcast", output.toByteArray());
		}
	}

}
