package com.reygames.updateinator.commands;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ModCommands {
	
	public static void registerCommands() {
		CommandRegistrationCallback.EVENT.register(UpdateinatorCommand::register);
	}
}
