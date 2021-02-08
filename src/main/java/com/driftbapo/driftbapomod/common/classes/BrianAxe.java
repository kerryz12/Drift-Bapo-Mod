package com.driftbapo.driftbapomod.common.classes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;

public class BrianAxe extends AxeItem {

    private int counter;
    private static final int BLOCK_BREAK_LIMIT = 1000;

    public BrianAxe (IItemTier itier, int ad, float atkspd, Properties builder) {
        super(itier, ad, atkspd, builder);
        counter = 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn) {
        counter = 0;
        // get held item, and block player is looking at
        ItemStack itemStackIn = p.getHeldItem(handIn);
        BlockRayTraceResult rayTraceResult = this.rayTrace(worldIn, p, RayTraceContext.FluidMode.NONE);
        BlockPos origin = rayTraceResult.getPos();

        // call the recursive helper function
        helper_function(worldIn, p, handIn, origin);

        return ActionResult.resultPass(itemStackIn);
    }

    private void helper_function(World worldIn, PlayerEntity p, Hand handIn, BlockPos origin) {
        ItemStack itemStackIn = p.getHeldItem(handIn);

        // if isTree returns true, increment the counter. If the counter exceeds the limit, return
        // otherwise, destroy the block and call the function again at an adjacent block
        if (isTree(worldIn, origin)) {
            counter++;
            if (counter > BLOCK_BREAK_LIMIT) {
                return;
            }
            worldIn.destroyBlock(origin, true);
            helper_function(worldIn, p, handIn, origin.add(1, 0, 0));
            helper_function(worldIn, p, handIn, origin.add(-1, 0, 0));
            helper_function(worldIn, p, handIn, origin.add(0, 1, 0));
            helper_function(worldIn, p, handIn, origin.add(0, -1, 0));
            helper_function(worldIn, p, handIn, origin.add(0, 0, 1));
            helper_function(worldIn, p, handIn, origin.add(0, 0, -1));
        }

        return;
    }

    // returns true if the block at origin is a leaf or log block
    private boolean isTree(World worldIn, BlockPos origin) {
        ResourceLocation logsID = new ResourceLocation("minecraft", "logs");
        ResourceLocation leavesID = new ResourceLocation("minecraft", "leaves");

        return BlockTags.getCollection().getTagByID(logsID).contains(worldIn.getBlockState(origin).getBlock())
                || BlockTags.getCollection().getTagByID(leavesID).contains(worldIn.getBlockState(origin).getBlock());
    }

}
