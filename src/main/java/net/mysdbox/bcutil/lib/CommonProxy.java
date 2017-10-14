package net.mysdbox.bcutil.lib;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.mysdbox.bcutil.block.TileInfiEnergyRequestor;

public class CommonProxy {
	protected Class infiRequestor = TileInfiEnergyRequestor.class;
	
	public World getClientWorld() {
		return null;
	}
	
	public void registerTileEntity(){
		GameRegistry.registerTileEntity(infiRequestor, getTileEntityId(infiRequestor));
	}
	
	protected final String getTileEntityId(Class<? extends TileEntity> tent){
		return "tile." + tent.getSimpleName();
	}
	
	protected final String getTileEntityId(Class<? extends TileEntity> tent, int meta){
		return String.format("tile.%s.meta%d", tent.getSimpleName(), meta);
	}
}
