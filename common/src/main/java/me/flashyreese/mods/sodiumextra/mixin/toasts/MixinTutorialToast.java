package me.flashyreese.mods.sodiumextra.mixin.toasts;

import me.flashyreese.mods.sodiumextra.client.SodiumExtraClientMod;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.gui.components.toasts.TutorialToast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TutorialToast.class)
public class MixinTutorialToast {
    @Shadow
    private Toast.Visibility visibility;

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    public void draw(ToastManager toastManager, long l, CallbackInfo ci) {
        if (!SodiumExtraClientMod.options().extraSettings.advancementToast) {
            this.visibility = Toast.Visibility.HIDE;
            ci.cancel();
        }
    }
}
