package net.mysdbox.bcutil.item;

import buildcraft.api.tools.IToolWrench;
import buildcraft.core.lib.block.TileBuildCraft;
import buildcraft.transport.Pipe;
import buildcraft.transport.PipeTransportFluids;
import buildcraft.transport.PipeTransportPower;
import buildcraft.transport.TileGenericPipe;
import buildcraft.core.BCCreativeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.mysdbox.bcutil.BuildCraftUtilities;
import net.mysdbox.bcutil.lib.CodingUtil;
import net.mysdbox.bcutil.lib.LoggingUtil;

import java.lang.reflect.Field;
import org.apache.logging.log4j.Level;

public class ItemPipeInspector extends Item implements IToolWrench {
	private int usesCount = 0;
	
	public ItemPipeInspector(){
		setCreativeTab(BCCreativeTab.get("main"));
		setUnlocalizedName(getClass().getSimpleName());
		setFull3D();
		setTextureName("bcutil:ammeter");
		setMaxStackSize(1);
		setHarvestLevel("wrench", 0);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
		usesCount++;
		if(usesCount % 2 == 1) return false;
		TileEntity block = world.getTileEntity(x, y, z);
		
		if(block == null) return false;
		
		if(block instanceof TileGenericPipe)
			return InspectEnergyPipes(stack, player, block, world, x, y, z, side, hitX, hitY, hitZ);
		
		return false;
	}
	
	private boolean InspectEnergyPipes(ItemStack stack, EntityPlayer player, TileEntity ent, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
		TileEntity block = ent;
		
		Pipe p = ((TileGenericPipe)block).pipe;
		if(p.transport.getClass() != PipeTransportPower.class)
			return InspectFluidsPipe(stack, player, ent, world, x, y, z, side, hitX, hitY, hitZ);
		
		PipeTransportPower tp = (PipeTransportPower)p.transport;
		short[] out = tp.displayPower;
		short tmp = 0;
		for(short i : out){
			tmp += i;
		}
		BuildCraftUtilities.sendChatMessage(String.format(
			"Power Transport Pipe [%d, %d, %d] currently passing energy: %d RF %s",
			x, y, z, tmp,
			tp.overload >= 60? BuildCraftUtilities.loadTextStyle("red")
				+ "[overloaded]" : CodingUtil.emptyStr));
		return !world.isRemote;
	}
	
	private boolean InspectFluidsPipe(ItemStack stack, EntityPlayer player, TileEntity ent, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
		TileEntity block = ent;
		
		Pipe p = ((TileGenericPipe)block).pipe;
		if(p.transport.getClass() != PipeTransportFluids.class){
			return false;
		}
		PipeTransportFluids pipe = (PipeTransportFluids)p.transport;
		if(pipe.fluidType == null){
			BuildCraftUtilities.sendChatMessage(
				String.format("Fluids Transport Pipe [%d, %d, %d] currently empty",
					x, y, z));
		}else{
			BuildCraftUtilities.sendChatMessage(
				String.format("Fluids Transport Pipe [%d, %d, %d] currently passing %s: %d mb",
					x, y, z,
					pipe.fluidType.getLocalizedName(), pipe.getFlowRate()));
		}
		return !world.isRemote;
	}
	
	@Override
	public boolean canWrench(EntityPlayer player, int paramInt1, int paramInt2, int paramInt3) {
		return false;
	}

	@Override
	public void wrenchUsed(EntityPlayer player, int paramInt1, int paramInt2, int paramInt3) {
		player.swingItem();
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass){
		return itemIcon;
	}
}
