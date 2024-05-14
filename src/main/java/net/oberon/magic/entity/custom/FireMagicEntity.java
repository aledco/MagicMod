package net.oberon.magic.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.oberon.magic.entity.ModEntities;

public class FireMagicEntity extends FireballEntity {
    private int ticks = 0;

    public FireMagicEntity(EntityType<? extends FireballEntity> entityType, World world) {
        super(entityType, world);
    }

    private static final float HALF = (float)Math.PI / 180;

    private static float getVelocityX(LivingEntity owner) {
        return -MathHelper.sin(owner.getYaw() * HALF) *
                MathHelper.cos(owner.getPitch() * HALF);
    }

    private static float getVelocityY(LivingEntity owner) {
        return -MathHelper.sin((owner.getPitch() - 1.0f) * HALF);
    }

    private static float getVelocityZ(LivingEntity owner) {
        return MathHelper.cos(owner.getYaw() * HALF) *
                MathHelper.cos(owner.getPitch() * HALF);
    }

    public FireMagicEntity(World world, LivingEntity owner, int explosivePower) {
        super(world,
                owner,
                getVelocityX(owner),
                getVelocityY(owner),
                getVelocityZ(owner),
                explosivePower);
        this.setPosition(this.getPos().add(0, 1, 0));
    }

    public static FireMagicEntity create(EntityType<? extends FireballEntity> entityType, World world) {
        return new FireMagicEntity(entityType, world);
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
