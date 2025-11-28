package com.cursee.disenchanting_table.mixin.client;

import com.cursee.disenchanting_table.DisEnchantingTable;
import com.cursee.disenchanting_table.platform.Services;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

  @Inject(at = @At("TAIL"), method = "<init>")
  private void init(CallbackInfo info) {

    if (Services.PLATFORM.isDevelopmentEnvironment()) {
      DisEnchantingTable.LOG.info("This line is printed by an example mixin from Common!");
      DisEnchantingTable.LOG.info("MC Version: {}", Minecraft.getInstance().getVersionType());
    }
  }
}