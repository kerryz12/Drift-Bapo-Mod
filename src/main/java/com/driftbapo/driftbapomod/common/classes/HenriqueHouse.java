package com.driftbapo.driftbapomod.common.classes;

import com.driftbapo.driftbapomod.common.driftbapomod;
import com.driftbapo.driftbapomod.common.items.HenriqueUndoItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import java.util.Random;

public class HenriqueHouse extends PickaxeItem {

    private Template undo_template = new Template();
    private BlockPos undo_blockpos = new BlockPos(0, 0, 0);

    public HenriqueHouse (IItemTier itier, int ad, float atkspd, Properties builder) {
        super(itier, ad, atkspd, builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn) {
        BlockRayTraceResult rayTraceResult = this.rayTrace(worldIn, p, RayTraceContext.FluidMode.NONE);
        BlockPos origin = rayTraceResult.getPos();

        if (!worldIn.isRemote) {
            ServerWorld serverworld = (ServerWorld) worldIn;
            TemplateManager templateManager = serverworld.getStructureTemplateManager();
            ResourceLocation resourceLocation = new ResourceLocation(driftbapomod.MODID, "test");
            Template template = templateManager.getTemplate(resourceLocation);

            PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).
                    setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos)null);

            if (template != null) {
                BlockState blockState = serverworld.getBlockState(origin);
                BlockPos offset = origin.add(-16, -1, -16);
                BlockPos size = BlockPos.ZERO.add(33,256,33);

                serverworld.notifyBlockUpdate(offset, blockState, blockState, 3);

                undo_template.takeBlocksFromWorld(worldIn, offset, size, false, null);
                undo_blockpos = offset;
                template.func_237144_a_(serverworld, offset, placementsettings, func_214074_b(serverworld.getSeed()));
            }
        }

        ItemStack henriqueUndo = new HenriqueUndo(HenriqueUndoItem.HENRIQUEUNDO, 1, 1, (new Item.Properties()).group(ItemGroup.MISC),
                undo_template, undo_blockpos).getDefaultInstance();

        return ActionResult.resultPass(henriqueUndo);
    }

    // taken from StructureBlockTileEntity.java
    private static Random func_214074_b(long p_214074_0_) {
        return p_214074_0_ == 0L ? new Random(Util.milliTime()) : new Random(p_214074_0_);
    }
}
