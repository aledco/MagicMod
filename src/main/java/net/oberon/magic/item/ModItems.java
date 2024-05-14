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
import net.oberon.magic.item.custom.WandItem;

public class ModItems {
    public static final Item WAND;
    public static final Item BASIC_MAGIC;
    public static final Item HEALING_MAGIC;

    static {
        WAND = registerItem("wand", new WandItem(new FabricItemSettings()));
        BASIC_MAGIC = registerItem("basic_magic", new Item(new FabricItemSettings()));
        HEALING_MAGIC = registerItem("healing_magic", new Item(new FabricItemSettings()));
    }

    private static void addItemsToToolsTabItemGroup(FabricItemGroupEntries entries) {
        entries.add(WAND);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MagicMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(ModItems::addItemsToToolsTabItemGroup);
    }
}
