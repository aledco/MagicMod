package net.oberon.magic.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.oberon.magic.MagicMod;
import net.oberon.magic.item.custom.BasicMagicItem;
import net.oberon.magic.item.custom.BasicWandItem;
import net.oberon.magic.item.custom.FireWandItem;

public class ModItems {
    public static final Item BASIC_WAND = registerItem("basic_wand", new BasicWandItem(new FabricItemSettings()));
    public static final Item FIRE_WAND = registerItem("fire_wand", new FireWandItem(new FabricItemSettings()));

    public static final Item BASIC_MAGIC = registerItem("basic_magic", new BasicMagicItem(new FabricItemSettings()));

    private static void addItemsToToolsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(BASIC_WAND);
        entries.add(FIRE_WAND);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MagicMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolsTabItemGroup);
    }
}
