package me.timixo.timixoEssentials.utils

import me.timixo.timixoEssentials.TimixoEssentials
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

object HomeManager {
    private val HOMES_KEY = NamespacedKey(TimixoEssentials.instance, "homes")
    private val locationType = LocationDataType { key -> NamespacedKey(TimixoEssentials.instance, key) }

    public fun setHome(player: Player, name: String, position: Location) {
        val playerPDC = player.persistentDataContainer
        val homesPDC = playerPDC.getOrDefault(
            HOMES_KEY,
            PersistentDataType.TAG_CONTAINER,
            playerPDC.adapterContext.newPersistentDataContainer()
        )
        homesPDC.set(NamespacedKey(TimixoEssentials.instance, name), locationType, position)
        playerPDC.set(HOMES_KEY, PersistentDataType.TAG_CONTAINER, homesPDC)
    }

    public fun getHome(player: Player, name: String): Location? {
        val playerPDC = player.persistentDataContainer
        val homesPDC = playerPDC.get(
            HOMES_KEY,
            PersistentDataType.TAG_CONTAINER,
        )
        if (homesPDC == null) return null
        return homesPDC.get(NamespacedKey(TimixoEssentials.instance, name), locationType)
    }

    public fun listHomes(player: Player): Map<String, Location> {
        val playerPDC = player.persistentDataContainer
        val homesPDC = playerPDC.get(HOMES_KEY, PersistentDataType.TAG_CONTAINER) ?: return mapOf()

        return homesPDC.keys.associate { it.key to homesPDC.get(it, locationType)!! }
    }

    public fun countHomes(player: Player): Int {
        val playerPDC = player.persistentDataContainer
        val homesPDC = playerPDC.get(HOMES_KEY, PersistentDataType.TAG_CONTAINER) ?: return 0
        return homesPDC.keys.size
    }
}