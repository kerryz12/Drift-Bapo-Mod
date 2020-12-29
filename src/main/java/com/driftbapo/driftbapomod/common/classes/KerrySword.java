package com.driftbapo.driftbapomod.common.classes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class KerrySword extends SwordItem {

    public KerrySword(IItemTier itier, int ad, float atkspd, Properties builder){
        super(itier, ad, atkspd, builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn){
        ItemStack itemStackIn = p.getHeldItem(handIn);
        return ActionResult.resultFail(itemStackIn);
    }

}
