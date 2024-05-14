package net.oberon.magic.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public class HealingMagicParticle extends AbstractMagicParticle {
    public HealingMagicParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, SpriteProvider spriteProvider) {
        super(world, x, y, z, velocityX, velocityY, velocityZ, spriteProvider);
    }

    @Override
    protected float getMinRed() {
        return 0;
    }

    @Override
    protected float getMinGreen() {
        return 0.8235294F;
    }

    @Override
    protected float getMinBlue() {
        return 0;
    }

    @Override
    protected float getMaxRed() {
        return 0;
    }

    @Override
    protected float getMaxGreen() {
        return 0.9764706F;
    }

    @Override
    protected float getMaxBlue() {
        return 0;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new HealingMagicParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
        }
    }
}
