package me.InfIV.ChatManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.MPlayer;

public class Facs {

	public static String replace(String format, Player facOwner, Player reciever) {
		if (ChatManager.factions) {
			MPlayer p = MPlayer.get(facOwner);
			String faction = p.getFaction().getColorTo(MPlayer.get(reciever))
					+ p.getFaction().getName();
			if (ChatColor.stripColor(faction).equalsIgnoreCase("Wilderness")) {
				return format.replaceAll("%faction", "");
			}
			if (p.getFaction().getLeader() == p) {
				return format.replaceAll("%faction",
						p.getFaction().getColorTo(MPlayer.get(reciever)) + "**"
								+ faction);
			} else if (p.getRole().isAtLeast(Rel.OFFICER)) {
				return format.replaceAll("%faction",
						p.getFaction().getColorTo(MPlayer.get(reciever)) + "*"
								+ faction);
			} else {
				return format.replaceAll("%faction", faction);
			}

		} else {
			return format.replaceAll("%faction", "");
		}

	}
}
