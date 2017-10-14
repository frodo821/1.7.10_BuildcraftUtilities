package net.mysdbox.bcutil.core;

import buildcraft.BuildCraftCore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.mysdbox.bcutil.block.BlockInfiEnergyRequestor;
import net.mysdbox.bcutil.item.*;

public class GameRegistoryUtil {
	public static Item PipeInspector;
	public static Block InfiEnergyRequestor;
	public static Item ItemInfiEneReq;
	
	public static void register(){
		PipeInspector = new ItemPipeInspector();
		GameRegistry.registerItem(PipeInspector,
				"item." + PipeInspector.getClass().getSimpleName());
		
		InfiEnergyRequestor = new BlockInfiEnergyRequestor();
		
		GameRegistry.registerBlock(InfiEnergyRequestor,
				"block." + InfiEnergyRequestor.getClass().getSimpleName());
	}
	
	public static void registerRecipes(){
		GameRegistry.addRecipe(
				new ShapedOreRecipe(
						new ItemStack(PipeInspector, 1),
						new Object[] {
								"I I",
								"RgR",
								"idi",
								Character.valueOf('I'),"ingotIron",
								Character.valueOf('R'),"dustRedstone",
								Character.valueOf('g'),"gearGold",
								Character.valueOf('i'),"gearIron",
								Character.valueOf('d'),"gearDiamond"
								}));
	}
}
