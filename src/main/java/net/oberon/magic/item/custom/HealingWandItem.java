package net.oberon.magic.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.oberon.magic.entity.custom.HealingMagicEntity;


public class HealingWandItem extends AbstractWandItem {
    public HealingWandItem(Settings settings) {
        super(settings);
    }

    @Override
    protected int timeBetweenUses() {
        return 30;
    }

    @Override
    protected int useDamage() {
        return 2;
    }

    @Override
    protected Entity magicEntity(World world, PlayerEntity user) {
        return new HealingMagicEntity(world, user);
    }
}
