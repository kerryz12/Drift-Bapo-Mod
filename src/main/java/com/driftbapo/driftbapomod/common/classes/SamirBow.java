package com.driftbapo.driftbapomod.common.classes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SamirBow extends BowItem {


    public SamirBow(Properties builder) {
        super(builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn){
        ItemStack itemStackIn = p.getHeldItem(handIn);
        ArrowEntity arrow = new ArrowEntity(worldIn, p);
        arrow.setMotion(p.getLookVec());
        arrow.setNoGravity(true);
        arrow.addEffect(new EffectInstance(Effect.get(2)));
        arrow.setShooter(p);
        arrow.setVelocity(p.getLookVec().x*4, p.getLookVec().y*4, p.getLookVec().z*4);
        worldIn.addEntity(arrow);
        return ActionResult.resultSuccess(itemStackIn);
    }

}
