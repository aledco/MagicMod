package net.oberon.magic.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public abstract class AbstractMagicParticle extends SpriteBillboardParticle {
    private boolean reachedGround;
    private final SpriteProvider spriteProvider;

    public AbstractMagicParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z);
        this.velocityMultiplier = 0.96F;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
        this.red = MathHelper.nextFloat(this.random, getMinRed(), getMaxRed());
        this.green = MathHelper.nextFloat(this.random, getMinGreen(), getMaxGreen());
        this.blue = MathHelper.nextFloat(this.random, getMinBlue(), getMaxBlue());
        this.scale *= 0.75F;
        this.maxAge = (int)(20.0 / ((double)this.random.nextFloat() * 0.8 + 0.2));
        this.reachedGround = false;
        this.collidesWithWorld = false;
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
    }

    protected abstract float getMinRed();
    protected abstract float getMinGreen();
    protected abstract float getMinBlue();
    protected abstract float getMaxRed();
    protected abstract float getMaxGreen();
    protected abstract float getMaxBlue();

    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
            if (this.onGround) {
                this.velocityY = 0.0;
                this.reachedGround = true;
            }

            if (this.reachedGround) {
                this.velocityY += 0.002;
            }

            this.move(this.velocityX, this.velocityY, this.velocityZ);
            if (this.y == this.prevPosY) {
                this.velocityX *= 1.1;
                this.velocityZ *= 1.1;
            }

            this.velocityX *= this.velocityMultiplier;
            this.velocityZ *= this.velocityMultiplier;
            if (this.reachedGround) {
                this.velocityY *= this.velocityMultiplier;
            }

        }
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    public float getSize(float tickDelta) {
        return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 32.0F, 0.0F, 1.0F);
    }
}
