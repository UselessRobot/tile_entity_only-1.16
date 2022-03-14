package com.toaster.tileentityonly.functionalblocks.tileentities;

import java.util.function.Supplier;

import com.toaster.tileentityonly.TileEntityOnly;
import com.toaster.tileentityonly.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
	public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TileEntityOnly.MOD_ID);
	
	public static void register(IEventBus eventBus) {
		TILE_ENTITIES.register(eventBus);
	}
	
	public static RegistryObject<TileEntityType<PlaceholderMachineTileEntity>> PLACEHOLDER_MACHINE = 
			register("placeholder_machine_tile_entity", PlaceholderMachineTileEntity::new, ModBlocks.PLACEHOLDER_MACHINE);
	
	
	private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> factory, RegistryObject<? extends Block> block) {
        return TILE_ENTITIES.register(name, () -> {
            //noinspection ConstantConditions - null in build
            return TileEntityType.Builder.of(factory, block.get()).build(null);
        });
    }
}
