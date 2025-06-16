package com.cursee.cw_religions.client.gui.screens.inventory;

import com.cursee.cw_religions.CWReligions;
import com.cursee.cw_religions.CWReligionsClient;
import com.cursee.cw_religions.client.gui.component.Widget;
import com.cursee.cw_religions.client.gui.screens.AbstractSubScreen;
import com.cursee.cw_religions.core.data.struct.Religion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class AltarViewReligionsSubScreen extends AbstractSubScreen {

    public static final ResourceLocation ALTAR_VIEW_RELIGIONS_LOCATION = CWReligions.identifier("textures/gui/container/altar_view_religions.png");

    private final AltarScreen reference;

    public AltarViewReligionsSubScreen(AltarScreen lastScreen, Component title) {
        super(lastScreen, title);
        reference = lastScreen;
    }

    private static boolean withinBounds(int value, int lower, int upper) {
        return value >= lower && value <= upper;
    }

    @Override
    protected void init() {
        super.init();

        List<Religion> religions = CWReligionsClient.religionsData.getAllReligions().stream().toList();
        for (int i = 0; i < religions.size(); i++) {
            Religion religion = religions.get(i);

            this.addWidget(new Widget(51, 251, 5 + (20 * i), 25 + (20 * i)) {

                @Override
                public boolean mouseClicked(double mouseX, double mouseY, int button) {
                    return super.mouseClicked(mouseX, mouseY, button);
                }
            });
        }

        this.addWidget(new Widget(reference.imageLowerX + 5, reference.imageLowerX + 44, reference.imageLowerY + 126, reference.imageLowerY + 138) {
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {

                if (withinBounds((int) mouseX, this.minX, this.maxX) && withinBounds((int) mouseY, this.minY, this.maxY)) {
                    AltarViewReligionsSubScreen.this.onClose();
                }

                return super.mouseClicked(mouseX, mouseY, button);
            }
        });
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        Font font = Minecraft.getInstance().font;

//        int imageWidth = 256;
//        int imageHeight = 144;

//        reference.imageLowerX = (this.width - imageWidth) / 2;
//        reference.imageLowerY = (this.height - imageHeight) / 2;

        guiGraphics.blit(ALTAR_VIEW_RELIGIONS_LOCATION, reference.imageLowerX, reference.imageLowerY, 0, 0, reference.imageWidthX, reference.imageHeightY);

        guiGraphics.drawCenteredString(font, "<", reference.imageLowerX + 24, reference.imageLowerY + 129, 0xFFFFFFFF);

        List<Religion> religions = CWReligionsClient.religionsData.getAllReligions().stream().toList();
        for (int i = 0; i < religions.size(); i++) {

            // draw each button's background
            guiGraphics.blit(ALTAR_VIEW_RELIGIONS_LOCATION, reference.imageLowerX + 51, reference.imageLowerY + 5 + (20 * i), 0, 168, 200, 20);

            // draw each button's text
            Religion religion = religions.get(i);
            guiGraphics.drawString(font, String.valueOf(religion.symbol() + ", " + religion.name()), reference.imageLowerX + 53, reference.imageLowerY + 11 + (20 * i), 0xFFFFFFFF);

            if (withinBounds(mouseX, reference.imageLowerX + 51, reference.imageLowerX + 251) && withinBounds(mouseY, reference.imageLowerY + 5 + (20 * i), reference.imageLowerY + 5 + (20 * i) + 20)) {
                guiGraphics.fill(reference.imageLowerX + 51, reference.imageLowerY + 5 + (20 * i), reference.imageLowerX + 251, reference.imageLowerY + 5 + (20 * i) + 20, 0x55DDDDFF);
            }
        }

        if (withinBounds(mouseX, reference.imageLowerX + 5, reference.imageLowerX + 44) && withinBounds(mouseY, reference.imageLowerY + 126, reference.imageLowerY + 138)) {
            guiGraphics.fill(reference.imageLowerX + 5, reference.imageLowerY + 126, reference.imageLowerX + 44, reference.imageLowerY + 138, 0x55DDDDFF);
        }
    }

    class ReligionSelectionList extends ObjectSelectionList<ReligionSelectionList.Entry> {

        public ReligionSelectionList(AltarViewReligionsSubScreen screen, Minecraft minecraft) {
            super(minecraft, screen.width, screen.height, 32, screen.height - 61, 18);
        }

        public class Entry extends ObjectSelectionList.Entry<Entry> {
            @Override
            public Component getNarration() {
                return Component.literal("");
            }

            @Override
            public void render(GuiGraphics guiGraphics, int i, int i1, int i2, int i3, int i4, int i5, int i6, boolean b, float v) {

            }
        }
    }
}
