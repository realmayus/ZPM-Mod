package realmayus.zpmmod.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import realmayus.zpmmod.blockControllerLarge.ContainerControllerLarge;
import realmayus.zpmmod.blockControllerLarge.GuiControllerLarge;
import realmayus.zpmmod.blockControllerLarge.TileControllerLarge;
import realmayus.zpmmod.blockControllerSmall.ContainerControllerSmall;
import realmayus.zpmmod.blockControllerSmall.GuiControllerSmall;
import realmayus.zpmmod.blockControllerSmall.TileControllerSmall;


public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileControllerSmall) {
            return new ContainerControllerSmall(player.inventory, (TileControllerSmall) te);
        } else if (te instanceof TileControllerLarge) {
            return new ContainerControllerLarge(player.inventory, (TileControllerLarge) te);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof TileControllerSmall) {
            return new GuiControllerSmall((TileControllerSmall) te, new ContainerControllerSmall(player.inventory, (TileControllerSmall) te));
        } else if (te instanceof TileControllerLarge) {
            return new GuiControllerLarge((TileControllerLarge) te, new ContainerControllerLarge(player.inventory, (TileControllerLarge) te));
        }
        return null;
    }
}