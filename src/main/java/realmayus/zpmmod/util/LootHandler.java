package realmayus.zpmmod.util;

import realmayus.zpmmod.ZPMMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class LootHandler {


    @SubscribeEvent
    public void lootLoad(LootTableLoadEvent evt) {
        String prefix = "minecraft:chests/";
        String name = evt.getName().toString();

        if (name.startsWith(prefix)) {
            String file = name.substring(name.indexOf(prefix) + prefix.length());
            switch (file) {
                case "abandoned_mineshaft":
                case "desert_pyramid":
                case "jungle_temple":
                case "simple_dungeon": evt.getTable().addPool(getInjectPool(file)); break;
                case "spawn_bonus_chest": evt.getTable().addPool(getInjectPool(file)); break;
                case "stronghold_corridor": evt.getTable().addPool(getInjectPool(file)); break;
                case "village_blacksmith":
                default: break;
            }
        }
    }

    private LootPool getInjectPool(String entryName) {
        return new LootPool(new LootEntry[] { getInjectEntry(entryName, 100) }, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(8, 9), "zpmmod_inject_pool");
    }

    private LootEntryTable getInjectEntry(String name, int weight) {
        return new LootEntryTable(new ResourceLocation(ZPMMod.MODID, "inject/" + name), weight, 0, new LootCondition[0], "zpmmod_inject_entry");
    }

}