package net.mysdbox.bcutil;

import java.util.HashMap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.mysdbox.bcutil.core.GameRegistoryUtil;
import net.mysdbox.bcutil.lib.CodingUtil;
import net.mysdbox.bcutil.lib.CommonProxy;
import net.mysdbox.bcutil.lib.LoggingUtil;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;

@Mod(
		modid = BuildCraftUtilities.MODID,
		dependencies = "required-after:BuildCraft|Transport",
		version = BuildCraftUtilities.MODVER,
		name = BuildCraftUtilities.MODNAME)
public class BuildCraftUtilities {
	public static final String MODID = "bcutil";
	public static final String MODVER = "0.0.1 beta";
	public static final String MODNAME = "BuildCraft Utilities";
	
	private static HashMap<String, String> textDecorators = new HashMap<String, String>();
	
	@SidedProxy(
		clientSide = "net.mysdbox.bcutil.client.ClientProxy",
		serverSide = "net.mysdbox.bcutil.lib.CommonProxy")
	public static CommonProxy proxy;
	
	@Instance("bcutil")
	public static BuildCraftUtilities instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		registerTextStyles();
		LoggingUtil.loggerInit(event);
		LoggingUtil.log("Registering items...");
		GameRegistoryUtil.register();
		LoggingUtil.log("All items have been registered!");
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event){
		LoggingUtil.log("Creating recipes and item instances...");
		GameRegistoryUtil.registerRecipes();
		LoggingUtil.log("All crafting recipes of all items have been registered!");
		LoggingUtil.log("And now registering tile entities...");
		proxy.registerTileEntity();
		LoggingUtil.log("All tile entities have been registered!");
	}
	
	private void registerTextStyles(){
		textDecorators.put("black", "��0");
		textDecorators.put("darkblue", "��1");
		textDecorators.put("darkgreen", "��2");
		textDecorators.put("darkaqua", "��3");
		textDecorators.put("darkred", "��4");
		textDecorators.put("darkpurple", "��5");
		textDecorators.put("gold", "��6");
		textDecorators.put("gray", "��7");
		textDecorators.put("darkgray", "��8");
		textDecorators.put("blue", "��9");
		textDecorators.put("lime", "��a");
		textDecorators.put("aqua", "��b");
		textDecorators.put("red", "��c");
		textDecorators.put("purple", "��d");
		textDecorators.put("yellow", "��e");
		textDecorators.put("white", "��f");
		textDecorators.put("random", "��k");
		textDecorators.put("bold", "��l");
		textDecorators.put("strike", "��m");
		textDecorators.put("underline", "��n");
		textDecorators.put("italic", "��o");
		textDecorators.put("reset", "��r");
	}
	
	public static String loadTextStyle(String key){
		if(!textDecorators.containsKey(key)){
			return CodingUtil.emptyStr;
		}
		return textDecorators.get(key);
	}
	
	public static void sendChatMessage(String mes){
		if(FMLCommonHandler.instance().getEffectiveSide().isClient()){
			Minecraft.getMinecraft().ingameGUI.getChatGUI()
				.printChatMessage(new ChatComponentText(mes));
		}
		else if(FMLCommonHandler.instance().getEffectiveSide().isServer()){
			MinecraftServer.getServer().getConfigurationManager()
				.sendChatMsgImpl(new ChatComponentText(mes), true);
		}
	}
	
	public static void sendChatMessage(String mes, String style){
		sendChatMessage(loadTextStyle(style) + mes);
	}
}
