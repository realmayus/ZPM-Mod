package mayus.zpmmod.networking;

import io.netty.buffer.ByteBuf;
import mayus.zpmmod.blockControllerLarge.ContainerControllerLarge;
import mayus.zpmmod.blockControllerSmall.ContainerControllerSmall;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSetRedstoneBehaviour implements IMessage {

    // A default constructor is always required
    public PacketSetRedstoneBehaviour(){}

    @Override public void toBytes(ByteBuf buf) {}

    @Override public void fromBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketSetRedstoneBehaviour, IMessage> {

        @Override public IMessage onMessage(PacketSetRedstoneBehaviour message, MessageContext ctx) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            serverPlayer.getServerWorld().addScheduledTask(() -> {
                if (serverPlayer.openContainer instanceof ContainerControllerLarge) {
                    ContainerControllerLarge openContainer = (ContainerControllerLarge) serverPlayer.openContainer;
                    if (openContainer.redstoneBehaviour == 0) {
                        openContainer.te.redstoneBehaviour = 1;
                    } else if (openContainer.redstoneBehaviour == 1) {
                        openContainer.te.redstoneBehaviour = 2;
                    } else {
                        openContainer.te.redstoneBehaviour = 0;
                    }
                    openContainer.te.markDirty();
                } else if (serverPlayer.openContainer instanceof ContainerControllerSmall) {
                    ContainerControllerSmall openContainer = (ContainerControllerSmall) serverPlayer.openContainer;
                    if (openContainer.redstoneBehaviour == 0) {
                        openContainer.te.redstoneBehaviour = 1;
                    } else if (openContainer.redstoneBehaviour == 1) {
                        openContainer.te.redstoneBehaviour = 2;
                    } else {
                        openContainer.te.redstoneBehaviour = 0;
                    }
                    openContainer.te.markDirty();
                }
            });
            // No response packet
            return null;
        }
    }
}
