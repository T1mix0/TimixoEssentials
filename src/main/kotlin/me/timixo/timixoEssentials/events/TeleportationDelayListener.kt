package me.timixo.timixoEssentials.events

import me.timixo.timixoEssentials.utils.TeleportationManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerMoveEvent

class TeleportationDelayListener : Listener {
    private fun cancelPendingTeleports(player: Player) {
        TeleportationManager.activeTeleports.computeIfPresent(player) { plr, data ->
            data.task.cancel()
            plr.sendMessage(Component.text("Teleportation cancelled!", NamedTextColor.DARK_RED)
                .decoration(TextDecoration.BOLD, true))
            return@computeIfPresent null
        }
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        if (event.hasChangedPosition()) cancelPendingTeleports(event.player)
    }

    @EventHandler
    fun onPlayerDamage(event: EntityDamageEvent) {
        if (event.entity !is Player) return;
        cancelPendingTeleports(event.entity as Player)
    }
}