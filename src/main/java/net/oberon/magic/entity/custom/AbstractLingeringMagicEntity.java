package net.oberon.magic.entity.custom;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.oberon.magic.entity.ModEntities;

import java.util.List;

public abstract class AbstractLingeringMagicEntity extends ThrownItemEntity {
    private int ticks = 0;

    public AbstractLingeringMagicEntity(EntityType<? extends AbstractLingeringMagicEntity> entityType, World world) {
        super(entityType, world);
    }

    public AbstractLingeringMagicEntity(EntityType<? extends AbstractLingeringMagicEntity> entityType, World world, LivingEntity owner) {
        super(entityType, world);
        this.setOwner(owner);
        this.setNoGravity(true);
        this.setPosition(owner.getPos().add(0, 1, 0));
        this.setVelocity(owner, owner.getPitch(), owner.getYaw(), -1.0f, speed(), 1.0f);
    }

    protected float speed() {
        return 2.0f;
    }

    protected abstract ParticleEffect getParticleType();

    protected abstract void configureEffectCloud(AreaEffectCloudEntity effectCloud);

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (hitResult.getType() == HitResult.Type.ENTITY && this.isOwner(((EntityHitResult)hitResult).getEntity())) {
            return;
        }

        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.AMBIENT, 1.0f, 2.0f);
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
        this.configureEffectCloud(effectCloud);
        return effectCloud;
    }

    @Override
    public void tick() {
        super.tick();
        this.ticks++;
        if (this.ticks >= ModEntities.MAX_TICKS && !this.getWorld().isClient) {
            this.discard();
        }

        var vel = this.getVelocity();
        var d = this.getX() + vel.x;
        var e = this.getY() + vel.y;
        var f = this.getZ() + vel.z;
        this.getWorld().addParticle(getParticleType(), d, e, f, 0.0, 0.0, 0.0);
    }
}
