package me.flashyreese.mods.sodiumextra.mixin.toasts;

import me.flashyreese.mods.sodiumextra.client.SodiumExtraClientMod;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SystemToast.class)
public class MixinSystemToast {
    @Shadow
    private Toast.Visibility wantedVisibility;

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void draw(ToastManager toastManager, long l, CallbackInfo ci) {
        if (!SodiumExtraClientMod.options().extraSettings.systemToast) {
            this.wantedVisibility = Toast.Visibility.HIDE;
            ci.cancel();
        }
    }
}
