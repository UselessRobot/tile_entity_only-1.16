package com.toaster.tileentityonly.items;

import com.toaster.tileentityonly.TileEntityOnly;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TileEntityOnly.MOD_ID);
	
	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}
	
	public static final RegistryObject<Item> PLACEHOLDER_ITEM = ITEMS.register("placeholder_item", 
			() -> new Item(new Item.Properties()
					.tab(ModItemGroups.MOD_GROUP)
				)
	);
}
