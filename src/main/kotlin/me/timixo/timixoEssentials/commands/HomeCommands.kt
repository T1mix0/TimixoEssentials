package me.timixo.timixoEssentials.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.timixo.timixoEssentials.utils.HomeManager
import me.timixo.timixoEssentials.utils.TeleportationManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

object HomeCommands {
    fun setHomeCommand(): LiteralCommandNode<CommandSourceStack> = Commands.literal("sethome")
        .then(Commands.argument("name", StringArgumentType.word())
            .executes { ctx ->
                val player = ctx.source.executor!! as Player
                if (HomeManager.countHomes(player) >= 3) {
                    player.sendMessage(Component.text("You cannot set more homes", NamedTextColor.DARK_RED)
                        .decoration(TextDecoration.BOLD, true))
                    return@executes 1
                }
                val name = StringArgumentType.getString(ctx, "name")
                HomeManager.setHome(player, name, ctx.source.location)
                    player.sendMessage(Component.text("Home named: $name was successfully set", NamedTextColor.GREEN)
                        .decoration(TextDecoration.BOLD, false))
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
                if (location == null) {
                    ctx.source.executor?.sendMessage(Component.text("You don't have home named: $name", NamedTextColor.DARK_RED)
                        .decoration(TextDecoration.BOLD, false))
                    return@executes 1
                }

                TeleportationManager.startTeleport(entity, location, 5)
                return@executes 1
            }
        )
        .build()

    fun delHomeCommand(): LiteralCommandNode<CommandSourceStack> = Commands.literal("delhome")
        .then(Commands.argument("name", StringArgumentType.word())
            .suggests { ctx, builder ->
                HomeManager.listHomes(ctx.source.executor!! as Player)
                    .forEach { builder.suggest(it.key) }
                return@suggests builder.buildFuture()
            }
            .executes { ctx ->
                val name = StringArgumentType.getString(ctx, "name")
                val player = ctx.source.executor!! as Player
                if (HomeManager.removeHome(player, name)) {
                player.sendMessage(Component.text("Home named $name has been deleted", NamedTextColor.GREEN))
                }



                return@executes 1
            }
        )
        .build()
}