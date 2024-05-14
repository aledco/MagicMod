package net.oberon.magic.entity.custom;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.oberon.magic.entity.ModEntities;
import net.oberon.magic.item.ModItems;
import net.oberon.magic.particle.ModParticles;

public class BasicMagicEntity extends ThrownItemEntity {
    private final static float DAMAGE = 4;
    private final static float SPEED = 0.8f;

    private int ticks = 0;

    public BasicMagicEntity(EntityType<? extends BasicMagicEntity> entityType, World world) {
        super(entityType, world);
    }

    public BasicMagicEntity(World world, LivingEntity owner) {
        super(ModEntities.BASIC_MAGIC, owner, world);
        this.setNoGravity(true);
        this.setPosition(owner.getPos().add(0, 1, 0));
        this.setVelocity(owner, owner.getPitch(), owner.getYaw(), -1.0f, SPEED, 1.0f);
    }

    public static BasicMagicEntity create(EntityType<? extends BasicMagicEntity> entityType, World world) {
        return new BasicMagicEntity(entityType, world);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BASIC_MAGIC;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (!this.getWorld().isClient) {
            var entity = entityHitResult.getEntity();
            if (entity.isAlive()) {
                entity.damage(this.getDamageSources().thrown(this, this.getOwner()), DAMAGE);
            }
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            var particleEffect = this.getParticleType(); // TODO get death sound to work and see what happens when particle velocity is turned up
            for (int i = 0; i < 64; ++i) {
                var velX = (this.random.nextFloat() - 0.5f) * 0.2f;
                var velY = (this.random.nextFloat() - 0.5f) * 0.2f;
                var velZ = (this.random.nextFloat() - 0.5f) * 0.2f;
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), velX, velY, velZ);
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.AMBIENT, 1.0f, 2.0f);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
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
        this.getWorld().addParticle(getParticleType(), d, e + 0.125, f, 0.0, 0.0, 0.0);
    }

    private ParticleEffect getParticleType() {
        return ModParticles.BASIC_MAGIC;
    }
}
