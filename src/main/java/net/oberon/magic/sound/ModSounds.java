package net.oberon.magic.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.oberon.magic.MagicMod;

public class ModSounds {
    public static final SoundEvent BASIC_MAGIC_CAST;

    static {
        BASIC_MAGIC_CAST = registerSoundEvent("basic_magic_cast");
    }

    @SuppressWarnings("SameParameterValue")
    private static SoundEvent registerSoundEvent(String name) {
        var id = new Identifier(MagicMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
    }
}
