package net.oberon.magic.item.custom;

import net.minecraft.enchantment.Enchantment;
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

import java.util.Optional;

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

    private EnchantmentResult getMagicEnchantment(ItemStack stack) {
        if (stack.hasEnchantments()) {
            var map = EnchantmentHelper.fromNbt(stack.getEnchantments());
            var enchantment = map
                    .keySet()
                    .stream()
                    .filter(e -> e instanceof AbstractMagicEnchantment)
                    .map(e -> (AbstractMagicEnchantment) e)
                    .findFirst();
            if (enchantment.isPresent()) {
                return new EnchantmentResult(enchantment.get(), map.get(enchantment.get()));
            }
        }

        return new EnchantmentResult();
    }

    private Entity magicEntity(World world, PlayerEntity user, EnchantmentResult enchantmentResult) {
        if (enchantmentResult.containsEnchantment) {
            return enchantmentResult.enchantment.magicEntity(world, user, enchantmentResult.level);
        } else {
            return new BasicMagicEntity(world, user);
        }
    }

    private long useTime(EnchantmentResult enchantmentResult) {
        if (timeOfLastUse == 0) {
            return 0;
        } else {
            if (enchantmentResult.containsEnchantment) {
                return timeOfLastUse + enchantmentResult.enchantment.reloadTime();
            } else {
                return timeOfLastUse + timeBetweenUses();
            }
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var stack = user.getStackInHand(hand);
        var time = world.getTime();
        var enchantmentResult = this.getMagicEnchantment(stack);
        if (world.isClient() || this.useTime(enchantmentResult) > time) {
            return TypedActionResult.fail(stack);
        }

        // update time
        this.timeOfLastUse = time;

        // spawn magic
        var entity = magicEntity(world, user, enchantmentResult);
        world.spawnEntity(entity);

        // play cast sound
        world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.AMBIENT, 1.0f, 0.3f);

        // damage
        stack.damage(useDamage(), user, p -> p.sendToolBreakStatus(user.getActiveHand()));
        return TypedActionResult.success(stack);
    }

    private static class EnchantmentResult {
        public boolean containsEnchantment;
        public AbstractMagicEnchantment enchantment;
        public int level;

        public EnchantmentResult(AbstractMagicEnchantment enchantment, int level) {
            this.containsEnchantment = true;
            this.enchantment = enchantment;
            this.level = level;
        }

        public EnchantmentResult() {
            this.containsEnchantment = false;
        }
    }
}
