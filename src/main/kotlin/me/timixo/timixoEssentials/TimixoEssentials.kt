package me.timixo.timixoEssentials

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.timixo.timixoEssentials.commands.HomeCommands
import me.timixo.timixoEssentials.commands.TPACommands
import me.timixo.timixoEssentials.events.TeleportationDelayListener
import org.bukkit.plugin.java.JavaPlugin

class TimixoEssentials : JavaPlugin() {
    companion object {
        lateinit var instance: TimixoEssentials
            private set
    }

    override fun onEnable() {
        instance = this
        this.logger.info("[TimixoEssentials] enabled!")

        lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { commands ->
            val registrar = commands.registrar()
            registrar.register(HomeCommands.setHomeCommand())
            registrar.register(HomeCommands.homeCommand())
            registrar.register(HomeCommands.delHomeCommand())
            registrar.register(TPACommands.teleportAsk())
            registrar.register(TPACommands.teleportAccept())
            registrar.register(TPACommands.teleportDeny())
            registrar.register(TPACommands.teleportCancel())
        }

        server.pluginManager.registerEvents(TeleportationDelayListener(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
