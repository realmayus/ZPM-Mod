package realmayus.zpmmod.integration;

import li.cil.oc.api.driver.DriverBlock;
import li.cil.oc.api.network.ManagedEnvironment;
import realmayus.zpmmod.blockControllerLarge.TileControllerLarge;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DriverController implements DriverBlock {
    @Override
    public boolean worksWith(World world, BlockPos blockPos, EnumFacing enumFacing) {
        TileEntity tile = world.getTileEntity(blockPos);
        if(tile != null) {
            return tile instanceof TileControllerLarge;
        } else {
            return false;
        }
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, BlockPos blockPos, EnumFacing enumFacing) {
        if(world.getTileEntity(blockPos) instanceof TileControllerLarge) {
            return new EnvironmentControllerLarge((TileControllerLarge)world.getTileEntity(blockPos));
        } else {
            return null;
        }
    }
}
