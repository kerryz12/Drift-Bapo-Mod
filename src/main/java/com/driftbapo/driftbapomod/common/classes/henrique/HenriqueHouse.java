package com.driftbapo.driftbapomod.common.classes.henrique;

import com.driftbapo.driftbapomod.common.driftbapomod;
import com.driftbapo.driftbapomod.common.items.henrique.HenriqueRotateItem;
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

    private Template undoTemplate = new Template();
    private BlockPos rotateBlockpos = new BlockPos(0, 0, 0);
    private Template originalTemplate = new Template();

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
            ResourceLocation resourceLocation = new ResourceLocation(driftbapomod.MODID, "house1");
            Template template = templateManager.getTemplate(resourceLocation);

            PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).
                    setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos)null);

            if (template != null) {
                BlockState blockState = serverworld.getBlockState(origin);
                BlockPos offset = origin.add(-16, -1, -16);
                BlockPos size = BlockPos.ZERO.add(33,256,33);

                serverworld.notifyBlockUpdate(offset, blockState, blockState, 3);

                undoTemplate.takeBlocksFromWorld(worldIn, offset, size, false, null);
                rotateBlockpos = offset;
                originalTemplate = template;
                template.func_237144_a_(serverworld, offset, placementsettings, func_214074_b(serverworld.getSeed()));
            }
        }

        ItemStack henriqueRotate = new HenriqueRotate(HenriqueRotateItem.HENRIQUEROTATE, 1, 1,
                (new Item.Properties()).group(ItemGroup.MISC), originalTemplate, undoTemplate, rotateBlockpos,
                Rotation.NONE).getDefaultInstance();

        return ActionResult.resultPass(henriqueRotate);
    }

    // taken from StructureBlockTileEntity.java
    private static Random func_214074_b(long p_214074_0_) {
        return p_214074_0_ == 0L ? new Random(Util.milliTime()) : new Random(p_214074_0_);
    }

    private String housePicker() {
        return null;
    }
}
