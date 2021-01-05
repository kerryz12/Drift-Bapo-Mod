package com.driftbapo.driftbapomod.common.classes;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.server.ServerWorld;
import java.util.Random;

public class HenriqueUndo extends PickaxeItem {

    private Template undo_template;
    private BlockPos undo_blockPos;

    public HenriqueUndo(IItemTier itier, int ad, float atkspd, Properties builder, Template template, BlockPos blockPos) {
        super(itier, ad, atkspd, builder);
        undo_template = template;
        undo_blockPos = blockPos;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn) {
        if (!worldIn.isRemote) {
            ServerWorld serverworld = (ServerWorld) worldIn;

            PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).
                    setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos)null);

            if (undo_template != null) {
                BlockState blockState = serverworld.getBlockState(undo_blockPos);

                serverworld.notifyBlockUpdate(undo_blockPos, blockState, blockState, 3);

                undo_template.func_237144_a_(serverworld, undo_blockPos, placementsettings,
                        func_214074_b(serverworld.getSeed()));
            }
        }

        return ActionResult.resultPass(ItemStack.EMPTY);
    }

    // taken from StructureBlockTileEntity.java
    private static Random func_214074_b(long p_214074_0_) {
        return p_214074_0_ == 0L ? new Random(Util.milliTime()) : new Random(p_214074_0_);
    }
}
