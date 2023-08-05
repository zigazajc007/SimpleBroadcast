package com.rabbitcompany.simplebroadcast;

import com.rabbitcompany.simplebroadcast.commands.Broadcast;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class SimpleBroadcast extends JavaPlugin {

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
}
