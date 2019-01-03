package mayus.zpmmod.lightblock;

import mayus.zpmmod.ZPMMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;



public class BlockLightBlock extends Block{

    public static final PropertyInteger LIGHT_LEVEL = PropertyInteger.create("lightlevel", 0, 15);

    public static final ResourceLocation LIGHT_BLOCK = new ResourceLocation(ZPMMod.MODID, "zpmmod");



    public BlockLightBlock() {
        super(Material.IRON);
        setCreativeTab(ZPMMod.creativeTab);
        setTranslationKey("zpmmod");
        setHardness(5);
        setHarvestLevel("pickaxe", 2);
        setRegistryName(LIGHT_BLOCK);
        setDefaultState(this.getBlockState().getBaseState().withProperty(LIGHT_LEVEL, 0));
    }


    /**
     * RIGHT CLICK
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        if(!player.isSneaking()) {
            //set LightLevel of block according to IProperty named LIGHT_LEVEL
            world.setBlockState(pos, state.withProperty(LIGHT_LEVEL, (state.getValue(LIGHT_LEVEL) + 1) % 16));
            player.sendMessage(new TextComponentString("Current Light level: " + state.getValue(LIGHT_LEVEL)));
        }
        return true;
    }




    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIGHT_LEVEL);
    }

    private IBlockState withLevel(int level)
    {
        return this.getDefaultState().withProperty(LIGHT_LEVEL, level);
    }

    private int getLevel(IBlockState state) {
        return state.getValue(LIGHT_LEVEL);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.withLevel(meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return this.getLevel(state);
    }

    @Override
    public int getLightValue(IBlockState state) {
        return getLevel(state);
    }

}
