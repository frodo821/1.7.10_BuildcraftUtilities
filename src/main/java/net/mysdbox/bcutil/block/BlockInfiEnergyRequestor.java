package net.mysdbox.bcutil.block;

import buildcraft.api.tools.IToolWrench;
import buildcraft.core.BCCreativeTab;
import buildcraft.core.lib.block.BlockBuildCraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.mysdbox.bcutil.BuildCraftUtilities;

public class BlockInfiEnergyRequestor extends BlockBuildCraft {

	protected BlockInfiEnergyRequestor(Material material, CreativeTabs creativeTab) {
		super(material, creativeTab);
		setBlockName("BlockInfiEnergyRequestor");
		setBlockTextureName("bcutil:BlockInfiEnergyRequestor");
		setStepSound(soundTypeStone);
		setBlockUnbreakable();
		setLightLevel(1f);
	}
	
	public BlockInfiEnergyRequestor() {
		this(Material.iron, BCCreativeTab.get("main"));
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
		TileInfiEnergyRequestor ent = (TileInfiEnergyRequestor)world.getTileEntity(x, y, z);
		ItemStack items = player.inventory.getCurrentItem();
		if(ent == null || world.isRemote) return false;
		BuildCraftUtilities.sendChatMessage(
			String.format("%sAverage Output :%d RF, %sAverage Input :%d RF",
				BuildCraftUtilities.loadTextStyle("red"),
				ent.getAverageOutput(),
				BuildCraftUtilities.loadTextStyle("lime"),
				ent.getAverageInput()));
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileInfiEnergyRequestor();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister ireg){
		super.registerBlockIcons(ireg);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return blockIcon;
	}

}
