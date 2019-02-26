package mayus.zpmmod.blockControllerLarge;


import mayus.zpmmod.ZPMMod;
import mayus.zpmmod.networking.PacketHandler;
import mayus.zpmmod.networking.PacketSetEnabled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

@SuppressWarnings("Duplicates")
public class GuiControllerLarge extends GuiContainer {

    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;

    private TileControllerLarge controllerLarge;

    private static final ResourceLocation GUI = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_large.png");

    private static final ResourceLocation GUIoff = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_large_off.png");

    public GuiControllerLarge(TileControllerLarge tileEntity, ContainerControllerLarge container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        controllerLarge = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        if(controllerLarge.enabled) {
            mc.getTextureManager().bindTexture(GUI);
        } else {
            mc.getTextureManager().bindTexture(GUIoff);
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
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(mouseButton == 0) {
            int xAxis = (mouseX - (width - xSize) / 2);
            int yAxis = (mouseY - (height - ySize) / 2);
            if(xAxis >= 148 && xAxis <= 167 && yAxis >= 56 && yAxis <= 75) {
                System.out.println("Click");
                Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                PacketHandler.INSTANCE.sendToServer(new PacketSetEnabled());
            }
        }
    }
}
