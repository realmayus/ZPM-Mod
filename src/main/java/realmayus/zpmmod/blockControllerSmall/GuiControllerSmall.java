package realmayus.zpmmod.blockControllerSmall;

import realmayus.zpmmod.ZPMMod;
import realmayus.zpmmod.network.PacketHandler;
import realmayus.zpmmod.network.PacketSetRedstoneBehaviour;
import realmayus.zpmmod.network.PacketToggleEnabled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GuiControllerSmall extends GuiContainer {

    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;

    private TileControllerSmall controllerSmall;

    private ContainerControllerSmall container;

    private static final ResourceLocation GUI = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_small.png");

    public GuiControllerSmall(TileControllerSmall tileEntity, ContainerControllerSmall container) {
        super(container);


        this.container = container;
        xSize = WIDTH;
        ySize = HEIGHT;

        controllerSmall = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //Setting color to white because JEI is bae (gui would be yellow)
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(GUI);

        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        if(container.isEnabled) {
            drawTexturedModalRect(guiLeft + 156, guiTop + 15, 176, 0, 5, 11);
        } else {
            drawTexturedModalRect(guiLeft + 156, guiTop + 15, 181, 0, 5, 11);
        }


        if(container.redstoneBehaviour == 0) {
            drawTexturedModalRect(guiLeft + 150, guiTop + 34, 176, 11, 16, 14);
        } else if(container.redstoneBehaviour == 1) {
            drawTexturedModalRect(guiLeft + 150, guiTop + 34, 176, 25, 16, 14);
        } else {
            drawTexturedModalRect(guiLeft + 150, guiTop + 34, 176, 39, 16, 14);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        //Render the dark background

        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        //Render any tooltips
        renderHoveredToolTip(mouseX, mouseY);

        int xAxis = (mouseX - (width - xSize) / 2);
        int yAxis = (mouseY - (height - ySize) / 2);
        if(xAxis >= 148 && xAxis <= 167 && yAxis >= 31 && yAxis <= 50) {
            if(container.redstoneBehaviour == 0) {
                drawTooltip(mouseX, mouseY, new ArrayList<>(Collections.singletonList(I18n.format("tile.zpmmod.tooltip.ignore_signal"))));
            } else if(container.redstoneBehaviour == 1) {
                drawTooltip(mouseX, mouseY, new ArrayList<>(Collections.singletonList(I18n.format("tile.zpmmod.tooltip.active_on_signal"))));
            } else {
                drawTooltip(mouseX, mouseY, new ArrayList<>(Collections.singletonList(I18n.format("tile.zpmmod.tooltip.inactive_on_signal"))));
            }
        } else if(xAxis >= 148 && xAxis <= 167 && yAxis >= 7 && yAxis <= 26) {
            if(container.isEnabled) {
                drawTooltip(mouseX, mouseY, new ArrayList<>(Collections.singletonList(I18n.format("tile.zpmmod.tooltip.disable"))));
            } else {
                drawTooltip(mouseX, mouseY, new ArrayList<>(Collections.singletonList(I18n.format("tile.zpmmod.tooltip.enable"))));
            }
        }
    }

    public void drawTooltip(int x, int y, List<String> tooltips)
    {
        drawHoveringText(tooltips, x, y, fontRenderer);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(mouseButton == 0) {
            int xAxis = (mouseX - (width - xSize) / 2);
            int yAxis = (mouseY - (height - ySize) / 2);
            if(xAxis >= 148 && xAxis <= 167 && yAxis >= 31 && yAxis <= 50) {

                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                PacketHandler.INSTANCE.sendToServer(new PacketSetRedstoneBehaviour());

//                Incrementing here as well (because the packet does it (to keep it in sync with the packet))
                if(container.redstoneBehaviour != 2) {
                    container.redstoneBehaviour++;
                } else {
                    container.redstoneBehaviour = 0;
                }
            } else if(xAxis >= 148 && xAxis <= 167 && yAxis >= 7 && yAxis <= 26) {
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                PacketHandler.INSTANCE.sendToServer(new PacketToggleEnabled());
            }
        }
    }
}
