package com.toaster.tileentityonly.data;

import com.toaster.tileentityonly.TileEntityOnly;
import com.toaster.tileentityonly.blocks.ModBlocks;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {

	public BlockStates(DataGenerator generator,  ExistingFileHelper exFileHelper) {
		super(generator, TileEntityOnly.MOD_ID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		// TODO Auto-generated method stub
		simpleBlock(ModBlocks.PLACEHOLDER_MACHINE.get());
	}

}
