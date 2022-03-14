package com.toaster.tileentityonly.functionalblocks.block;

import com.toaster.tileentityonly.functionalblocks.containers.PlaceholderMachineContainer;
import com.toaster.tileentityonly.functionalblocks.tileentities.ModTileEntities;
import com.toaster.tileentityonly.functionalblocks.tileentities.PlaceholderMachineTileEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.StateHolder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class PlaceholderMachineBlock extends Block{
//	public static final DirectionProperty FACING = HorizontalBlock.FACING;
	public static final BooleanProperty LIT = BlockStateProperties.LIT;

	public PlaceholderMachineBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
				this.stateDefinition.any()
//				.setValue(FACING, Direction.NORTH)
				.setValue(LIT, Boolean.valueOf(false))
		);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		// TODO Auto-generated method stub
		super.createBlockStateDefinition(builder);
		builder.add(LIT);
	}
	
	@Override
	public ActionResultType use(BlockState state, World level, BlockPos pos, PlayerEntity player, Hand hand,
			BlockRayTraceResult hit) {
		if(!level.isClientSide()) {
			TileEntity tileEntity = level.getBlockEntity(pos);
			
			if(!player.isCrouching()) {
				if(tileEntity instanceof PlaceholderMachineTileEntity) {
					INamedContainerProvider containerProvider = createContainerProvider(level, pos);
					
					NetworkHooks.openGui((ServerPlayerEntity) player, containerProvider, tileEntity.getBlockPos());
				} else {
					throw new IllegalStateException("Missing Container Provider.");
				}
			} else {
				if(tileEntity instanceof PlaceholderMachineTileEntity) {
//					if(false) {
//						//TODO
//					}
				}
			}
			
		}
		return ActionResultType.SUCCESS; // show interact animation on client
	}
	
	private INamedContainerProvider createContainerProvider(World level, BlockPos pos) {
		// TODO Auto-generated method stub
		
		return new INamedContainerProvider() {
			
			@Override
			public Container createMenu(int i, PlayerInventory playerInv, PlayerEntity player) {
				return new PlaceholderMachineContainer(i, level, pos, playerInv, player);
			}
			
			@Override
			public ITextComponent getDisplayName() {
				return new TranslationTextComponent("screen.tileentityonly.placeholder");
			}
		};
		
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		// TODO Auto-generated method stub
		return ModTileEntities.PLACEHOLDER_MACHINE.get().create();
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

}
