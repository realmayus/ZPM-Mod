package mayus.zpmmod.blockControllerSmall;


import mayus.zpmmod.ZPMMod;
import mayus.zpmmod.util.IGuiTile;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockControllerSmall extends Block implements ITileEntityProvider {


    //Creation of a so called "BlockState" for saving the direction the block is placed in
    public static final PropertyDirection FACING = PropertyDirection.create("facing");


    //Save the combinations of the equipped items as an Integer (for the textures:
    // 1 | empty
    // 2 | depleted
    // 3 | full
    public static final PropertyInteger TEXTURE = PropertyInteger.create("texture", 1, 3);

    //Creation of a constant for saving texture path and ingame name
    public static final ResourceLocation CONTROLLER_SMALL = new ResourceLocation(ZPMMod.MODID, "controller_small");


    /**
     * Constructor
     */
    public BlockControllerSmall() {

        //Sound the Block makes by harvesting or walking over it
        super(Material.IRON);

        //Setting the RegistryName to the constant 'CONTROLLER_SMALL'
        setRegistryName(CONTROLLER_SMALL);

        //Setting a 'translation key' for referring to it in a language file (for setting the localized name of the block)
        setTranslationKey(ZPMMod.MODID + ".controller_small");

        //Setting the level when the block can be harvested
        setHarvestLevel("pickaxe", 1);

        //Setting the default BlockState for the direction the block is placed in
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        //Adding the block to the custom creative tab
        setCreativeTab(ZPMMod.creativeTab);
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
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }


    /**
     * A couple of necessary methods for creating and getting the BlockState, nothing fancy here.
     */
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta & 7));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }


}
