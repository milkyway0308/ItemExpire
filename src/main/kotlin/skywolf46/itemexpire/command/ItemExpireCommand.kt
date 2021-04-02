package skywolf46.itemexpire.command

import org.bukkit.entity.Player
import skywolf46.commandannotation.annotations.legacy.MinecraftCommand
import skywolf46.commandannotation.annotations.minecraft.PermissionHandler
import skywolf46.commandannotation.annotations.minecraft.PlayerOnly
import skywolf46.commandannotation.data.command.CommandArgument
import skywolf46.extrautility.util.TimeParser
import skywolf46.extrautility.util.sendMessage
import skywolf46.itemexpire.command.ItemExpireCommand.removeWrap
import skywolf46.itemexpire.util.isExpireItem
import skywolf46.itemexpire.util.removeExpire
import skywolf46.itemexpire.util.unwrap
import skywolf46.itemexpire.util.wrap

object ItemExpireCommand {
    @JvmStatic
    @MinecraftCommand(
        value = [
            "/itemexpire",
            "/ieset"
        ]
    )
    @PermissionHandler(
        value = "itemexpire.admin",
        message = "&cPermission denied."
    )
    @PlayerOnly("&cPlayer only command")
    fun Player.applyWrapper(arg: CommandArgument) {
        player.itemInHand?.let { item ->
            if (item.isExpireItem()) {
                sendMessage("§cItem is already marked as expire item! If you want to reset, use /itemexpireremove or /ieremove to remove mark on item.")
                return
            }
            if (arg.length() < 1) {
                sendMessage(
                    "§e/${arg.command} <expireTime:dhms>",
                    "§7Apply time limitation to holding item.",
                    "§7Parameter accepts dhms to define time. At example, you can apply 1 day, 12 hours limitation to item.",
                    "§7Example: §b/${arg.command} 1d12h"
                )
                return
            }
            try {
                val time = TimeParser.parseToMillisecond(arg[0])
                if (time <= 0) {
                    sendMessage("§cTime requires over 1ms.")
                    return
                }
                sendMessage("§aExpiration time is apply to holding item.")
                item.wrap(time)
            } catch (e: Exception) {
                e.printStackTrace()
                sendMessage("§cTime format error: Something wrong on time parameter.")
            }
        } ?: run {
            sendMessage("§cMust have to hold a target item first.")
        }
    }

    @JvmStatic
    @MinecraftCommand(
        value = [
            "/itemexpireremove",
            "/ieremove"
        ]
    )
    @PermissionHandler(
        value = "ieremove",
        message = "&cPermission denied."
    )
    @PlayerOnly("&cPlayer only command")
    fun Player.removeWrap(arg: CommandArgument) {
        itemInHand?.let { item ->
            if (!item.isExpireItem()) {
                sendMessage("§cTarget item is not marked as ItemExpire item.")
                return
            }
            item.removeExpire()
            sendMessage("§aTarget item is unmarked.")
        } ?: run {
            sendMessage("§cMust have to hold a target item first.")
        }
    }
}