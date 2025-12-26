package me.timixo.timixoEssentials.utils

import io.papermc.paper.command.brigadier.argument.ArgumentTypes.entity
import me.timixo.timixoEssentials.TimixoEssentials
import net.kyori.adventure.text.Component
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitScheduler
import java.util.UUID
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import org.checkerframework.checker.units.qual.Length

object TeleportationManager : Listener {

    val activeTeleports = mutableMapOf<Player, TeleportData>()

    public fun startTeleport(player: Entity, target: Location, seconds: Long) {

        val scheduler = Bukkit.getScheduler()

        val task = scheduler.runTaskLater(TimixoEssentials.instance, Runnable {
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