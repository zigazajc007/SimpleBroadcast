package com.rabbitcompany.simplebroadcastbungee.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.rabbitcompany.simplebroadcastbungee.SimpleBroadcastBungee;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Map;
import java.util.Set;

public class Channel {

	public static void send(String player, String subchannel, String... data) {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF(player);
		output.writeUTF(subchannel);
		for (String da : data) output.writeUTF(da);

		try {
			if (SimpleBroadcastBungee.getInstance().getProxy().getPlayer(player).isConnected())
				SimpleBroadcastBungee.getInstance().getProxy().getPlayer(player).getServer().getInfo().sendData("my:simplebroadcast", output.toByteArray());
		} catch (Exception ignored) {}
	}

	public static Set<String> sendToAllServers(String player, String subchannel, String... data) {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF(player);
		output.writeUTF(subchannel);
		for (String da : data) output.writeUTF(da);

		Map<String, ServerInfo> servers = SimpleBroadcastBungee.getInstance().getProxy().getServers();
		for (Map.Entry<String, ServerInfo> en : servers.entrySet()) {
			String name = en.getKey();
			SimpleBroadcastBungee.getInstance().getProxy().getServerInfo(name).sendData("my:simplebroadcast", output.toByteArray());
		}
		return servers.keySet();
	}

}
