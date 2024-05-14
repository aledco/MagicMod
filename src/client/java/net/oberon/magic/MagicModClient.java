package net.oberon.magic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

import net.oberon.magic.entity.ModEntities;
import net.oberon.magic.particle.ModParticles;
import net.oberon.magic.particle.custom.BasicMagicParticle;
import net.oberon.magic.particle.custom.HealingMagicParticle;

public class MagicModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.BASIC_MAGIC, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.FIRE_MAGIC, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.HEALING_MAGIC, FlyingItemEntityRenderer::new);

		ParticleFactoryRegistry.getInstance().register(ModParticles.HEALING_MAGIC, HealingMagicParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(ModParticles.BASIC_MAGIC, BasicMagicParticle.Factory::new);
	}
}