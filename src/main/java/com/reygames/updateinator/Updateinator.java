package com.reygames.updateinator;

import net.fabricmc.api.ModInitializer;

public class Updateinator implements ModInitializer {

	public static final String MOD_ID = "updateinator";

	@Override
	public void onInitialize() {
		Commands.registerCommands();
	}
}
