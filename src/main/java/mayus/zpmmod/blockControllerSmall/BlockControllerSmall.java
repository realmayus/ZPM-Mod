package mayus.zpmmod.blockControllerSmall;


import mayus.zpmmod.ZPMMod;
import mayus.zpmmod.integration.TOPInfoProvider;
import mayus.zpmmod.util.IGuiTile;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.ProbeMode;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockControllerSmall extends Block implements TOPInfoProvider {


    //Creation of a so called "BlockState" for saving the direction the block is placed in
    public static final PropertyDirection FACING_HORIZ = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);


    //Creation of a constant for saving texture path and ingame name
    public static final ResourceLocation CONTROLLER_SMALL = new ResourceLocation(ZPMMod.MODID, "controller_small");


    /**
     * Constructor
     */
    public BlockControllerSmall() {

        //Sound the Block makes by harvesting or walking over it
        super(Material.IRON);

        //Setting a 'translation key' for referring to it in a language file (for setting the localized name of the block)
        setTranslationKey(ZPMMod.MODID + ".controller_small");

        //Setting the level when the block can be harvested
        setHarvestLevel("pickaxe", 1);

        //Setting the default BlockState for the direction the block is placed in
        setDefaultState(blockState.getBaseState().withProperty(FACING_HORIZ, EnumFacing.NORTH));


        setHardness(5.0F);
    }


    /**
     * Setting the ResourceLocation to the registry name (the ResourceLocation above)
     */
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileControllerSmall();
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
        if (te instanceof TileControllerSmall) {
            // If we are sure that the entity there is correct we can proceed:
            TileControllerSmall tileControllerSmall = (TileControllerSmall) te;
            // First add a horizontal line showing the clock item followed by current contents of the counter in the tile entity
            if (tileControllerSmall.isEnabled) probeInfo.horizontal()
                    .text(TextFormatting.GREEN + "Enabled");
            else probeInfo.horizontal()
                    .text(TextFormatting.RED + "Disabled");
            if(!tileControllerSmall.inputHandler.getStackInSlot(0).isEmpty()) {
                probeInfo.horizontal(probeInfo.defaultLayoutStyle().borderColor(0xffff0000))
                        .text("1/1 ZPMs installed")
                        .item(tileControllerSmall.inputHandler.getStackInSlot(0));
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);

        if(te instanceof TileControllerSmall) {
            if(((TileControllerSmall) te).doesContainZPM()) {
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), ((TileControllerSmall) te).inputHandler.getStackInSlot(0));
            }
        }
        super.breakBlock(worldIn, pos, state);
    }
}
