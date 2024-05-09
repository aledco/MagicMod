package net.oberon.magic.enchantment.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.oberon.magic.item.custom.AbstractWandItem;

public abstract class AbstractMagicEnchantment extends Enchantment {
    protected AbstractMagicEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    public abstract Entity magicEntity(World world, PlayerEntity user);

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof AbstractWandItem;
    }
}
