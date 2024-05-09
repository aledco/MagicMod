package net.oberon.magic.item.custom;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.oberon.magic.enchantment.custom.AbstractMagicEnchantment;
import net.oberon.magic.entity.custom.BasicMagicEntity;
import net.oberon.magic.item.ModToolMaterials;

public class WandItem extends ToolItem {
    private long timeOfLastUse = 0;

    public WandItem(Settings settings) {
        super(ModToolMaterials.WAND, settings);
    }

    protected int timeBetweenUses() {
        return 20;
    }

    protected int useDamage() {
        return 1;
    }

    protected Entity magicEntity(World world, PlayerEntity user, ItemStack stack) {
        if (stack.hasEnchantments()) {
            var enchantment = EnchantmentHelper
                    .fromNbt(stack.getEnchantments())
                    .keySet()
                    .stream()
                    .filter(e -> e instanceof AbstractMagicEnchantment)
                    .map(e -> (AbstractMagicEnchantment) e)
                    .findFirst();
            if (enchantment.isPresent()) {
                return enchantment.get().magicEntity(world, user);
            }
        }

        return new BasicMagicEntity(world, user);
    }

    private long useTime() {
        if (timeOfLastUse == 0) {
            return 0;
        } else {
            return timeOfLastUse + timeBetweenUses();
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        var time = world.getTime();
        if (world.isClient() || useTime() > time) {
            return TypedActionResult.fail(stack);
        }

        // update time
        timeOfLastUse = time;

        // spawn magic
        var entity = magicEntity(world, user, stack);
        world.spawnEntity(entity);

        // play cast sound
        world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.AMBIENT, 1.0f, 0.5f);

        // damage
        stack.damage(useDamage(), user, p -> p.sendToolBreakStatus(user.getActiveHand()));
        return TypedActionResult.success(stack);
    }
}
