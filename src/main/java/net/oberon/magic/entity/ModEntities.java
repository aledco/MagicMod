package net.oberon.magic.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.oberon.magic.MagicMod;
import net.oberon.magic.entity.custom.BasicMagicEntity;
import net.oberon.magic.entity.custom.FireMagicEntity;

public class ModEntities {
    public static final EntityType<BasicMagicEntity> BASIC_MAGIC = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MagicMod.MOD_ID, "basic_magic"),
            EntityType.Builder.create(BasicMagicEntity::create, SpawnGroup.MISC).build());

    public static final EntityType<FireMagicEntity> FIRE_MAGIC = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(MagicMod.MOD_ID, "fire_magic"),
            EntityType.Builder.create(FireMagicEntity::create, SpawnGroup.MISC).build());

    public static void registerEntities() {
        MagicMod.LOGGER.info("Registering Entities for " + MagicMod.MOD_ID);
    }
}
