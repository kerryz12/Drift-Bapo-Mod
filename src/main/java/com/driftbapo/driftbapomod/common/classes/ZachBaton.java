package com.driftbapo.driftbapomod.common.classes;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import java.util.Random;

import java.util.Arrays;
import java.util.List;

public class ZachBaton extends SwordItem {

    public ZachBaton (IItemTier itier, int ad, float atkspd, Item.Properties builder) {
        super(itier, ad, atkspd, builder);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity p, Hand handIn) {
        ItemStack itemStackIn = p.getHeldItem(handIn);
        BlockRayTraceResult rayTraceResult = this.rayTrace(worldIn, p, RayTraceContext.FluidMode.NONE);
        BlockPos origin = rayTraceResult.getPos();
        List<String> states = Arrays.asList("instrument", "note");

        ResourceLocation location = new ResourceLocation("minecraft", randomSound());
        SoundEvent event = new SoundEvent(location);
        Random randomInt = new Random();

        if (worldIn.getBlockState(origin).getBlock().equals(Blocks.NOTE_BLOCK)) {
            BlockState state = worldIn.getBlockState(origin);
            states.set(0, state.get(BlockStateProperties.NOTE_BLOCK_INSTRUMENT).toString());
            states.set(1, state.get(BlockStateProperties.NOTE_0_24).toString());

            if (!worldIn.isRemote) {
                p.sendMessage(new TranslationTextComponent(states.get(0)), p.getUniqueID());
                p.sendMessage(new TranslationTextComponent(numToNote(states.get(1))), p.getUniqueID());
            }
        }

        else {
            worldIn.playSound(p, origin, event, SoundCategory.PLAYERS, 100, randomInt.nextInt(25));
        }

        return ActionResult.resultPass(itemStackIn);
    }

    private String numToNote(String num) {
        int numInt = Integer.parseInt(num);
        List<String> note_list = Arrays.asList("F#/Gb (Octave 1)", "G (Octave 1)", "G#/Ab (Octave 1)", "A (Octave 1)",
                "A#/Bb (Octave 1)", "B (Octave 1)", "C (Octave 1)", "C#/Db (Octave 1)", "D (Octave 1)",
                "D#/Eb (Octave 1)", "E (Octave 1)", "F (Octave 1)", "F#/Gb (Octave 2)", "G (Octave 2)",
                "G#/Ab (Octave 2)", "A (Octave 2)", "A#/Bb (Octave 2)", "B (Octave 2)", "C (Octave 2)",
                "C#/Db (Octave 2)", "D (Octave 2)", "D#/Eb (Octave 2)", "E (Octave 2)", "F (Octave 2)",
                "F#/Gb (Octave 3)");

        return note_list.get(numInt);
    }

    private String randomSound() {
        Random rand = new Random();
        int randInt = rand.nextInt(16);
        List<String> noteblockType = Arrays.asList("block.note_block.banjo", "block.note_block.basedrum",
                "block.note_block.bass", "block.note_block.bell", "block.note_block.bit", "block.note_block.chime",
                "block.note_block.cow_bell", "block.note_block.didgeridoo", "block.note_block.flute",
                "block.note_block.guitar", "block.note_block.harp", "block.note_block.hat",
                "block.note_block.iron_xylophone", "block.note_block.pling", "block.note_block.snare",
                "block.note_block.xylophone");

        return noteblockType.get(randInt);
    }
}
