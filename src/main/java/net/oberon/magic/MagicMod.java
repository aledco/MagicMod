package net.oberon.magic;

import net.fabricmc.api.ModInitializer;

import net.oberon.magic.enchantment.ModEnchantments;
import net.oberon.magic.entity.ModEntities;
import net.oberon.magic.item.ModItemGroups;
import net.oberon.magic.item.ModItems;
import net.oberon.magic.sound.ModSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicMod implements ModInitializer {
	public static final String MOD_ID = "magicmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();
		ModEntities.registerEntities();
		ModSounds.registerSounds();
		ModEnchantments.registerEnchantments();
	}
}
