package me.timixo.timixoEssentials.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.option.OptionSchema
import org.bukkit.entity.Player

object TPAManager {
    var pending: MutableMap<Player, AskedTp> = mutableMapOf();

    fun teleportAsk(player: Player, target: Player) {
        pending[target] = AskedTp(player);

        target.sendMessage(Component.text("${player.name} asked to teleport to you", NamedTextColor.DARK_AQUA))
        target.sendMessage(Component.text("/tpaccept to accept", NamedTextColor.GREEN))
        target.sendMessage(Component.text("/tpdecline to decline", NamedTextColor.RED))

    }
    fun teleportAccept(player: Player){
        var location = player.location;
        var source = pending[player]?.fromPlayer
        if (source == null) {
            player.sendMessage(Component.text("You don't have any pending tpas to accept"))
            return
        }
        pending.remove(player)
        source.sendMessage(Component.text("Player ${player.name} accepted you request", NamedTextColor.GREEN))
        TeleportationManager.startTeleport(source, location, 5 )

    }
    fun teleportDeny(player: Player){
    var source = pending[player]?.fromPlayer
        if (source == null) {
            player.sendMessage(Component.text("You don't have any pending requests", NamedTextColor.DARK_AQUA))
            return
        }


    }
    fun teleportCancel(player: Player, target: Player) {
        val chujwie = pending[target]?.fromPlayer
        if (chujwie == player) {
            pending.remove(target)
            player.sendMessage(Component.text("Teleportation to ${player.name} cancelled", NamedTextColor.RED))
            return
        }
        target.sendMessage(Component.text("you don't have any requests send to this player", NamedTextColor.DARK_AQUA))
    }

    public data class AskedTp(var fromPlayer: Player);
}


