package me.timixo.timixoEssentials.utils

import me.timixo.timixoEssentials.TimixoEssentials
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent

object TeleportationManager {
    val activeTeleports = mutableMapOf<Player, TeleportData>()

    fun startTeleport(player: Entity, target: Location, seconds: Long) {
        if (activeTeleports.contains(player)) {
            activeTeleports.remove(player)?.task?.cancel()
            player.sendMessage(Component.text("Previous teleportation was cancelled", NamedTextColor.GREEN))
        }
        val scheduler = Bukkit.getScheduler()

        val task = scheduler.runTaskLater(TimixoEssentials.instance, Runnable {
            activeTeleports.remove(player)
            player.teleport(target, PlayerTeleportEvent.TeleportCause.COMMAND)
        }, seconds*20)

        val data = TeleportData(
            startLocation = player.location.clone(),
            targetLocation = target,
            secondsLeft = seconds,
            task = task,
        )
        activeTeleports[player as Player] = data
    }
}