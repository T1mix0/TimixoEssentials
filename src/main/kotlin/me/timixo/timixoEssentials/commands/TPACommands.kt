package me.timixo.timixoEssentials.commands

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import io.papermc.paper.command.brigadier.argument.ArgumentTypes
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver
import me.timixo.timixoEssentials.utils.TPAManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player

object TPACommands {
    fun teleportAsk(): LiteralCommandNode<CommandSourceStack> = Commands.literal("tpa")
        .then(
            Commands.argument("target", ArgumentTypes.player())

                .executes { ctx ->
                    val player = ctx.source.executor as Player
                    val target =
                        ctx.getArgument("target", PlayerSelectorArgumentResolver::class.java).resolve(ctx.getSource())
                            .toMutableList()[0];
                    ctx.source.executor?.sendMessage(
                        Component.text(
                            "You want to teleport to ${target.name}",
                            NamedTextColor.GREEN
                        )
                    ) //Do wyjebania późnej
                    TPAManager.teleportAsk(player, target)
                    return@executes 1
                }
        )
        .build()

    fun teleportAccept(): LiteralCommandNode<CommandSourceStack> = Commands.literal("tpaccept")
        .executes { ctx ->
            val player = ctx.source.executor as Player
            TPAManager.teleportAccept(player)
            return@executes 1


        }
        .build()

    fun teleportDeny(): LiteralCommandNode<CommandSourceStack> = Commands.literal("tpdeny")
        .executes { ctx ->
            val player = ctx.source.executor as Player
            TPAManager.teleportDeny(player)
            return@executes 1
        }
        .build()


    fun teleportCancel(): LiteralCommandNode<CommandSourceStack> = Commands.literal("tpcancel")
        .then(
            Commands.argument("target", ArgumentTypes.player())

                .executes { ctx ->
                    val player = ctx.source.executor as Player
                    val target =
                        ctx.getArgument("target", PlayerSelectorArgumentResolver::class.java).resolve(ctx.getSource())
                            .toMutableList()[0];
                    TPAManager.teleportCancel(player, target)
                    return@executes 1
                }
        )
        .build()
}