package com.rabbitcompany.simplebroadcastbungee.listeners;

import com.rabbitcompany.simplebroadcastbungee.SimpleBroadcastBungee;
import com.rabbitcompany.simplebroadcastbungee.utils.Channel;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class PluginMessageListener implements Listener {

	@EventHandler
	public void onPluginMessageEvent(PluginMessageEvent e) {
		if(!e.getTag().equalsIgnoreCase("my:simplebroadcast")) return;
		ByteArrayInputStream arrayInput = new ByteArrayInputStream(e.getData());
		DataInputStream input = new DataInputStream(arrayInput);

		try{
			String sender = input.readUTF();
			String subchannel = input.readUTF();

			SimpleBroadcastBungee.getInstance().getLogger().info("Sender: " + sender + " Subchannel: " + subchannel);

			switch (subchannel){
				case "broadcast":
					String uuid = input.readUTF();
					String channel = input.readUTF();
					String message = input.readUTF();
					Channel.sendToAllServers(sender, subchannel, uuid, channel, message);
					break;
			}
			input.close();
		}catch (IOException ignored){}
	}
}
