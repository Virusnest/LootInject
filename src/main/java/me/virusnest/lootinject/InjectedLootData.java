package me.virusnest.lootinject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.loot.v2.FabricLootTableBuilder;
import net.fabricmc.fabric.impl.resource.loader.FabricLifecycledResourceManager;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.ReloadableRegistries;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class InjectedLootData {
    public Map<Identifier, LootTable> injectedTables = new HashMap<>();
    public static String INJECT_PATH = "loot_inject";
    public void LoadInjectedTables(ResourceManager manager) {
        manager.findResources(INJECT_PATH, path -> path.getPath().endsWith(".json")).forEach(this::LoadInjectedTable);
    }
    public void LoadInjectedTable(Identifier id,Resource resource){

        //remove json and injected_loot_table from the path only from the end and start
        id = Identifier.of(id.getNamespace(), id.getPath().substring(INJECT_PATH.length() + 1, id.getPath().length() - 5));

        Lootinject.LOGGER.info(id.toString());
        try {
            //load the json file to a LootTable Object

            BufferedReader reader = new BufferedReader(resource.getReader());
            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
            LootTable table = LootTable.CODEC.parse(new Dynamic<>(JsonOps.INSTANCE, json)).result().get();
            injectedTables.put(id, table);
            // Do something with the table
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Clear(){
        injectedTables.clear();
    }
}
