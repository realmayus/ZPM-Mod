package mayus.zpmmod.util;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import mayus.zpmmod.blockControllerLarge.TileControllerLarge;
import mayus.zpmmod.blockControllerSmall.TileControllerSmall;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;

public class EnvironmentControllerLarge extends AbstractZPMEnvironment<TileControllerLarge> {

    public EnvironmentControllerLarge(TileControllerLarge tileEntity) {
        super("controllerLarge", tileEntity);
    }

    @Callback(doc = "function():table - Get information about the installed ZPMs in the controller.")
    public Object[] getZpmEnergy(final Context context, Arguments arguments)
    {
        return new Object[]{new HashMap<Integer, Object>() {
            {
                put(1, tile.getEnergyOfZPM(0));
                put(2, tile.getEnergyOfZPM(1));
                put(3, tile.getEnergyOfZPM(2));
            }

        }
        };
    }

    @Callback
    public void setEnabled(final Context context, Arguments arguments){

    }

}
