package net.oberon.magic.enchantment.custom;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.oberon.magic.item.custom.WandItem;

public abstract class AbstractMagicEnchantment extends Enchantment {
    public AbstractMagicEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    public abstract Entity magicEntity(World world, PlayerEntity user, int level);

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof WandItem;
    }

    @Override
    public boolean canAccept(Enchantment other) {
        if (!super.canAccept(other)) {
            return false;
        }

        return !(other instanceof AbstractMagicEnchantment);
    }

    public int reloadTime() {
        return 20;
    }
}
