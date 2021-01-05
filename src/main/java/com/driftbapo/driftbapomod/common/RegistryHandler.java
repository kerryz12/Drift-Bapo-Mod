package com.driftbapo.driftbapomod.common;

import com.driftbapo.driftbapomod.common.classes.*;
import com.driftbapo.driftbapomod.common.items.CamdenItem;
import com.driftbapo.driftbapomod.common.items.HenriqueItem;
import com.driftbapo.driftbapomod.common.items.HenriqueUndoItem;
import com.driftbapo.driftbapomod.common.items.ZachItem;
import com.driftbapo.driftbapomod.common.tools.BrianItem;
import com.driftbapo.driftbapomod.common.tools.KerryItem;
import net.minecraft.item.*;
import com.driftbapo.driftbapomod.common.tools.SamirItem;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {
    // create DeferredRegister object
    public static final DeferredRegister<Item> BRIAN = DeferredRegister.create(ForgeRegistries.ITEMS, BrianItem.MODID);
    public static final DeferredRegister<Item> KERRY = DeferredRegister.create(ForgeRegistries.ITEMS, KerryItem.MODID);
    public static final DeferredRegister<Item> ZACH = DeferredRegister.create(ForgeRegistries.ITEMS, ZachItem.MODID);
    public static final DeferredRegister<Item> CAMDEN = DeferredRegister.create(ForgeRegistries.ITEMS, CamdenItem.MODID);
    public static final DeferredRegister<Item> HENRIQUE = DeferredRegister.create(ForgeRegistries.ITEMS, HenriqueItem.MODID);
    public static final DeferredRegister<Item> HENRIQUE_ = DeferredRegister.create(ForgeRegistries.ITEMS, SamirItem.MODID);
    public static final DeferredRegister<Item> SAMIR = DeferredRegister.create(ForgeRegistries.ITEMS, SamirItem.MODID);

    public static void init() {
        // attach DeferredRegister to the event bus
        BRIAN.register(FMLJavaModLoadingContext.get().getModEventBus());
        KERRY.register(FMLJavaModLoadingContext.get().getModEventBus());
        ZACH.register(FMLJavaModLoadingContext.get().getModEventBus());
        CAMDEN.register(FMLJavaModLoadingContext.get().getModEventBus());
        HENRIQUE.register(FMLJavaModLoadingContext.get().getModEventBus());
        HENRIQUE_.register(FMLJavaModLoadingContext.get().getModEventBus());
        SAMIR.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // register item
    // Brian
    public static final RegistryObject<AxeItem> BRIANAXE = BRIAN.register("brianaxe", () ->
            new BrianAxe(BrianItem.BRIANAXE, 10, 1.0F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    // Kerry
    public static final RegistryObject<SwordItem> KERRYSWORD = KERRY.register("kerrysword", () ->
            new SwordItem(KerryItem.KERRYSWORD, 4, 16, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    // Zach
    public static final RegistryObject<SwordItem> ZACHBATON = ZACH.register("zachbaton", () ->
            new ZachBaton(ZachItem.ZACHBATON, 1, 1, (new Item.Properties()).group(ItemGroup.MISC))
    );

    // Camden
    public static final RegistryObject<SwordItem> CAMDENRESUME = CAMDEN.register("camdenresume", () ->
            new CamdenResume(CamdenItem.CAMDENRESUME, 1, 1, (new Item.Properties()).group(ItemGroup.MISC))
    );

    // Henrique
    public static final RegistryObject<PickaxeItem> HENRIQUEHOUSE = HENRIQUE.register("henriquehouse", () ->
            new HenriqueHouse(HenriqueItem.HENRIQUEHOUSE, 1, 1, (new Item.Properties()).group(ItemGroup.MISC))
    );

    public static final RegistryObject<PickaxeItem> HENRIQUEUNDO = HENRIQUE_.register("henriqueundo", () ->
            new HenriqueUndo(HenriqueUndoItem.HENRIQUEUNDO, 1, 1, (new Item.Properties()).group(ItemGroup.MISC),
                    null, new BlockPos(0, 0, 0))
    );

    // Samir
    public static final RegistryObject<BowItem> SAMIRBOW = SAMIR.register("samirbow", () ->
        new SamirBow((new Item.Properties()).group(ItemGroup.MISC))
    );
}
