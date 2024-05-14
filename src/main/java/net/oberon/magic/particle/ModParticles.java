package net.oberon.magic.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.oberon.magic.MagicMod;

public class ModParticles {
    public static final DefaultParticleType BASIC_MAGIC = FabricParticleTypes.simple();
    public static final DefaultParticleType HEALING_MAGIC = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE,
                new Identifier(MagicMod.MOD_ID, "basic_magic"),
                BASIC_MAGIC);

        Registry.register(Registries.PARTICLE_TYPE,
                new Identifier(MagicMod.MOD_ID, "healing_magic"),
                HEALING_MAGIC);
    }
}
