package mayus.zpmmod.networking;

import io.netty.buffer.ByteBuf;
import mayus.zpmmod.blockControllerLarge.ContainerControllerLarge;
import mayus.zpmmod.blockControllerSmall.ContainerControllerSmall;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketToggleEnabled implements IMessage {

    // A default constructor is always required
    public PacketToggleEnabled(){}

    @Override public void toBytes(ByteBuf buf) {}

    @Override public void fromBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketToggleEnabled, IMessage> {

        @Override public IMessage onMessage(PacketToggleEnabled message, MessageContext ctx) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                if (serverPlayer.openContainer instanceof ContainerControllerLarge) {
                    ContainerControllerLarge openContainer = (ContainerControllerLarge) serverPlayer.openContainer;
                    openContainer.te.isEnabled = !openContainer.te.isEnabled;
                } else if (serverPlayer.openContainer instanceof ContainerControllerSmall) {
                    ContainerControllerSmall openContainer = (ContainerControllerSmall) serverPlayer.openContainer;
                    openContainer.te.isEnabled = !openContainer.te.isEnabled;
                }
            });
            // No response packet
            return null;
        }
    }
    
}
