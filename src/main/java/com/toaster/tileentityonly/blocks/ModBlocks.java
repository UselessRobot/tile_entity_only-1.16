package com.toaster.tileentityonly.blocks;

import java.util.function.Supplier;

import com.toaster.tileentityonly.TileEntityOnly;
import com.toaster.tileentityonly.functionalblocks.block.PlaceholderMachineBlock;
import com.toaster.tileentityonly.items.ModItemGroups;
import com.toaster.tileentityonly.items.ModItems;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TileEntityOnly.MOD_ID);
	
	public static void register(IEventBus eventBus) {
		BLOCKS.register(eventBus);
	}
	
	public static final RegistryObject<Block> PLACEHOLDER_MACHINE = registerBlock("placeholder_machine", 
			() -> new PlaceholderMachineBlock(AbstractBlock.Properties //TODO use the processor constructor
					.of(Material.METAL)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(2)
					.requiresCorrectToolForDrops()
					.strength(2.0f, 5.0f)
					)
			);
	
	/**
	 * Registers the block input, also registers the corresponding BlockItem
	 * @param <T> Anything that extends Block
	 * @param name item id of the block
	 * @param block the block supplied from this class 
	 * @return
	 */
	private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
		RegistryObject<T> returnBlock = BLOCKS.register(name, block);
		registerBlockItem(name, returnBlock);
		return returnBlock;
	}
	
	private static<T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
		ModItems.ITEMS.register(
				name, () -> new BlockItem(block.get(), 
						new Item.Properties()
						.tab(ModItemGroups.MOD_GROUP)
						)
				);
	}
}
