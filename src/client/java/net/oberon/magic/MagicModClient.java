package net.oberon.magic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

import net.oberon.magic.entity.ModEntities;
import net.oberon.magic.entity.custom.LingeringMagicEntityRenderer;

public class MagicModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.BASIC_MAGIC, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.FIRE_MAGIC, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(ModEntities.HEALING_MAGIC, LingeringMagicEntityRenderer::new);
	}
}