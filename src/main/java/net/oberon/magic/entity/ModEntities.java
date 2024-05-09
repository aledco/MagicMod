package net.oberon.magic.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.oberon.magic.MagicMod;
import net.oberon.magic.entity.custom.BasicMagicEntity;
import net.oberon.magic.entity.custom.FireMagicEntity;
import net.oberon.magic.entity.custom.HealingMagicEntity;

public class ModEntities {
    public final static int MAX_TICKS = 256;

    public static final EntityType<BasicMagicEntity> BASIC_MAGIC;
    public static final EntityType<FireMagicEntity> FIRE_MAGIC;
    public static final EntityType<HealingMagicEntity> HEALING_MAGIC;

    static {
        BASIC_MAGIC = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(MagicMod.MOD_ID, "basic_magic"),
                EntityType.Builder.create(BasicMagicEntity::create, SpawnGroup.MISC).build());

        FIRE_MAGIC = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(MagicMod.MOD_ID, "fire_magic"),
                EntityType.Builder.create(FireMagicEntity::create, SpawnGroup.MISC).build());

        HEALING_MAGIC = Registry.register(Registries.ENTITY_TYPE,
                new Identifier(MagicMod.MOD_ID, "dragon_magic"),
                EntityType.Builder.create(HealingMagicEntity::create, SpawnGroup.MISC).build());
    }

    public static void registerEntities() {
        MagicMod.LOGGER.info("Registering Entities for " + MagicMod.MOD_ID);
    }
}
