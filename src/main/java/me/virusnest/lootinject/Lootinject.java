package me.virusnest.lootinject;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.fabric.mixin.loot.LootTableAccessor;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.DynamicRegistriesProvider;
import net.minecraft.data.server.loottable.LootTableGenerator;
import net.minecraft.data.server.loottable.LootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.AnyOfLootCondition;
import net.minecraft.loot.entry.DynamicEntry;
import net.minecraft.loot.entry.GroupEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootTableEntry;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.resource.*;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Interface;

import java.io.InputStream;

public class Lootinject implements ModInitializer {

    public static final String MOD_ID = "lootinject";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static InjectedLootData data = new InjectedLootData();
    @Override
    public void onInitialize() {
        LOGGER.info("Initalised");
        LootTableEvents.MODIFY.register((table, builder, source, wrapper) -> {
            // Let's only modify built-in loot tables and leave data pack loot tables untouched by checking the source.
            // We also check that the loot table ID is equal to the ID we want.
            if (data.injectedTables.containsKey(table.getValue()) &&source.isBuiltin()) {
                // We make the pool inject table from data
                LootPool poolBuilder = LootPool.builder().with(LootTableEntry.builder(data.injectedTables.get(table.getValue()))).build();

                builder.pool(poolBuilder);
            }
        });
    }

}
