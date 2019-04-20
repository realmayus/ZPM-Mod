package mayus.zpmmod.proxy;

import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.ListenableFuture;
import li.cil.oc.api.Driver;
import mayus.zpmmod.ModBlocks;
import mayus.zpmmod.ModItems;
import mayus.zpmmod.ZPMMod;
import mayus.zpmmod.api.MainCompatHandler;
import mayus.zpmmod.networking.PacketHandler;
import mayus.zpmmod.util.DriverController;
import mayus.zpmmod.util.LootHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.animation.ITimeValue;
import net.minecraftforge.common.model.animation.IAnimationStateMachine;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        PacketHandler.registerMessages();
        MainCompatHandler.registerTOP();

        MinecraftForge.EVENT_BUS.register(new LootHandler());
        LootTableList.register(new ResourceLocation(ZPMMod.MODID, "inject/simple_dungeon"));
        LootTableList.register(new ResourceLocation(ZPMMod.MODID, "inject/stronghold_corridor"));
        LootTableList.register(new ResourceLocation(ZPMMod.MODID, "inject/spawn_bonus_chest"));
        LootTableList.register(new ResourceLocation(ZPMMod.MODID, "inject/desert_pyramid"));

    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(ZPMMod.instance, new GuiHandler());
        Driver.add(new DriverController());
    }

    public void postInit(FMLPostInitializationEvent e) {

    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        ModBlocks.register(event.getRegistry());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        ModItems.register(event.getRegistry());
    }

    @Nullable
    public IAnimationStateMachine load(ResourceLocation location, ImmutableMap<String, ITimeValue> parameters) {
        return null;
    }


    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }

    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }
}
