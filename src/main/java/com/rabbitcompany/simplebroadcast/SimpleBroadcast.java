package com.rabbitcompany.simplebroadcast;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.rabbitcompany.simplebroadcast.commands.Broadcast;
import com.rabbitcompany.simplebroadcast.utils.Colors;
import com.rabbitcompany.simplebroadcast.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public final class SimpleBroadcast extends JavaPlugin implements PluginMessageListener {

	public static HashSet<String> receivedBroadcasts = new HashSet<String>();
	private static SimpleBroadcast instance;
	private final YamlConfiguration conf = new YamlConfiguration();
	private final YamlConfiguration mess = new YamlConfiguration();
	private File co = null;
	private File me = null;

	public static SimpleBroadcast getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		this.co = new File(getDataFolder(), "config.yml");
		this.me = new File(getDataFolder(), "messages.yml");

		mkdir();
		loadYamls();

		if(getConf().getBoolean("bungee_enabled", false)) {
			getServer().getMessenger().registerOutgoingPluginChannel(this, "my:simplebroadcast");
			getServer().getMessenger().registerIncomingPluginChannel(this, "my:simplebroadcast", this);
		}

		//Commands
		this.getCommand("broadcast").setExecutor(new Broadcast());
		this.getCommand("broadcast").setTabCompleter(new TabCompletion());
	}

	@Override
	public void onDisable() {

	}

	public void mkdir() {
		if (!this.co.exists()) saveResource("config.yml", false);
		if (!this.me.exists()) saveResource("messages.yml", false);
	}

	public void loadYamls() {
		try {
			this.conf.load(this.co);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		try {
			this.mess.load(this.me);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public YamlConfiguration getConf() {
		return this.conf;
	}

	public YamlConfiguration getMess() {
		return this.mess;
	}

	@Override
	public void onPluginMessageReceived(String channel, Player pla, byte[] message) {
		if (!channel.equalsIgnoreCase("my:simplebroadcast")) return;

		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String sender = in.readUTF();
		String subchannel = in.readUTF();

		switch (subchannel){
			case "broadcast":
				String bUUID = in.readUTF();
				String bChannel = in.readUTF();
				String bMessage = in.readUTF();

				if(receivedBroadcasts.contains(bUUID)) break;
				receivedBroadcasts.add(bUUID);
				Set<String> channels = SimpleBroadcast.getInstance().getConf().getConfigurationSection("channels").getKeys(false);
				if(!channels.contains(bChannel)) break;

				String format = SimpleBroadcast.getInstance().getConf().getString("channels." + bChannel + ".format", "&7[&6Broadcast&7] {message}");
				String receiverPermission = SimpleBroadcast.getInstance().getConf().getString("channels." + bChannel + ".receiverPermission", null);
				boolean bungee = SimpleBroadcast.getInstance().getConf().getBoolean("channels." + bChannel + ".bungee", false);

				if(!bungee) break;

				for(Player player : Bukkit.getServer().getOnlinePlayers()){
					if(sender.equals(player.getName())) continue;
					if(receiverPermission != null && !player.hasPermission(receiverPermission)) continue;
					player.sendMessage(Colors.toHex(Message.chat(format.replace("{message}", bMessage))));
				}

				break;
		}
	}
}
