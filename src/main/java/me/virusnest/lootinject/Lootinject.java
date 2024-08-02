package me.virusnest.lootinject;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.LootTableEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                for (InjectData injectTable : data.injectedTables.get(table.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder();
                    poolBuilder.with(LootTableEntry.builder(injectTable.table));
                    builder.pool(poolBuilder);
                }
            }
        });
    }

}
