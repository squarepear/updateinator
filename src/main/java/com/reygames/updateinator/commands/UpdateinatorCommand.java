package com.reygames.updateinator.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

// import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.util.Arrays;

public class UpdateinatorCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(literal("updateinator").requires(Permissions.require("updateinator", 2))
			.then(literal("list").requires(Permissions.require("updateinator.list", 2)).executes(UpdateinatorCommand::list))
			.then(literal("update").requires(Permissions.require("updateinator.update", 4)).executes(UpdateinatorCommand::update))
		);
	}
	
    public static int list(CommandContext<ServerCommandSource> context) {
		// TODO: Get list of user installed mods

		// for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
		// 	ModMetadata metadata = mod.getMetadata();
		// 	var authors = metadata.getAuthors();

		// 	if (!authors.isEmpty() && authors.iterator().next().getName().trim() == "FabricMC")
		// 		continue;

		// 	context.getSource().sendFeedback(new LiteralText(String.format("%s %s %s", metadata.getName(), metadata.getVersion(), metadata.getId())), false);
		// }

		context.getSource().sendFeedback(new TranslatableText("commands.updateinator.list").formatted(Formatting.GOLD), false);
		
        return Command.SINGLE_SUCCESS;
    }

	public static int update(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(new TranslatableText("commands.updateinator.update").formatted(Formatting.GOLD), false);

		return Command.SINGLE_SUCCESS;
	}
}