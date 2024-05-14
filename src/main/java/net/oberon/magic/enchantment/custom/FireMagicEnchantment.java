package net.oberon.magic.enchantment.custom;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.oberon.magic.entity.custom.FireMagicEntity;

public class FireMagicEnchantment extends AbstractMagicEnchantment {
    public FireMagicEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    private static int getExplosivePower(int level) {
        return (int)Math.pow(2, level - 1);
    }

    @Override
    public Entity magicEntity(World world, PlayerEntity user, int level) {
        return new FireMagicEntity(world, user, getExplosivePower(level));
    }

    @Override
    public int getMaxLevel() {
        return 6;
    }
}
