package net.oberon.magic.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import net.oberon.magic.entity.ModEntities;

public class FireMagicEntity extends FireballEntity {
    private final static float SPEED = 3f;
    private final static int MAX_TICKS = 256;

    private int ticks = 0;

    public FireMagicEntity(EntityType<? extends FireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public FireMagicEntity(World world, LivingEntity owner) {
        super(ModEntities.FIRE_MAGIC, world);
        this.setOwner(owner);
        this.setPosition(owner.getPos().add(0, 1, 0));
        this.setVelocity(owner, owner.getPitch(), owner.getYaw(), -1.0f, SPEED, 1.0f);
    }

    public static FireMagicEntity create(EntityType<? extends FireballEntity> entityType, World world) {
        return new FireMagicEntity(entityType, world);
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
