package me.virusnest.lootinject.mixins;

import com.google.gson.JsonElement;
import me.virusnest.lootinject.Lootinject;
import net.minecraft.loot.LootDataType;
import net.minecraft.registry.*;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(ReloadableRegistries.class)
public class ReloadableMixin {
    @Inject(method = "Lnet/minecraft/registry/ReloadableRegistries;prepare(Lnet/minecraft/loot/LootDataType;Lnet/minecraft/registry/RegistryOps;Lnet/minecraft/resource/ResourceManager;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;", at = @At("HEAD"))
    private static <T> void load(LootDataType<T> type, RegistryOps<JsonElement> ops, ResourceManager resourceManager, Executor prepareExecutor, CallbackInfoReturnable<CompletableFuture<MutableRegistry<?>>> cir){
        Lootinject.data.Clear();
        Lootinject.data.LoadInjectedTables(resourceManager);
    }

}
