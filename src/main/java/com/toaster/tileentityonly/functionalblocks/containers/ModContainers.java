package com.toaster.tileentityonly.functionalblocks.containers;

import com.toaster.tileentityonly.TileEntityOnly;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {
	public static DeferredRegister<ContainerType<?>> CONTAINERS = 
			DeferredRegister.create(ForgeRegistries.CONTAINERS, TileEntityOnly.MOD_ID);
	
	public static final RegistryObject<ContainerType<PlaceholderMachineContainer>> PLACEHOLDER_MACHINE = 
			CONTAINERS.register("placeholder_machine_container", 
					() -> IForgeContainerType.create((windowId, inv, data) -> {
							BlockPos pos = data.readBlockPos();
							World world = inv.player.level;
							return new PlaceholderMachineContainer(windowId, world, pos, inv, inv.player);
						}
					) 
			);
	
	public static void register(IEventBus eventBus) {
		CONTAINERS.register(eventBus);
	}
}
