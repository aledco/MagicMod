package net.oberon.magic.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.oberon.magic.item.ModToolMaterials;

public abstract class AbstractWandItem extends ToolItem {
    private long timeOfLastUse = 0;

    public AbstractWandItem(Settings settings) {
        super(ModToolMaterials.WAND, settings);
    }

    abstract int timeBetweenUses();

    abstract int useDamage();

    abstract Entity magicEntity(World world, PlayerEntity user);

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
        var entity = magicEntity(world, user);
        world.spawnEntity(entity);

        // play cast sound
        world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.AMBIENT, 1.0f, 0.5f);

        // damage
        stack.damage(useDamage(), user, p -> p.sendToolBreakStatus(user.getActiveHand()));
        return TypedActionResult.success(stack);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        stack.damage(2, miner, p -> p.sendToolBreakStatus(miner.getActiveHand()));
        return true;
    }
}
