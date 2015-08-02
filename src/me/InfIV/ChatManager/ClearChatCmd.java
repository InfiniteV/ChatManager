package me.InfIV.ChatManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChatCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String commandLabel,
			String[] args) {
		if (s instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("clearchat")) {
				if (s.hasPermission("chatmanager.clearchat")){
					for(int i = 0; i < 151; i++){
						((Player) s).sendMessage("   ");
					}
				}

					return true;
			}
		}else{
			System.out.println("[ChatManager] Silly console, only players can clear their chat.");
		}
		return false;
	}

}
