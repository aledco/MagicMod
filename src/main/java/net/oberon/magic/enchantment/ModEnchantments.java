package net.oberon.magic.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.oberon.magic.MagicMod;
import net.oberon.magic.enchantment.custom.FireMagicEnchantment;


public class ModEnchantments {
    public static Enchantment FIRE_MAGIC;

    static {
        FIRE_MAGIC = registerEnchantment("fire_magic",
                new FireMagicEnchantment(Enchantment.Rarity.COMMON,
                        EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    }

    public static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(MagicMod.MOD_ID, name), enchantment);
    }

    public static void registerEnchantments() {
    }
}
