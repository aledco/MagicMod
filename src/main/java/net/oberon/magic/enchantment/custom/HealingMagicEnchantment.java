package net.oberon.magic.enchantment.custom;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.oberon.magic.entity.custom.HealingMagicEntity;

public class HealingMagicEnchantment extends AbstractMagicEnchantment {
    public HealingMagicEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot... slotTypes) {
        super(rarity, target, slotTypes);
    }

    @Override
    public Entity magicEntity(World world, PlayerEntity user, int level) {
        return new HealingMagicEntity(world, user);
    }

    @Override
    public int reloadTime() {
        return 100;
    }
}
