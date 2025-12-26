package me.timixo.timixoEssentials.utils
import org.bukkit.Location
import org.bukkit.scheduler.BukkitTask

data class TeleportData(
    val startLocation: Location,
    val targetLocation: Location,
    var secondsLeft: Long,
    var task: BukkitTask,
)



