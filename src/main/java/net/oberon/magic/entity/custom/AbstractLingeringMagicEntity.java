package net.oberon.magic.entity.custom;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.oberon.magic.entity.ModEntities;

import java.util.List;

public abstract class AbstractLingeringMagicEntity extends ExplosiveProjectileEntity {
    private int ticks = 0;

    public AbstractLingeringMagicEntity(EntityType<? extends AbstractLingeringMagicEntity> entityType, World world) {
        super(entityType, world);
    }

    public AbstractLingeringMagicEntity(EntityType<? extends AbstractLingeringMagicEntity> entityType, World world, LivingEntity owner) {
        super(entityType, world);
        this.setOwner(owner);
        this.setPosition(owner.getPos().add(0, 1, 0));
        this.setVelocity(owner, owner.getPitch(), owner.getYaw(), -1.0f, speed(), 1.0f);
    }

    protected abstract float speed();

    @Override
    protected abstract ParticleEffect getParticleType();

    protected abstract void addStatusEffects(AreaEffectCloudEntity effectCloud);

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (hitResult.getType() == HitResult.Type.ENTITY && this.isOwner(((EntityHitResult)hitResult).getEntity())) {
            return;
        }

        if (!this.getWorld().isClient) {
            List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(4.0, 2.0, 4.0));
            var effectCloud = this.buildEffectCloud();
            if (!list.isEmpty()) {
                for (LivingEntity livingEntity : list) {
                    double d = this.squaredDistanceTo(livingEntity);
                    if (!(d < 16.0)) continue;
                    effectCloud.setPosition(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                    break;
                }
            }

            this.getWorld().spawnEntity(effectCloud);
            this.discard();
        }
    }

    private AreaEffectCloudEntity buildEffectCloud() {
        var effectCloud = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
        effectCloud.setOwner((LivingEntity)this.getOwner());
        effectCloud.setParticleType(this.getParticleType());
        effectCloud.setRadius(3.0f);
        effectCloud.setDuration(600);
        effectCloud.setRadiusGrowth((7.0f - effectCloud.getRadius()) / (float)effectCloud.getDuration());
        this.addStatusEffects(effectCloud);
        return effectCloud;
    }

    @Override
    public void tick() {
        super.tick();
        this.ticks++;
        if (this.ticks >= ModEntities.MAX_TICKS && !this.getWorld().isClient) {
            this.discard();
        }
    }
}
