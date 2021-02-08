package com.driftbapo.driftbapomod.common.classes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.Random;

public class CamdenResume extends SwordItem {

    public CamdenResume (IItemTier itier, int ad, float atkspd, Item.Properties builder) {
        super(itier, ad, atkspd, builder);
    }

    @Override
    public ActionResultType itemInteractionForEntity(ItemStack stack, PlayerEntity p, LivingEntity target, Hand handIn) {

        if (target.getEntity() instanceof VillagerEntity) {
            // get the target villager, create a new set of data with profession none, then set it to the target's data
            VillagerEntity villager = (VillagerEntity) target.getEntity();
            VillagerData villagerData = new VillagerData(villager.getVillagerData().getType(), VillagerProfession.NONE,
                    villager.getVillagerData().getLevel());
            villager.setVillagerData(villagerData);

            // play a sound
            ResourceLocation location = new ResourceLocation("minecraft", "block.anvil.land");
            SoundEvent event = new SoundEvent(location);
            target.playSound(event, 100, 0);

            // display some particles
            World worldIn = target.getEntityWorld();
            worldIn.addParticle(ParticleTypes.EXPLOSION_EMITTER, villager.getPosition().getX(),
                    villager.getPosition().getY(), villager.getPosition().getZ(), 1.0D, 0.0D, 0.0D);

        }

        return ActionResultType.SUCCESS;
    }

    // gives the hit entity a random potion effect with potency I-V and duration 5-10 seconds
    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        Random randomInt = new Random();
        EffectInstance effect = new EffectInstance(Effect.get(randomInt.nextInt(27) + 1),
                randomInt.nextInt(101) + 100, randomInt.nextInt(5) + 1);

        if (entity.isLiving()) {
            LivingEntity mob = (LivingEntity) entity;
            mob.addPotionEffect(effect);
        }

        // return false to continue normal damage calculation
        return false;
    }
}
