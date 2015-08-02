package me.InfIV.ChatManager;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager extends JavaPlugin {

	static ChatManager instance;
	static Economy econ;
	static Chat chat;
	static boolean factions = false;

	@Override
	public void onEnable() {
	
		if (!setupEconomy()) {
			System.out
					.println("[ChatManager] - Disabled due to no Vault dependency found!");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		loadConfig();
		instance = this;
		
		getCommand("ChatManager").setExecutor(this);
		
		if (Bukkit.getPluginManager().getPlugin("Factions") != null
				&& Bukkit.getPluginManager().getPlugin("MassiveCore") != null) {
			factions = true;
		}
		getCommand("clearchat").setExecutor(new ClearChatCmd());
		setupChat();
		Bukkit.getPluginManager().registerEvents(new AsyncPlayerChat(), this);
		System.out.println("[ChatManager] Enabled ChatManager!");
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	public static ChatManager getInstance() {
		return instance;
	}

	public static boolean isFactions() {
		return factions;
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager()
				.getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	public static Chat getChat() {
		return chat;
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}

	public static Economy getEcon() {
		return econ;
	}

	public void loadConfig() {
		getConfig()
				.addDefault("Format", "%prefix %player %suffix &f: %message");
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	@Override
	public boolean onCommand(CommandSender s,Command cmd, String commandLabel, String[] args){
		if(cmd.getName().equalsIgnoreCase("ChatManager")){
			if(s instanceof Player){
				Player p = (Player) s;
				if(p.hasPermission("chatmanager.command")||p.hasPermission("ChatManager.*")){
					if(args.length==0){
						p.sendMessage("§a§lChatManager §8 - §cCommands");
						p.sendMessage("§9reload - Reloads the plugin.");
						return true;
					}else{
						if(args.length==1){
							if(args[0].equalsIgnoreCase("reload")){
								if(p.hasPermission("ChatManager.reload")||p.hasPermission("ChatManager.*"))
								reloadConfig();
								AsyncPlayerChat.format = getConfig().getString("Format");
								p.sendMessage("§a§lChatManager§8 - §9Reloaded config!");
								return true;
							}else{
								p.sendMessage("§a§lChatManager§8 - §cCommands");
								p.sendMessage("§9Reload - Reloads the plugin.");
								return true;
							}
						}else{
							p.sendMessage("§a§lChatManager§8 - §cCommands");
							p.sendMessage("§9reload - Reloads the plugin.");
							return true;
						}
					}
				}else{
					p.sendMessage("Unknown command. Type \"/help\" for help.");
					return true;
				}
			}else{
				if(args.length==0){
					System.out.println("ChatManager - Commands");
					System.out.println("reload - Reloads the plugin.");
					return true;
				}else{
					if(args.length==1){
						if(args[0].equalsIgnoreCase("reload")){
							reloadConfig();
							System.out.println("ChatManager - Reloaded config!");
							return true;
						}else{
							System.out.println("§lChatManager - Commands");
							System.out.println("reload - Reloads the plugin.");
							return true;
						}
					}else{
						System.out.println("ChatManager - Commands");
						System.out.println("reload - Reloads the plugin.");
						return true;
					}
				}
			}
		}
		return false;
	}
}
