package com.cursee.cw_religions;

import com.cursee.cw_religions.client.gui.screens.inventory.AltarScreen;
import com.cursee.cw_religions.core.data.ReligionsData;
import com.cursee.cw_religions.core.registry.ModMenus;
import com.cursee.cw_religions.platform.Services;
import com.cursee.cw_religions.window.ModSecondaryWindowRunnable;

public class CWReligionsClient {

    // todo impl config reading/writing
    public static boolean debug = true;

    public static ReligionsData religionsData;

    public static void init() {
        Services.PLATFORM.registerScreen(ModMenus.ALTAR, AltarScreen::new);

        if (debug) {
            var thread = new Thread(new ModSecondaryWindowRunnable(), "Mod Window Thread");
            thread.setDaemon(true);
            thread.start();

            ModSecondaryWindowRunnable.instance.log("Started secondary logging window for CW: Religions");
        }
    }
}
