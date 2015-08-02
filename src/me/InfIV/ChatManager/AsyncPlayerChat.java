package me.InfIV.ChatManager;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncPlayerChat implements Listener {
	static ChatManager cm = ChatManager.getInstance();
	public static String format = cm.getConfig().getString("Format");
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e){
		if(!e.isCancelled()){
		String f1 = format;
		f1 = replace(f1, e.getPlayer());
		f1 = ChatColor.translateAlternateColorCodes('&', f1);
		if(e.getPlayer().hasPermission("ChatManager.colour")||e.getPlayer().hasPermission("ChatManager.*")){
			
		f1 = f1.replaceAll("%message", ChatColor.translateAlternateColorCodes('&',e.getMessage()));
		for(Player p: e.getRecipients()){
			if(ChatManager.isFactions()){
				p.sendMessage(Facs.replace(f1, e.getPlayer(), p));
			}else{
				p.sendMessage(f1);
			}
		}
		System.out.println(f1);
		e.setCancelled(true);
		}else{
			
			f1 = f1.replaceAll("%message", ChatColor.stripColor(e.getMessage()));
			for(Player p: e.getRecipients()){
				if(ChatManager.isFactions()){
					p.sendMessage(Facs.replace(f1, e.getPlayer(), p));
				}else{
					p.sendMessage(f1);
				}
			}
			System.out.println(f1);
			e.setCancelled(true);
		}
		}
	}
	 Chat chat = ChatManager.getChat();
	Economy econ = ChatManager.getEcon();
	public String replace(String s,Player p) {
		String worldName = p.getWorld().getName();
		return s
		.replaceAll("%prefix",
				chat.getPlayerPrefix(p))
		.replaceAll("%suffix",
				chat.getPlayerSuffix(p))
		.replaceAll("%world", worldName)
		.replaceAll("%uuid", p.getUniqueId().toString())
		.replaceAll("%onlinelayers", "" + Bukkit.getServer().getOnlinePlayers().size())
		.replaceAll("%rank", chat.getPlayerPrefix(p))
		.replaceAll("%balance", "" + econ.getBalance(p))
		.replaceAll("%gamemode", p.getGameMode().toString())
		.replaceAll("%exp", "" + p.getExp() )
		.replaceAll("%expLevel", p.getLevel() + "" )
		.replaceAll("%location", p.getLocation().toString());
		
	}
}
