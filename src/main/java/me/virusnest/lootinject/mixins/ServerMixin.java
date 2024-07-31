package me.virusnest.lootinject.mixins;

import me.virusnest.lootinject.InjectedLootData;
import me.virusnest.lootinject.Lootinject;
import net.minecraft.resource.DataConfiguration;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public abstract class ServerMixin {



    @Shadow public abstract ResourceManager getResourceManager();

    @Inject(method="Lnet/minecraft/server/MinecraftServer;loadDataPacks(Lnet/minecraft/resource/ResourcePackManager;Lnet/minecraft/resource/DataConfiguration;ZZ)Lnet/minecraft/resource/DataConfiguration;", at=@At("HEAD"))
    private static void load(ResourcePackManager resourcePackManager, DataConfiguration dataConfiguration, boolean initMode, boolean safeMode, CallbackInfoReturnable<DataConfiguration> cir){
    }
}
