package mayus.zpmmod.BlockControllerSmall;

import mayus.zpmmod.ZPMMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiControllerSmall extends GuiContainer {

    public static final int WIDTH = 175;
    public static final int HEIGHT = 165;

    private TileControllerSmall controllerSmall;

    private static final ResourceLocation GUI = new ResourceLocation(ZPMMod.MODID, "textures/gui/zpminterface_small.png");

    public GuiControllerSmall(TileControllerSmall tileEntity, ContainerControllerSmall container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        controllerSmall = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(GUI);
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
}
