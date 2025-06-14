package com.cursee.cw_religions;

import net.minecraft.resources.ResourceLocation;

public class CWReligions {

    public static void init() {}

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(Constants.MOD_ID, path);
    }
}