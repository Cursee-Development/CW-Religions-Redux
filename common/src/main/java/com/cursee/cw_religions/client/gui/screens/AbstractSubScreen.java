package com.cursee.cw_religions.client.gui.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AbstractSubScreen extends Screen {

    protected final Screen lastScreen;

    public AbstractSubScreen(Screen lastScreen, Component title) {
        super(title);
        this.lastScreen = lastScreen;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.lastScreen);
    }

    protected void basicListRender(GuiGraphics guiGraphics, OptionsList optionsList, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        optionsList.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
