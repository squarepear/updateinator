package com.reygames.updateinator.commands;

import com.github.zafarkhaja.semver.Version;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.reygames.updateinator.utils.API.*;
import com.reygames.updateinator.utils.Mods;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.MinecraftVersion;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

// import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

import java.util.Collection;

public class UpdateinatorCommand {
	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
		dispatcher.register(literal("updateinator").requires(Permissions.require("updateinator", 2))
			.then(literal("list").requires(Permissions.require("updateinator.list", 2)).executes(UpdateinatorCommand::list))
			.then(literal("updates").requires(Permissions.require("updateinator.updates", 4)).executes(UpdateinatorCommand::updates))
		);
	}
	
    public static int list(CommandContext<ServerCommandSource> context) {
		Collection<ModContainer> mods = Mods.getAll();

		for (ModContainer mod : mods) {
			ModMetadata metadata = mod.getMetadata();
			
			context.getSource().sendFeedback(new LiteralText("")
				.append( // Mod name in gold
					new LiteralText(metadata.getName()).formatted(Formatting.GOLD).formatted(Formatting.BOLD)
				).append( // Mod version in blue
					new LiteralText(" " + metadata.getVersion().getFriendlyString()).formatted(Formatting.BLUE)
				).append( // Mod id in gray
					new LiteralText(" " + metadata.getId()).formatted(Formatting.GRAY)
				),
				false
				);
			}
			
			return Command.SINGLE_SUCCESS;
		}
		
		public static int updates(CommandContext<ServerCommandSource> context) {
			Collection<ModContainer> mods = Mods.getAll();
			
			for (ModContainer mod : mods) {
				ModMetadata metadata = mod.getMetadata();
				
				try {
					APIVersion version = API.getLatestVersion(metadata.getId(), MinecraftVersion.GAME_VERSION.getReleaseTarget());
					Version localVersion = Version.valueOf(metadata.getVersion().toString());
					Version latestVersion = Version.valueOf(version.version_number.replace(String.format("mc%s-", MinecraftVersion.GAME_VERSION.getReleaseTarget()), ""));
					
					System.out.println(metadata.getVersion().toString());
					System.out.println(version.version_number.replace(String.format("mc%s-", MinecraftVersion.GAME_VERSION.getReleaseTarget()), ""));
					
					context.getSource().sendFeedback(new LiteralText("")
						.append( // Mod name in gold
							new LiteralText(metadata.getName()).formatted(Formatting.GOLD).formatted(Formatting.BOLD)
						).append( // Mod version in blue or green if new
							Version.BUILD_AWARE_ORDER.compare(localVersion, latestVersion) < 0 ?
							new LiteralText(" " + latestVersion.toString()).formatted(Formatting.GREEN) :
							new LiteralText(" " + localVersion.toString()).formatted(Formatting.BLUE)
						).append( // Mod id in gray
							new LiteralText(" " + metadata.getId()).formatted(Formatting.GRAY)
						),
						false
					);
				} catch (Exception e) {
					context.getSource().sendFeedback(new LiteralText("")
						.append( // Mod name in gold
							new LiteralText(metadata.getName()).formatted(Formatting.GOLD).formatted(Formatting.BOLD)
						).append( // Mod version in blue
							new LiteralText(" " + metadata.getVersion().getFriendlyString()).formatted(Formatting.BLUE)
						).append( // Mod id in gray
							new LiteralText(" " + metadata.getId()).formatted(Formatting.GRAY)
						),
						false
					);
				}
			}

		return Command.SINGLE_SUCCESS;
	}
}