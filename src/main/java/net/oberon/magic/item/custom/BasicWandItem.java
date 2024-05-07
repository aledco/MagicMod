package net.oberon.magic.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.oberon.magic.entity.custom.BasicMagicEntity;


public class BasicWandItem extends AbstractWandItem {
    public BasicWandItem(Settings settings) {
        super(settings);
    }

    @Override
    protected int timeBetweenUses() {
        return 20;
    }

    @Override
    protected int useDamage() {
        return 1;
    }

    @Override
    protected Entity magicEntity(World world, PlayerEntity user) {
        return new BasicMagicEntity(world, user);
    }
}
