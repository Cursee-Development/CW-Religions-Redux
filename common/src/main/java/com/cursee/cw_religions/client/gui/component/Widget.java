package com.cursee.cw_religions.client.gui.component;

import net.minecraft.client.gui.narration.NarrationElementOutput;

public abstract class Widget implements IWidget {

    public final int minX;
    public final int maxX;
    public final int minY;
    public final int maxY;

    public Widget(int minX, int maxX, int minY, int maxY) {
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return true;
    }

    @Override
    public void setFocused(boolean b) {}

    @Override
    public boolean isFocused() {
        return false;
    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {}
}
