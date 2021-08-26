package com.reygames.updateinator;

import com.reygames.updateinator.commands.UpdateinatorCommand;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class Commands {
	
	public static void registerCommands() {
		CommandRegistrationCallback.EVENT.register(UpdateinatorCommand::register);
	}
}
