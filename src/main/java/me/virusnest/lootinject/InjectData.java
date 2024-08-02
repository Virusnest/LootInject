package me.virusnest.lootinject;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class InjectData {
    public final RegistryKey<LootTable> table;
    public final RegistryKey<LootTable> inject;
    public static final Codec<InjectData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryKey.createCodec(RegistryKeys.LOOT_TABLE).fieldOf("table").forGetter(data -> RegistryKey.of(data.table.getRegistryRef(), data.table.getValue())),
            RegistryKey.createCodec(RegistryKeys.LOOT_TABLE).fieldOf("inject").forGetter(data -> RegistryKey.of(data.inject.getRegistryRef(), data.inject.getValue()))
    ).apply(instance, InjectData::new));
    public InjectData(RegistryKey<LootTable> table, RegistryKey<LootTable> inject) {
        this.table = table;
        this.inject = inject;
    }
}
