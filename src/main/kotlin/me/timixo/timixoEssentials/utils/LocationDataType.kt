package me.timixo.timixoEssentials.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

class LocationDataType(private val pluginKey: (String) -> NamespacedKey) : PersistentDataType<PersistentDataContainer, Location> {

    override fun getPrimitiveType(): Class<PersistentDataContainer> = PersistentDataContainer::class.java
    override fun getComplexType(): Class<Location> = Location::class.java

    override fun toPrimitive(complex: Location, context: PersistentDataAdapterContext): PersistentDataContainer {
        return context.newPersistentDataContainer().apply {
            set(pluginKey("world"), PersistentDataType.STRING, complex.world?.name ?: "world")
            set(pluginKey("x"), PersistentDataType.DOUBLE, complex.x)
            set(pluginKey("y"), PersistentDataType.DOUBLE, complex.y)
            set(pluginKey("z"), PersistentDataType.DOUBLE, complex.z)
            set(pluginKey("yaw"), PersistentDataType.FLOAT, complex.yaw)
            set(pluginKey("pitch"), PersistentDataType.FLOAT, complex.pitch)
        }
    }

    override fun fromPrimitive(primitive: PersistentDataContainer, context: PersistentDataAdapterContext): Location {
        val worldName = primitive.get(pluginKey("world"), PersistentDataType.STRING) ?: "world"
        val x = primitive.getOrDefault(pluginKey("x"), PersistentDataType.DOUBLE, 0.0)
        val y = primitive.getOrDefault(pluginKey("y"), PersistentDataType.DOUBLE, 0.0)
        val z = primitive.getOrDefault(pluginKey("z"), PersistentDataType.DOUBLE, 0.0)
        val yaw = primitive.getOrDefault(pluginKey("yaw"), PersistentDataType.FLOAT, 0f)
        val pitch = primitive.getOrDefault(pluginKey("pitch"), PersistentDataType.FLOAT, 0f)

        return Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch)
    }
}