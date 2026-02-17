package me.timixo.timixoEssentials.commands

import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

object MessageCommands {
    fun defaultMessage(): LiteralCommandNode<CommandSourceStack> = Commands.literal("msg")
        .then(
            Commands.argument("target", ArgumentTypes.player())
                .then(
                    Commands.argument("message", StringArgumentType.greedyString())
                        .executes { ctx ->
                            val player = ctx.source.executor as? Player
                            val message = ctx.getArgument("message", String()::class.java)
                            val target =
                                ctx.getArgument("target", PlayerSelectorArgumentResolver::class.java)
                                    .resolve(ctx.getSource())
                                    .toMutableList()[0];
                            player?.sendMessage(Component.text("${player.name} --> ${target.name}: ", NamedTextColor.BLUE) .append(Component.text("$message", NamedTextColor.DARK_GRAY)))
                            target.sendMessage(Component.text("${player?.name} --> ${target.name}: ", NamedTextColor.BLUE) .append(Component.text("$message", NamedTextColor.DARK_GRAY)))

                            return@executes 1
                        }
                ))
        .build()

}





