package me.virusnest.lootinject;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;

public class InjectData {
    private final RegistryKey<LootTable> table;
    private final List<RegistryKey<LootTable>> inject;

    public List<RegistryKey<LootTable>> getInject() {
        return inject;
    }

    public RegistryKey<LootTable> getTable() {
        return table;
    }

    public static final Codec<InjectData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryKey.createCodec(RegistryKeys.LOOT_TABLE).fieldOf("table").forGetter(InjectData::getTable),
            RegistryKey.createCodec(RegistryKeys.LOOT_TABLE).listOf().fieldOf("inject").forGetter(InjectData::getInject)
    ).apply(instance, InjectData::new));
    public InjectData(RegistryKey<LootTable> table, List<RegistryKey<LootTable>> inject) {
        this.table = table;
        this.inject = inject;
    }

}
