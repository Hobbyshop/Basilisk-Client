package me.hobbyshop.basilisk.mixins.gui;

import me.hobbyshop.basilisk.events.RenderEvent2D;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public class MixinGuiIngame {

    @Inject(method = "renderTooltip", at = @At(value = "RETURN"))
    public void renderTooltip(ScaledResolution sr, float partialTicks, CallbackInfo ci) {
        new RenderEvent2D().call();
    }

}
