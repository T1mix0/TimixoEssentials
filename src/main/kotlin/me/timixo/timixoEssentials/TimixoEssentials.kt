package me.timixo.timixoEssentials

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import me.timixo.timixoEssentials.commands.HomeCommands
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
        }

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
