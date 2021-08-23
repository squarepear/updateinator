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

import java.util.Collection;
import java.util.stream.Collectors;

public class UpdateinatorCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(literal("updateinator").requires(Permissions.require("updateinator", 2))
			.then(literal("list").requires(Permissions.require("updateinator.list", 2)).executes(UpdateinatorCommand::list))
			.then(literal("update").requires(Permissions.require("updateinator.update", 4)).executes(UpdateinatorCommand::update))
		);
	}
	
    public static int list(CommandContext<ServerCommandSource> context) {
		// TODO: Get list of user installed mods

		Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods().stream()
			// Remove mods with author FabricMC
			.filter(mod -> !(!mod.getMetadata().getAuthors().isEmpty() && mod.getMetadata().getAuthors().iterator().next().getName().trim().equals("FabricMC")))
			// Remove mods that don't depend on minecraft
			.filter(mod -> !(mod.getMetadata().getDepends().isEmpty() || !mod.getMetadata().getDepends().iterator().next().getModId().equals("minecraft")))
			// Return collection
			.collect(Collectors.toList());

		for (ModContainer mod : mods) {
			ModMetadata metadata = mod.getMetadata();

			context.getSource().sendFeedback(new LiteralText("")
				.append( // Mod name in gold
					new LiteralText(metadata.getName()).formatted(Formatting.GOLD)
				).append( // Mod version in blue
					new LiteralText(String.format(" %s ", metadata.getVersion().getFriendlyString())).formatted(Formatting.BLUE))
				.append( // Mod id in gray
					new LiteralText(metadata.getId()).formatted(Formatting.GRAY)
				),
				false
			);
		}
		
        return Command.SINGLE_SUCCESS;
    }

	public static int update(CommandContext<ServerCommandSource> context) {
		context.getSource().sendFeedback(new TranslatableText("commands.updateinator.update").formatted(Formatting.GOLD), false);

		return Command.SINGLE_SUCCESS;
	}
}