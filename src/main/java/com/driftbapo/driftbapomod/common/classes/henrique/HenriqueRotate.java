package com.driftbapo.driftbapomod.common.classes.henrique;

import com.driftbapo.driftbapomod.common.items.henrique.HenriqueRotateItem;
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

public class HenriqueRotate extends PickaxeItem {

    private Template undoTemplate;
    private BlockPos rotate_blockpos;
    private Rotation prevRotation;
    private Template originalTemplate;

    public HenriqueRotate(IItemTier itier, int ad, float atkspd, Properties builder, Template original,
                          Template undo, BlockPos blockPos, Rotation prev) {
        super(itier, ad, atkspd, builder);
        undoTemplate = undo;
        rotate_blockpos = blockPos;
        prevRotation = prev;
        originalTemplate = original;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn) {
        if (!worldIn.isRemote) {
            ServerWorld serverworld = (ServerWorld) worldIn;
            Rotation rotation = nextRotation(prevRotation);
            BlockPos newBlockpos = nextBlockpos(prevRotation);

            PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).
                    setRotation(rotation).setIgnoreEntities(false).setChunk((ChunkPos)null);

            PlacementSettings defaultsettings = (new PlacementSettings()).setMirror(Mirror.NONE).
                    setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos)null);

            if ((originalTemplate != null) && (rotation != null)) {
                BlockState blockState = serverworld.getBlockState(newBlockpos);

                serverworld.notifyBlockUpdate(newBlockpos, blockState, blockState, 3);

                originalTemplate.func_237144_a_(serverworld, newBlockpos, placementsettings,
                        func_214074_b(serverworld.getSeed()));

                ItemStack henriqueRotate = new HenriqueRotate(HenriqueRotateItem.HENRIQUEROTATE, 1, 1,
                        (new Item.Properties()).group(ItemGroup.MISC), originalTemplate, undoTemplate, rotate_blockpos,
                        rotation).getDefaultInstance();

                return ActionResult.resultPass(henriqueRotate);
            }

            else {
                BlockState blockState = serverworld.getBlockState(rotate_blockpos);

                serverworld.notifyBlockUpdate(rotate_blockpos, blockState, blockState, 3);

                undoTemplate.func_237144_a_(serverworld, rotate_blockpos, defaultsettings,
                        func_214074_b(serverworld.getSeed()));

                return ActionResult.resultPass(ItemStack.EMPTY);
            }
        }

        return ActionResult.resultPass(ItemStack.EMPTY);

    }

    // taken from StructureBlockTileEntity.java
    private static Random func_214074_b(long p_214074_0_) {
        return p_214074_0_ == 0L ? new Random(Util.milliTime()) : new Random(p_214074_0_);
    }

    private Rotation nextRotation(Rotation prevRotation) {
        Rotation nextRotation = Rotation.NONE;

        switch (prevRotation) {
            case NONE:
                nextRotation = Rotation.CLOCKWISE_90;
                break;
            case CLOCKWISE_90:
                nextRotation = Rotation.CLOCKWISE_180;
                break;
            case CLOCKWISE_180:
                nextRotation = Rotation.COUNTERCLOCKWISE_90;
                break;
            case COUNTERCLOCKWISE_90:
                nextRotation = null;
                break;
            default:
                break;
        }

        return nextRotation;
    }

    private BlockPos nextBlockpos(Rotation prevRotation) {
        BlockPos next = rotate_blockpos;

        switch (prevRotation) {
            case NONE:
                next = next.add(33,0,0);
                break;
            case CLOCKWISE_90:
                next = next.add(33,0,33);
                break;
            case CLOCKWISE_180:
                next = next.add(0,0,33);
                break;
            default:
                break;
        }

        return next;
    }
}
