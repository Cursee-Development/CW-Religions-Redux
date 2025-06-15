package com.cursee.cw_religions.mixin;

import com.cursee.cw_religions.CWReligionsClient;
import com.cursee.cw_religions.window.ModSecondaryWindowRunnable;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class ForgeTitleScreenMixin {

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {

        if (ModSecondaryWindowRunnable.instance == null && CWReligionsClient.debug) {
            var thread = new Thread(new ModSecondaryWindowRunnable(), "Mod Window Thread");
            thread.setDaemon(true);
            thread.start();

            ModSecondaryWindowRunnable.instance.log("Started secondary logging window for CW: Religions");
        }
    }
}