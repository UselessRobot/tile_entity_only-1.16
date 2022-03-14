package com.toaster.tileentityonly.data;

import com.toaster.tileentityonly.TileEntityOnly;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = TileEntityOnly.MOD_ID)
public class DataGenerators {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper extFileHelper = event.getExistingFileHelper();
		
		if(event.includeServer()) {
			
		}
		
		if(event.includeClient()) {
			generator.addProvider(new BlockStates(generator, extFileHelper));
			generator.addProvider(new ItemModels(generator, extFileHelper));
		}
	}
}
