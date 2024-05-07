package net.oberon.magic.entity.custom;

import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.oberon.magic.entity.ModEntities;
import net.oberon.magic.item.ModItems;

public class BasicMagicEntity extends ThrownItemEntity {
    private final static float DAMAGE = 4;
    private final static float SPEED = 0.8f;
    private final static int MAX_TICKS = 256;

    private int ticks = 0;

    public BasicMagicEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.setNoGravity(true);
    }

    public BasicMagicEntity(World world, LivingEntity owner) {
        super(ModEntities.BASIC_MAGIC, owner, world);
        this.setNoGravity(true);
        this.setPosition(owner.getPos().add(0, 1, 0));
        this.setVelocity(owner, owner.getPitch(), owner.getYaw(), -1.0f, SPEED, 1.0f);
    }

    public static BasicMagicEntity create(EntityType<? extends ThrownItemEntity> entityType, World world) {
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

    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? ParticleTypes.SPLASH : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ENDER_EYE_DEATH, SoundCategory.AMBIENT, 3.0f, 1.0f);
            ParticleEffect particleEffect = this.getParticleParameters(); // TODO get death sound to work and see what happens when particle velocity is turned up
            for (int i = 0; i < 32; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.ticks++;
        if (this.ticks >= MAX_TICKS && !this.getWorld().isClient) {
            this.discard();
        }
    }
}
