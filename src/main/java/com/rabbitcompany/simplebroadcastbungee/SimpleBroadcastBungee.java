package com.rabbitcompany.simplebroadcastbungee;

import com.rabbitcompany.simplebroadcastbungee.listeners.PluginMessageListener;
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
	}

	@Override
	public void onDisable() {

	}
}
