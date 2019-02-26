package mayus.zpmmod.networking;

import io.netty.buffer.ByteBuf;
import mayus.zpmmod.blockControllerLarge.ContainerControllerLarge;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketGetEnabled implements IMessage {


    private BlockPos tepos;
    // A default constructor is always required
    public PacketGetEnabled(){}

    public PacketGetEnabled(BlockPos tePos){
        this.tepos = tePos;
    }

    @Override public void toBytes(ByteBuf buf) {

    }

    @Override public void fromBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketSetEnabled, IMessage> {

        @Override public IMessage onMessage(PacketSetEnabled message, MessageContext ctx) {
            // This is the player the packet was sent to the server from
            EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
            if(serverPlayer.openContainer instanceof ContainerControllerLarge) {
                ContainerControllerLarge openContainer = (ContainerControllerLarge)serverPlayer.openContainer;
                System.out.println(openContainer.te.enabled);
                openContainer.te.enabled = !openContainer.te.enabled;
                openContainer.te.markDirty();
                System.out.println("Packet received! Tile Entity at position " + openContainer.te.getPos());
                System.out.println(openContainer.te.enabled);
            }

            // No response packet
            return null;
        }
    }
}
