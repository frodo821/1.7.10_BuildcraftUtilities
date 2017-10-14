package net.mysdbox.bcutil.core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

public class ConfigLoader {
	Configuration cfg;
	
	public ConfigLoader(FMLPreInitializationEvent ev){
		cfg = new Configuration(ev.getSuggestedConfigurationFile());
		cfg.load();
	}
	
	public Object[] getConfigContents(){
		return null;
	}
}
