/*
                         TestPlugin Copyright MFDGaming
                 This file is licensed under the GPLv2 license.
              To use this file you must own a copy of the license.
                       If you do not you can get it from:
            http://www.gnu.org/licenses/old-licenses/gpl-2.0.en.html
 */

package mfdgaming.testplugin;

import cn.nukkit.block.BlockID;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;

public class TestPlugin extends PluginBase implements Listener {
	@Override
	public void onEnable() {
		this.getServer().getPluginManager().registerEvents(this, this);
		this.getLogger().info("TestPlugin enabled :)");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (command.getName().equals("banme")) {
				String reason = "You asked for it";
				this.getServer().getNameBans().addBan(player.getName(), reason, null, "Server");
				player.kick(PlayerKickEvent.Reason.NAME_BANNED, reason);
			}
		}
		return true;
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getBlock().getId() == BlockID.COBBLESTONE) {
			Player player = event.getPlayer();
			String reason = "Broke cobblestone";
			this.getServer().getNameBans().addBan(player.getName(), reason, null, "Server");
			player.kick(PlayerKickEvent.Reason.NAME_BANNED, reason);
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		String reason = "Died";
		this.getServer().getNameBans().addBan(player.getName(), reason, null, "Server");
		player.kick(PlayerKickEvent.Reason.NAME_BANNED, reason);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		this.getServer().broadcastMessage(TextFormat.GREEN + "Welcome " + event.getPlayer().getName() + " to the server!");
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		this.getServer().broadcastMessage(TextFormat.LIGHT_PURPLE + "Goodbye " + event.getPlayer().getName() + ".");
	}
}