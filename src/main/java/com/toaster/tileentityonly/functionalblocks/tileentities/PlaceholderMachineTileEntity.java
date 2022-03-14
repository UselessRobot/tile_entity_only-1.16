package com.toaster.tileentityonly.functionalblocks.tileentities;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.toaster.tileentityonly.TileEntityOnly;
import com.toaster.tileentityonly.capabilities.Heat;
import com.toaster.tileentityonly.capabilities.Progress;
import com.toaster.tileentityonly.functionalblocks.containers.PlaceholderMachineContainer;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class PlaceholderMachineTileEntity extends AbstractFurnaceTileEntity{
	//TODO maybe extend something else?
	protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

	private int litTime;
	private int litDuration;
	private int cookingProgress;
	private int cookingTotalTime;

	private int itemMaxTemp;
	private int currentTemp;
	
	protected final IIntArray dataAccess = new IIntArray() {
		@Override
		public void set(int index, int value) {
			switch(index) {
			case 0:
				PlaceholderMachineTileEntity.this.litTime = value;
				break;
			case 1:
				PlaceholderMachineTileEntity.this.litDuration = value;
				break;
			case 2:
				PlaceholderMachineTileEntity.this.cookingProgress = value;
				break;
			case 3:
				PlaceholderMachineTileEntity.this.cookingTotalTime = value;
				break;
			case 4:
				PlaceholderMachineTileEntity.this.itemMaxTemp = value;
				break;
			case 5:
				PlaceholderMachineTileEntity.this.currentTemp = value;
				break;
			}
		}
		
		@Override
		public int getCount() {
			return 6;
		}
		
		@Override
		public int get(int index) {
			switch(index) {
			case 0:
				return PlaceholderMachineTileEntity.this.litTime;
			case 1:
				return PlaceholderMachineTileEntity.this.litDuration;
			case 2:
				return PlaceholderMachineTileEntity.this.cookingProgress;
			case 3:
				return PlaceholderMachineTileEntity.this.cookingTotalTime;
			case 4:
				return PlaceholderMachineTileEntity.this.itemMaxTemp;
			case 5:
				return PlaceholderMachineTileEntity.this.currentTemp;
			default:
				TileEntityOnly.LOGGER.info("default");
				return 0;
			}
		}
	};
	
	private final ItemStackHandler itemHandler = createHandler(); //for some reason it works without this maybe rebuild the tileentity with capabilities
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	
	private final Progress progressHandler = createProgress();
	private final LazyOptional<Progress> progress = LazyOptional.of(() -> progressHandler);
	
	private final Heat heatHandler = createHeat();
	private final LazyOptional<Heat> heat = LazyOptional.of(() -> heatHandler);
	
	public PlaceholderMachineTileEntity(TileEntityType<?> tileEntity) {
		super(tileEntity, IRecipeType.SMELTING); // takes all the recipes a furnace will take for now
	}
	
	public PlaceholderMachineTileEntity() {
		this(ModTileEntities.PLACEHOLDER_MACHINE.get());
	}
	
	private ItemStackHandler createHandler() {
		return new ItemStackHandler(3) { //starts for 1 you dummy, not 0
			
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			
			@Override
			public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
				return super.isItemValid(slot, stack);
			}
			
			@Override
			public int getSlotLimit(int slot) {
				return 64; //limit number of items in input slot
			}
			
			@Nonnull
			@Override
			public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
				if(!isItemValid(slot, stack)) {
					return stack;
				}
				return super.insertItem(slot, stack, simulate);
			}
			
		};
	}
	
	private Progress createProgress() {
		return new Progress(itemMaxTemp, 0) {
			@Override
			protected void onProgressChanged() {
				setChanged();
			}
		};
	}
	
	private Heat createHeat() {
		return new Heat(22, 30, 0, Integer.MAX_VALUE) {
			@Override
			protected void onHeatChanged() {
				setChanged();
			}
		};
	}
	
	@Override
	protected void invalidateCaps() {
		// TODO Auto-generated method stub
		super.invalidateCaps();
		handler.invalidate();
//		heat.invalidate();
//		progress.invalidate();
	}
	
	@Override
	public void tick() {
		TileEntityOnly.LOGGER.info("tick");
		boolean litFlag = this.isLit();
		boolean saveFlag = false;
		TileEntityOnly.LOGGER.info("litFlag: " + litFlag);
		TileEntityOnly.LOGGER.info("saveFlag: " + saveFlag);
		
		if(!this.level.isClientSide()) {
			List<FurnaceRecipe> furnaceRecipes = this.level.getRecipeManager().getAllRecipesFor(IRecipeType.SMELTING);
			for(IRecipe recipe:furnaceRecipes) {
				TileEntityOnly.LOGGER.info("furnaceresult: " + recipe.getResultItem().getDescriptionId());
			}
			ItemStack inputItem = itemHandler.getStackInSlot(0);
			ItemStack fuelItem = itemHandler.getStackInSlot(1);
			if(this.isLit() || !fuelItem.isEmpty() && !inputItem.isEmpty()) {
				TileEntityOnly.LOGGER.info("inputItem: " + inputItem.getDescriptionId());
				TileEntityOnly.LOGGER.info("fuelItem: " + fuelItem.getDescriptionId());
				
				IRecipe<?> irecipe = 
						this.level.getRecipeManager().getRecipeFor(
								IRecipeType.SMELTING, 
								this, 
								this.level)
						.orElse(null);
				
				TileEntityOnly.LOGGER.info("can process: " + this.canBurn(irecipe));
				if(!this.isLit() && this.canBurn(irecipe)) {
					this.litTime = this.getBurnDuration(fuelItem);
					this.litDuration = this.litTime;
					if (this.isLit()) {
						saveFlag = true;
						if (fuelItem.hasContainerItem()) {
//							this.items.set(1, fuelItem.getContainerItem());
							itemHandler.extractItem(1, 1, false);
							itemHandler.insertItem(1, fuelItem.getContainerItem(), false);
						}else if (!fuelItem.isEmpty()){
//							Item item = fuelItem.getItem();
//							fuelItem.shrink(1);
							itemHandler.extractItem(1, 1, false);
							if (fuelItem.isEmpty()) {
//								this.items.set(1, fuelItem.getContainerItem());
								itemHandler.extractItem(1, 1, false);
								itemHandler.insertItem(1, fuelItem.getContainerItem(), false);
							}
						}
					}
				}
				
				if(isLit() && this.canBurn(irecipe)) {
					this.cookingProgress++;
					if(this.cookingProgress >= this.cookingTotalTime) {
						this.cookingProgress = 0;
						this.cookingTotalTime = this.getTotalCookTime();
						burn(irecipe);
						saveFlag = true;
					}
				} else {
					this.cookingProgress = 0;
				}
			} else if(!this.isLit() && this.cookingProgress > 0) {
				this.cookingProgress = MathHelper.clamp(this.cookingProgress - 2, 0, this.cookingTotalTime);
				// Why subtract 2 from cooking progress?
			}
			
			if (litFlag != this.isLit()) {
					// check if tile entity lit status matches current lit status
				saveFlag = true;
//	            this.level.setBlock(this.worldPosition, this.level.getBlockState(this.worldPosition).setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(this.isLit())), 3);
			}
		}
		
		if(saveFlag) {
			setChanged();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void burn(@Nullable IRecipe<?> recipe) {
		if (recipe != null && this.canBurn(recipe)) {
//			ItemStack inputstack = itemHandler.getStackInSlot(0);
			ItemStack resultStack = ((IRecipe<ISidedInventory>) recipe).assemble(this);
//			ItemStack resultStack = new RecipeWrapper(itemHandler);
			TileEntityOnly.LOGGER.info("resultStack: " + resultStack.getDescriptionId());
			ItemStack outputStack = itemHandler.getStackInSlot(2);
			if (outputStack.isEmpty() || outputStack.getItem() == resultStack.getItem()) {
				itemHandler.insertItem(2, resultStack.copy(), false);
//				this.items.set(2, resultStack.copy());
			}
			
			if (!this.level.isClientSide()) {
				this.setRecipeUsed(recipe);
			}
			
			//wet sponge special case
//			if (inputstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.items.get(1).isEmpty() && this.items.get(1).getItem() == Items.BUCKET) {
//				//this.items.set(1, new ItemStack(Items.WATER_BUCKET));
//				itemHandler.extractItem(1, 1, false);
//				itemHandler.insertItem(1, new ItemStack(Items.WATER_BUCKET), remove);
//			}
			
			itemHandler.extractItem(0, 1, false);
			//inputstack.shrink(1);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected boolean canBurn(@Nullable IRecipe<?> recipe) {
		if(recipe == null) {
			TileEntityOnly.LOGGER.info("recipe is null.");
			
		}
		if (!itemHandler.getStackInSlot(0).isEmpty() && recipe != null) {
			//recipes are null fix this part
			ItemStack resultStack = ((IRecipe<ISidedInventory>) recipe).assemble(this);

			if (resultStack.isEmpty()) {
				return false;
			} else {
				ItemStack outputStack = itemHandler.getStackInSlot(2);
				if (outputStack.isEmpty()) {
					return true;
				} else if (!outputStack.sameItem(resultStack)) {
					return false;
				} else if (
						outputStack.getCount() + resultStack.getCount() <= this.getMaxStackSize() && 
						outputStack.getCount() + resultStack.getCount() <= outputStack.getMaxStackSize()
						) { // Forge fix: make furnace respect stack sizes in furnace recipes
					return true;
				} else {
					return outputStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
				}
			}
		} else {
			return false;
		}
	}
	
	
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap) {
		if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.cast();
		}
		return super.getCapability(cap);
	}
	
	private boolean isLit() {
		return this.litTime > 0;
	}
	
	public void load(BlockState blockState, CompoundNBT nbt) {
		super.load(blockState, nbt);
		
		this.itemMaxTemp = nbt.getInt("MaxItemTemp");
		this.currentTemp = nbt.getInt("currentTemp");
	}
	
	public CompoundNBT save(CompoundNBT nbt) {
		super.save(nbt);
		nbt.putInt("MaxItemTemp", this.itemMaxTemp);
		nbt.putInt("currentTemp", this.currentTemp);
		return nbt;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new TranslationTextComponent("container.placeholder_machine");
	}


	@Override
	protected Container createMenu(int id, PlayerInventory playerInv) {
		return new PlaceholderMachineContainer(id, playerInv.player.level, this.worldPosition, playerInv, playerInv.player, this.dataAccess);
	}
}
