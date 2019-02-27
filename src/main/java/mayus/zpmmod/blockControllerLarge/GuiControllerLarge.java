package mayus.zpmmod.blockControllerLarge;


import mayus.zpmmod.ZPMMod;
import mayus.zpmmod.networking.PacketHandler;
import mayus.zpmmod.networking.PacketSetEnabled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("Duplicates")
public class GuiControllerLarge extends GuiContainer {

    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;

    private TileControllerLarge controllerLarge;

    private ContainerControllerLarge container;

    private static final ResourceLocation GUI = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_large.png");

    private static final ResourceLocation GUI_ignore = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_large_ignore.png");

    private static final ResourceLocation GUI_no = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_large_no.png");

    private static final ResourceLocation GUIoff = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_large_off.png");

    private static final ResourceLocation GUIoff_ignore = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_large_off.png");

    private static final ResourceLocation GUIoff_no = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_large_off.png");

    public GuiControllerLarge(TileControllerLarge tileEntity, ContainerControllerLarge container) {
        super(container);

        this.container = container;
        xSize = WIDTH;
        ySize = HEIGHT;

        controllerLarge = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        //Setting color to white because JEI is bae (gui would be yellow)
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if(container.isEnabled) {
            if(container.redstoneBehaviour == 0) {
                mc.getTextureManager().bindTexture(GUI_ignore);
            } else if(container.redstoneBehaviour == 1) {
                mc.getTextureManager().bindTexture(GUI);
            } else {
                mc.getTextureManager().bindTexture(GUI_no);
            }
        } else {
            if(container.redstoneBehaviour == 0) {
                mc.getTextureManager().bindTexture(GUIoff_ignore);
            } else if(container.redstoneBehaviour == 1) {
                mc.getTextureManager().bindTexture(GUIoff);
            } else {
                mc.getTextureManager().bindTexture(GUIoff_no);
            }
        }
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);


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
                drawTooltip(mouseX, mouseY, new ArrayList<>(Collections.singletonList("Redstone Behaviour: Ignore")));
            } else if(container.redstoneBehaviour == 1) {
                drawTooltip(mouseX, mouseY, new ArrayList<>(Collections.singletonList("Redstone Behaviour: Active on Redstone")));
            } else {
                drawTooltip(mouseX, mouseY, new ArrayList<>(Collections.singletonList("Redstone Behaviour: Not Active on Redstone")));
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
                PacketHandler.INSTANCE.sendToServer(new PacketSetEnabled());

//                Incrementing here as well (because the packet does it (to keep it in sync with the packet))
                if(container.redstoneBehaviour != 2) {
                    container.redstoneBehaviour++;
                } else {
                    container.redstoneBehaviour = 0;
                }
            }
        }
    }
}
