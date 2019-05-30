package mayus.zpmmod.util;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import mayus.zpmmod.blockControllerLarge.TileControllerLarge;
import mayus.zpmmod.blockControllerSmall.TileControllerSmall;

import java.util.HashMap;

public class EnvironmentControllerSmall extends AbstractZPMEnvironment<TileControllerSmall> {

    public EnvironmentControllerSmall(TileControllerSmall tileEntity) {
        super("controllerSmall", tileEntity);
    }

    @Callback(doc = "function():int - Get information about the installed ZPM in the controller.")
    public Object[] getZpmEnergy(final Context context, Arguments arguments)
    {
        return new Object[]{tile.getEnergyOfZPM()};
    }

}
