package net.oberon.magic.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.oberon.magic.MagicMod;
import net.oberon.magic.enchantment.custom.FireMagicEnchantment;
import net.oberon.magic.enchantment.custom.HealingMagicEnchantment;


public class ModEnchantments {
    public final static Enchantment FIRE_MAGIC;
    public final static Enchantment HEALING_MAGIC;

    static {
        FIRE_MAGIC = registerEnchantment("fire_magic",
                new FireMagicEnchantment(Enchantment.Rarity.COMMON,
                        EnchantmentTarget.BREAKABLE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));

        HEALING_MAGIC = registerEnchantment("healing_magic",
                new HealingMagicEnchantment(Enchantment.Rarity.UNCOMMON,
                        EnchantmentTarget.BREAKABLE, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
    }

    public static Enchantment registerEnchantment(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(MagicMod.MOD_ID, name), enchantment);
    }

    public static void registerEnchantments() {
    }
}
