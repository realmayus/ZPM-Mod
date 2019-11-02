package mayus.zpmmod.integration;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import mayus.zpmmod.blockControllerLarge.TileControllerLarge;

import java.util.HashMap;

public class EnvironmentControllerLarge extends AbstractZPMEnvironment<TileControllerLarge> {

    public EnvironmentControllerLarge(TileControllerLarge tileEntity) {
        super("controllerLarge", tileEntity);
    }

    @Callback(doc = "function():table - Get information about the installed ZPMs in the controller.")
    public Object[] getZpmEnergy(final Context context, Arguments arguments) {
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
    public Object[] setEnabled(final Context context, Arguments arguments) {
        if(arguments.isBoolean(0)) {
            tile.isEnabled = arguments.checkBoolean(0);
        }

        return new Object[]{ true };
    }

    @Callback
    public Object[] setRedstoneBehaviour(final Context context, Arguments arguments) {
        if(arguments.isString(0)) {
            if(arguments.checkString(0).equalsIgnoreCase("ignore")) {
                tile.redstoneBehaviour = 0;
                return new Object[]{ true };
            } else if (arguments.checkString(0).equalsIgnoreCase("active_on_redstone")) {
                tile.redstoneBehaviour = 1;
                return new Object[]{ true };
            } else if(arguments.checkString(0).equalsIgnoreCase("not_active_on_redstone")) {
                tile.redstoneBehaviour = 2;
                return new Object[]{ true };
            } else {
                return new Object[]{ false };
            }
        } else {
            return new Object[]{ false };
        }


    }

    @Callback
    public Object[] getEnabled(final Context context, Arguments arguments) {
        return new Object[]{ tile.isEnabled };
    }

    @Callback
    public Object[] getRedstoneBehaviour(final Context context, Arguments arguments) {
        return new Object[] { getFriendlyRedstoneBehaviourNames(tile.redstoneBehaviour) };
    }

    @Callback
    public Object[] getMaxEnergy(final Context context, Arguments arguments) {
        return new Object[] { Integer.MAX_VALUE };
    }

    private String getFriendlyRedstoneBehaviourNames(int redstoneBehaviour) {
        switch (redstoneBehaviour) {
            case 0:
                return "ignore";
            case 1:
                return "active_on_redstone";
            case 2:
                return "not_active_on_redstone";
            default:
                return "ignore";
        }
    }
}
