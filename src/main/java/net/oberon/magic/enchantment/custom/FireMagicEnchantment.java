package net.oberon.magic.enchantment.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.oberon.magic.entity.custom.FireMagicEntity;
import net.oberon.magic.item.custom.AbstractWandItem;

public class FireMagicEnchantment extends AbstractMagicEnchantment {
    public FireMagicEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public Entity magicEntity(World world, PlayerEntity user) {
        return new FireMagicEntity(world, user);
    }
}
