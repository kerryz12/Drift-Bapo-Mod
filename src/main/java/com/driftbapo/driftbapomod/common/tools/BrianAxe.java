package com.driftbapo.driftbapomod.common.tools;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.util.ActionResultType;

public class BrianAxe extends AxeItem {

    public BrianAxe (IItemTier itier, int ad, float atkspd, Properties builder){
        super(itier, ad, atkspd, builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn){
        double x = p.getPosX();
        double y = p.getPosY();
        double z = p.getPosZ();

        ItemStack itemStackIn = p.getHeldItem(handIn);
        BlockRayTraceResult rayTraceResult = this.rayTrace(worldIn, p, RayTraceContext.FluidMode.NONE);
        BlockPos origin = rayTraceResult.getPos();

        if (worldIn.getBlockState(origin).getBlock().equals(Blocks.STRIPPED_OAK_LOG) || worldIn.getBlockState(origin).getBlock().equals(Blocks.BIRCH_LOG)
        || worldIn.getBlockState(origin).getBlock().equals(Blocks.ACACIA_LOG) || worldIn.getBlockState(origin).getBlock().equals(Blocks.JUNGLE_LOG)) {
            worldIn.destroyBlock(origin, true);

        }

        return ActionResult.resultPass(itemStackIn);

    }

}
