package com.driftbapo.driftbapomod.common.classes;

import com.driftbapo.driftbapomod.common.driftbapomod;
import jdk.jfr.internal.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeWorldServer;
import java.util.Random;
import net.minecraft.block.StructureBlock;

public class HenriqueHouse extends PickaxeItem {

    public HenriqueHouse (IItemTier itier, int ad, float atkspd, Properties builder) {
        super(itier, ad, atkspd, builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn) {
        ItemStack itemStackIn = p.getHeldItem(handIn);
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
                BlockPos offset = origin.add(-17, -1, -17);

                serverworld.notifyBlockUpdate(origin, blockState, blockState, 3);

                template.func_237144_a_(serverworld, offset, placementsettings, func_214074_b(serverworld.getSeed()));
            }
        }

        return ActionResult.resultPass(itemStackIn);
    }

    // taken from StructureBlockTileEntity.java
    private static Random func_214074_b(long p_214074_0_) {
        return p_214074_0_ == 0L ? new Random(Util.milliTime()) : new Random(p_214074_0_);
    }
}
