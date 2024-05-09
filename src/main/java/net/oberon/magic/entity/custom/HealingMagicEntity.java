package net.oberon.magic.entity.custom;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.oberon.magic.entity.ModEntities;

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
    protected float speed() {
        return 2.0f;
    }

    @Override
    protected ParticleEffect getParticleType() {
        return ParticleTypes.GLOW;
    }

    @Override
    protected void addStatusEffects(AreaEffectCloudEntity effectCloud) {
        effectCloud.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 1));
    }
}
