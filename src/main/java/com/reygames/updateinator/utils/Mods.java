package com.reygames.updateinator.utils;


import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.util.Collection;
import java.util.stream.Collectors;

public class Mods {
	public static Collection<ModContainer> getAll() {
		// Return list of user installed mods
		return FabricLoader.getInstance().getAllMods().stream()
		// Remove mods with author FabricMC
		.filter(mod -> !(!mod.getMetadata().getAuthors().isEmpty() && mod.getMetadata().getAuthors().iterator().next().getName().trim().equals("FabricMC")))
		// Remove mods that don't depend on minecraft
		.filter(mod -> !(mod.getMetadata().getDepends().isEmpty() || !mod.getMetadata().getDepends().iterator().next().getModId().equals("minecraft")))
		// Return collection
		.collect(Collectors.toList());
	}
}
