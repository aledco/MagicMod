package net.oberon.magic.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.oberon.magic.MagicMod;

public class ModItemGroups {
    public static final ItemGroup MAGIC_GROUP;

    static {
        MAGIC_GROUP = Registry.register(Registries.ITEM_GROUP,
                new Identifier(MagicMod.MOD_ID, "magic"),
                FabricItemGroup.builder().displayName(Text.translatable("itemgroup.magic"))
                        .icon(() -> new ItemStack(ModItems.WAND)).entries(((displayContext, entries) -> {
                            entries.add(ModItems.WAND);
                        })).build());
    }

    public static void registerItemGroups() {
        MagicMod.LOGGER.info("Registering item groups for " + MagicMod.MOD_ID);
    }
}
