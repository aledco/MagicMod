package net.oberon.magic.entity.custom;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.world.World;
import net.oberon.magic.entity.ModEntities;
import net.oberon.magic.item.ModItems;
import net.oberon.magic.particle.ModParticles;

public class HealingMagicEntity extends AbstractLingeringMagicEntity {
    public HealingMagicEntity(EntityType<? extends HealingMagicEntity> entityType, World world) {
        super(entityType, world);
    }

    public HealingMagicEntity(World world, LivingEntity owner) {
        super(ModEntities.HEALING_MAGIC, world, owner);
    }

    public static HealingMagicEntity create(EntityType<? extends HealingMagicEntity> entityType, World world) {
        return new HealingMagicEntity(entityType, world);
    }

    @Override
    protected void configureEffectCloud(AreaEffectCloudEntity effectCloud) {
        effectCloud.setRadius(2.0f);
        effectCloud.setDuration(300);
        effectCloud.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 1));
    }

    @Override
    protected ParticleEffect getParticleType() {
        return ModParticles.HEALING_MAGIC;
    }

    @Override
    public Item getDefaultItem() {
        return ModItems.HEALING_MAGIC;
    }
}
