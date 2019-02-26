package mayus.zpmmod.networking;

import mayus.zpmmod.ZPMMod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
    public static SimpleNetworkWrapper INSTANCE;

    private static int ID = 0;
    private static int nextID() {return ID++;}

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(ZPMMod.MODID);

        INSTANCE.registerMessage(PacketSetEnabled.Handler.class, PacketSetEnabled.class, nextID(), Side.SERVER);
    }

}
