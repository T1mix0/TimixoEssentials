package me.timixo.timixoEssentials.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.timixo.timixoEssentials.utils.HomeManager
import me.timixo.timixoEssentials.utils.TeleportationManager.startTeleport
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object HomeCommands {
    fun setHomeCommand(): LiteralCommandNode<CommandSourceStack> = Commands.literal("sethome")
        .then(Commands.argument("name", StringArgumentType.word())
            .executes { ctx ->
                val player = ctx.source.executor!! as Player
                if (HomeManager.countHomes(player) >= 3)
                    player.sendMessage("${ChatColor.DARK_RED}YOU CAN'T SET MORE HOMES")
                val name = StringArgumentType.getString(ctx, "name")
                HomeManager.setHome(player, name, ctx.source.location)
                    player.sendMessage("${ChatColor.DARK_GREEN}Home named: $name was successfully set.")
                return@executes 1
            }
        )
        .build()

    fun homeCommand(): LiteralCommandNode<CommandSourceStack> = Commands.literal("home")
        .then(Commands.argument("name", StringArgumentType.word())
            .suggests { ctx, builder ->
                HomeManager.listHomes(ctx.source.executor!! as Player)
                    .forEach { builder.suggest(it.key) }
                return@suggests builder.buildFuture()
            }
            .executes { ctx ->
                val name = StringArgumentType.getString(ctx, "name")
                val entity = ctx.source.executor!! as Player
                val location = HomeManager.getHome(ctx.source.executor!! as Player, name)
                if (location == null) ctx.source.executor?.sendMessage("There is no home named: $name")
                else {
                        ctx.source.executor?.sendMessage("Teleporting to home named: $name: ")
                        ctx.source.executor?.sendMessage("Don't move or take damage")
                    startTeleport(entity, location, 5)
                }
                    return@executes 1
            }
        )
        .build()
}