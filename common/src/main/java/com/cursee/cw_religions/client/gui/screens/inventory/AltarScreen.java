package com.cursee.cw_religions.client.gui.screens.inventory;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.core.tag.PlayerTag;
import com.cursee.cw_religions.core.world.inventory.AltarMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class AltarScreen extends AbstractContainerScreen<AltarMenu> {

    private static final ResourceLocation ALTAR_LOCATION = CWReligions.identifier("textures/gui/container/altar.png");

    private final Player player;

    public AltarScreen(AltarMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.player = playerInventory.player;
        this.inventoryLabelY = -999;
        this.titleLabelY = -999;

        this.imageWidth = 256;
        this.imageHeight = 144;
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        Font font = Minecraft.getInstance().font;

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(ALTAR_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);

//        // todo remove
//        for (int II = 0; II < CWReligions.RELIGIONS.size(); II++) {
//            Religion religion = CWReligions.RELIGIONS.get(II);
//            guiGraphics.drawString(font, religion.symbol() + " " + religion.name(), 0, 10 + (10*II), 0xFFFFFFFF);
//        }

        boolean hasReligion = this.player.getTags().stream().anyMatch(string -> string.equalsIgnoreCase(PlayerTag.FOLLOWING_RELIGION));
        if (!hasReligion) {

            /// Buttons

            // 'Confess' darkened background
            guiGraphics.blit(ALTAR_LOCATION, i + 88, j + 18, 102, 190, 50, 20);
            // 'Sacrifice' darkened background
            guiGraphics.blit(ALTAR_LOCATION, i + 143, j + 18, 102, 190, 50, 20);
            // 'Pray' darkened background
            guiGraphics.blit(ALTAR_LOCATION, i + 198, j + 18, 102, 190, 50, 20);

            // 'Rules' darkened background
            guiGraphics.blit(ALTAR_LOCATION, i + 88, j + 63, 102, 190, 50, 20);
            // 'Relations' darkened background
            guiGraphics.blit(ALTAR_LOCATION, i + 143, j + 63, 102, 190, 50, 20);
            // 'Ranks' darkened background
            guiGraphics.blit(ALTAR_LOCATION, i + 198, j + 63, 102, 190, 50, 20);
        }

        if (hasReligion) {

            /// Display text

            guiGraphics.drawString(font, "Maximum Piety", i + 7, j + 7, 0xFFFF0000, false);
            guiGraphics.drawString(font, "Current Piety", i + 7, j + 20, 0xFF0000FF, false);
            guiGraphics.drawString(font, "Member Count", i + 7, j + 33, 0xFFFFFFFF, false);
            guiGraphics.drawString(font, "Priest Count", i + 7, j + 46, 0xFFFFFFFF, false);
        }

        guiGraphics.drawCenteredString(font, "Confess", i + 113, j + 24, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, "Sacrifice", i + 168, j + 24, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, "Pray", i + 223, j + 24, 0xFFFFFFFF);

        guiGraphics.drawCenteredString(font, "Rules", i + 113, j + 69, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, "Relations", i + 168, j + 69, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, "Ranks", i + 223, j + 69, 0xFFFFFFFF);

        guiGraphics.drawCenteredString(font, "View Religions", i + 58, j + 122, 0xFFFFFFFF);

        // guiGraphics.drawString(font, String.valueOf(mouseX), 0, 0, 0xFFFFFFFF);
        // guiGraphics.drawString(font, String.valueOf(mouseY), 0, 10, 0xFFFFFFFF);
        // guiGraphics.drawString(font, String.valueOf(withinBounds(mouseX, i + 8, i + 107)), 0, 20, 0xFFFFFFFF);
        // guiGraphics.drawString(font, String.valueOf(withinBounds(mouseY, j + 116, j + 135)), 0, 30, 0xFFFFFFFF);
        // guiGraphics.drawString(font, String.valueOf(withinBounds(mouseX, i + 8, i + 107) && withinBounds(mouseY, j + 116, j + 135)), 0, 40, 0xFFFFFFFF);

        if (withinBounds(mouseX, i + 8, i + 107) && withinBounds(mouseY, j + 116, j + 135)) {
            // System.out.println(true);
            guiGraphics.fill(i + 8, j + 116, i + 107, j + 135, 0x55DDDDFF);
        }
    }

    private static boolean withinBounds(int value, int lower, int upper) {
        return value >= lower && value <= upper;
    }
}
