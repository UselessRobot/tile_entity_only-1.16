package com.toaster.tileentityonly.setup;

import com.toaster.tileentityonly.TileEntityOnly;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Registration {
	public static final DeferredRegister<Block> BLOCKS = create(ForgeRegistries.BLOCKS);
	public static final DeferredRegister<Item> ITEMS = create(ForgeRegistries.ITEMS);
	
	public static <T extends IForgeRegistryEntry<T>> DeferredRegister<T> create(IForgeRegistry<T> registry){
		return DeferredRegister.create(registry, TileEntityOnly.MOD_ID);
	}
	
	public static void register(IEventBus eventBus) {
//		BLOCKS.register(eventBus);
//		ITEMS.register(eventBus);
	}
}
