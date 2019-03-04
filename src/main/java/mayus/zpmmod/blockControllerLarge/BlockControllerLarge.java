package mayus.zpmmod.blockControllerLarge;



import mayus.zpmmod.ZPMMod;
import mayus.zpmmod.api.TOPInfoProvider;
import mayus.zpmmod.blockControllerSmall.TileControllerSmall;
import mayus.zpmmod.util.IGuiTile;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockControllerLarge extends Block implements ITileEntityProvider, TOPInfoProvider {


    //Creation of a so called "BlockState" for saving the direction the block is placed in
    public static final PropertyDirection FACING_HORIZ = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);


    //Creation of a constant for saving texture path and ingame name
    public static final ResourceLocation CONTROLLER_LARGE = new ResourceLocation(ZPMMod.MODID, "controller_large");


    /**
     * Constructor
     */
    public BlockControllerLarge() {

        //Sound the Block makes by harvesting or walking over it
        super(Material.IRON);

        //Setting the RegistryName to the constant 'CONTROLLER_SMALL'
        setRegistryName(CONTROLLER_LARGE);

        //Setting a 'translation key' for referring to it in a language file (for setting the localized name of the block)
        setTranslationKey(ZPMMod.MODID + ".controller_large");

        //Setting the level when the block can be harvested
        setHarvestLevel("pickaxe", 1);
        //Setting the default BlockState for the direction the block is placed in
        setDefaultState(blockState.getBaseState().withProperty(FACING_HORIZ, EnumFacing.NORTH));

        //Adding the block to the custom creative tab
        setCreativeTab(ZPMMod.creativeTab);

        setHardness(5.0F);
    }


    /**
     * Setting the ResourceLocation to the registry name (the ResourceLocation above)
     */
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileControllerLarge();
    }


    /**
     * EVENT that is called when you right-click the block,
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Only execute on the server
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof IGuiTile)) {

            return false;
        }
        if(!player.isSneaking()) {
            if(facing == EnumFacing.UP) {
                player.openGui(ZPMMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        } else {
            return false;
        }

        return true;
    }



    /**
     * Returning the BlockState for the direction so the Block actually shows the texture correctly.
     */
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING_HORIZ, placer.getHorizontalFacing().getOpposite());
    }


    /**
     * A couple of necessary methods for creating and getting the BlockState, nothing fancy here.
     */
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING_HORIZ);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING_HORIZ, EnumFacing.byIndex(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING_HORIZ).getIndex();
    }

    @Override
    public void addProbeInfo(ProbeMode mode, IProbeInfo probeInfo, EntityPlayer player, World world, IBlockState blockState, IProbeHitData data) {
        TileEntity te = world.getTileEntity(data.getPos());
        if (te instanceof TileControllerLarge) {
            // If we are sure that the entity there is correct we can proceed:
            TileControllerLarge tileControllerLarge = (TileControllerLarge) te;
            // First add a horizontal line showing the clock item followed by current contents of the counter in the tile entity
            if (((TileControllerLarge) te).isEnabled) probeInfo.horizontal()
                    .text(TextFormatting.GREEN + "Enabled");
            else probeInfo.horizontal()
                    .text(TextFormatting.RED + "Disabled");
            if(tileControllerLarge.doesContainZPM()) {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().borderColor(0xffff0000))
                        .text(tileControllerLarge.getZPMcount() + "/3 ZPMs installed")
                        .item(tileControllerLarge.inputHandler.getStackInSlot(0))
                        .item(tileControllerLarge.inputHandler.getStackInSlot(1))
                        .item(tileControllerLarge.inputHandler.getStackInSlot(2));
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);

        if(te instanceof TileControllerLarge) {
            if(((TileControllerLarge) te).doesContainZPM()) {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileControllerLarge) te).inputHandler.getStackInSlot(0));
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileControllerLarge) te).inputHandler.getStackInSlot(1));
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileControllerLarge) te).inputHandler.getStackInSlot(2));
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}
