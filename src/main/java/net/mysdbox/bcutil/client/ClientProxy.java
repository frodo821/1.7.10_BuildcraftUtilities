package net.mysdbox.bcutil.client;

import buildcraft.core.render.RenderingEntityBlocks;
import buildcraft.core.render.RenderingEntityBlocks.EntityRenderIndex;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.mysdbox.bcutil.core.GameRegistoryUtil;
import net.mysdbox.bcutil.lib.CommonProxy;

public class ClientProxy extends CommonProxy {
	@Override
	public World getClientWorld(){
		return FMLClientHandler.instance().getClient().theWorld;
	}
	@Override
	public void registerTileEntity(){
		GameRegistry.registerTileEntity(infiRequestor, getTileEntityId(infiRequestor));
		/*RenderingEntityBlocks.blockByEntityRenders.put(
				new EntityRenderIndex(GameRegistoryUtil.InfiEnergyRequestor, 0),
				new CommonRenderer(
					new ResourceLocation(
						"buildcraft:buildcraft/textures/blocks/BlockInfiEnergyRequestor/default")));*/
	}
}
