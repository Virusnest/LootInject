package me.virusnest.lootinject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InjectedLootData {
    public Map<Identifier, List<InjectData>> injectedTables = new HashMap<>();
    public static String INJECT_PATH = "loot_inject";
    public void LoadInjectedTables(ResourceManager manager) {
        manager.findResources(INJECT_PATH, path -> path.getPath().endsWith(".json")).forEach(this::LoadInjectedTable);
    }
    public void LoadInjectedTable(Identifier id,Resource resource){
        try {
            //load the json file to a LootTable Object

            BufferedReader reader = new BufferedReader(resource.getReader());
            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
            InjectData inject = InjectData.CODEC.parse(new Dynamic<>(JsonOps.INSTANCE, json)).result().get();
            for (RegistryKey<LootTable> key : inject.getInject()) {
                if(!injectedTables.containsKey(key.getValue())){
                    injectedTables.put(key.getValue(), new ArrayList());
                }
                injectedTables.get(key.getValue()).add(inject);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void Clear(){
        injectedTables.clear();
    }
}
