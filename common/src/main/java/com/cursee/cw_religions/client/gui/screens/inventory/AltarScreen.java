package com.cursee.cw_religions.client.gui.screens.inventory;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.client.gui.component.IWidget;
import com.cursee.cw_religions.client.gui.component.Widget;
import com.cursee.cw_religions.core.tag.PlayerTag;
import com.cursee.cw_religions.core.world.inventory.AltarMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class AltarScreen extends AbstractContainerScreen<AltarMenu> {

    public static final ResourceLocation ALTAR_LOCATION = CWReligions.identifier("textures/gui/container/altar.png");

    int imageLowerX;
    int imageLowerY;
    private final Player player;

    public int imageWidthX;
    public int imageHeightY;

    public AltarScreen(AltarMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.player = playerInventory.player;
        this.inventoryLabelY = -999;
        this.titleLabelY = -999;

        this.imageWidth = 256;
        this.imageHeight = 144;
        this.imageWidthX = imageWidth;
        this.imageHeightY = imageHeight;
    }

    @Override
    protected void init() {
        super.init();
        // this.addRenderableWidget();
        // this.addRenderableWidget(new ImageButton(this.width / 2 - 124, l + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (p_280830_) -> this.minecraft.setScreen(new LanguageSelectScreen(this, this.minecraft.options, this.minecraft.getLanguageManager())), Component.translatable("narrator.button.language")));
        this.addWidget(new ViewReligionsButton(this, imageLowerX + 8, imageLowerY + 116, imageLowerX + 107, imageLowerY + 135));
    }

    public static class ViewReligionsButton extends Widget {

        private final AltarScreen screenReference;

        public ViewReligionsButton(AltarScreen screen, int minX, int maxX, int minY, int maxY) {
            super(minX, maxX, minY, maxY);
            this.screenReference = screen;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            boolean withinBounds = withinBounds((int) mouseX, screenReference.imageLowerX + 8, screenReference.imageLowerX + 107) && withinBounds((int) mouseY, screenReference.imageLowerY + 116, screenReference.imageLowerY + 135);
            System.out.println(withinBounds);
            if (withinBounds) {
                Minecraft.getInstance().setScreen(new AltarViewReligionsSubScreen(screenReference, Component.literal("View Religions")));
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }
    }

    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {

        Font font = Minecraft.getInstance().font;

        imageLowerX = (this.width - this.imageWidth) / 2;
        imageLowerY = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(ALTAR_LOCATION, imageLowerX, imageLowerY, 0, 0, this.imageWidth, this.imageHeight);

//        // todo remove
//        for (int II = 0; II < CWReligions.RELIGIONS.size(); II++) {
//            Religion religion = CWReligions.RELIGIONS.get(II);
//            guiGraphics.drawString(font, religion.symbol() + " " + religion.name(), 0, 10 + (10*II), 0xFFFFFFFF);
//        }

        boolean hasReligion = this.player.getTags().stream().anyMatch(string -> string.equalsIgnoreCase(PlayerTag.FOLLOWING_RELIGION));
        if (!hasReligion) {

            /// Buttons

            // 'Confess' darkened background
            guiGraphics.blit(ALTAR_LOCATION, imageLowerX + 88, imageLowerY + 18, 102, 190, 50, 20);
            // 'Sacrifice' darkened background
            guiGraphics.blit(ALTAR_LOCATION, imageLowerX + 143, imageLowerY + 18, 102, 190, 50, 20);
            // 'Pray' darkened background
            guiGraphics.blit(ALTAR_LOCATION, imageLowerX + 198, imageLowerY + 18, 102, 190, 50, 20);

            // 'Rules' darkened background
            guiGraphics.blit(ALTAR_LOCATION, imageLowerX + 88, imageLowerY + 63, 102, 190, 50, 20);
            // 'Relations' darkened background
            guiGraphics.blit(ALTAR_LOCATION, imageLowerX + 143, imageLowerY + 63, 102, 190, 50, 20);
            // 'Ranks' darkened background
            guiGraphics.blit(ALTAR_LOCATION, imageLowerX + 198, imageLowerY + 63, 102, 190, 50, 20);
        }

        if (hasReligion) {

            /// Display text

            guiGraphics.drawString(font, "Maximum Piety", imageLowerX + 7, imageLowerY + 7, 0xFFFF0000, false);
            guiGraphics.drawString(font, "Current Piety", imageLowerX + 7, imageLowerY + 20, 0xFF0000FF, false);
            guiGraphics.drawString(font, "Member Count", imageLowerX + 7, imageLowerY + 33, 0xFFFFFFFF, false);
            guiGraphics.drawString(font, "Priest Count", imageLowerX + 7, imageLowerY + 46, 0xFFFFFFFF, false);
        }

        guiGraphics.drawCenteredString(font, "Confess", imageLowerX + 113, imageLowerY + 24, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, "Sacrifice", imageLowerX + 168, imageLowerY + 24, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, "Pray", imageLowerX + 223, imageLowerY + 24, 0xFFFFFFFF);

        guiGraphics.drawCenteredString(font, "Rules", imageLowerX + 113, imageLowerY + 69, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, "Relations", imageLowerX + 168, imageLowerY + 69, 0xFFFFFFFF);
        guiGraphics.drawCenteredString(font, "Ranks", imageLowerX + 223, imageLowerY + 69, 0xFFFFFFFF);

        guiGraphics.drawCenteredString(font, "View Religions", imageLowerX + 58, imageLowerY + 122, 0xFFFFFFFF);

        // guiGraphics.drawString(font, String.valueOf(mouseX), 0, 0, 0xFFFFFFFF);
        // guiGraphics.drawString(font, String.valueOf(mouseY), 0, 10, 0xFFFFFFFF);
        // guiGraphics.drawString(font, String.valueOf(withinBounds(mouseX, i + 8, i + 107)), 0, 20, 0xFFFFFFFF);
        // guiGraphics.drawString(font, String.valueOf(withinBounds(mouseY, j + 116, j + 135)), 0, 30, 0xFFFFFFFF);
        // guiGraphics.drawString(font, String.valueOf(withinBounds(mouseX, i + 8, i + 107) && withinBounds(mouseY, j + 116, j + 135)), 0, 40, 0xFFFFFFFF);

        if (withinBounds(mouseX, imageLowerX + 8, imageLowerX + 107) && withinBounds(mouseY, imageLowerY + 116, imageLowerY + 135)) {
            // System.out.println(true);
            guiGraphics.fill(imageLowerX + 8, imageLowerY + 116, imageLowerX + 107, imageLowerY + 135, 0x55DDDDFF);
        }
    }

    private static boolean withinBounds(int value, int lower, int upper) {
        return value >= lower && value <= upper;
    }
}
