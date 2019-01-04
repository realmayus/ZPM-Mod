package mayus.zpmmod.ItemZPM;

import mayus.zpmmod.ZPMMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemZPM extends Item {

    public static final ResourceLocation ZPM = new ResourceLocation(ZPMMod.MODID, "zpm");

    public ItemZPM()
    {
        maxStackSize = 1;
        setCreativeTab(ZPMMod.creativeTab);
        setTranslationKey("zpm");
        setRegistryName(ZPM);
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {

        if(worldIn.isRemote) {
            playerIn.sendMessage(new TextComponentString("CLICK!"));
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

}
