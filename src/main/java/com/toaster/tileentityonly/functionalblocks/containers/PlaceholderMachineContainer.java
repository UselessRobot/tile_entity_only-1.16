package com.toaster.tileentityonly.functionalblocks.containers;

import com.toaster.tileentityonly.TileEntityOnly;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class PlaceholderMachineContainer extends Container{
	
	private final TileEntity tileEntity;
	private final PlayerEntity playerEntity;
	private final IItemHandler playerInv;
	private final IIntArray data;
	
	public PlaceholderMachineContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player) {
		this(id, world, pos, playerInv, player, new IntArray(4));
	}

	public PlaceholderMachineContainer(int id, World world, BlockPos pos, PlayerInventory playerInv, PlayerEntity player, IIntArray data) {
		super(ModContainers.PLACEHOLDER_MACHINE.get(), id);
		this.tileEntity = world.getBlockEntity(pos);
		this.playerEntity = player;
		this.playerInv = new InvWrapper(playerInv);
		this.data = data;
		
		layoutPlayerInventorySlots(8, 84);
		
		if(tileEntity != null) {
			tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
					addSlot(new SlotItemHandler(h, 0, 52, 31));		// input slot
					addSlot(new SlotItemHandler(h, 1, 80, 53)); 	// fuel slot
					addSlot(new SlotItemHandler(h, 2, 108, 31)); 	// output slot
				}
			);
		}
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
//		IWorldPosCallable levelPos = IWorldPosCallable.create(this.tileEntity.getLevel(), this.tileEntity.getBlockPos());
		return lessThan64(player, this.tileEntity.getBlockPos());
	}
	
	private boolean lessThan64(PlayerEntity player, BlockPos defaultVal) {
		return player.distanceToSqr(
				(double)defaultVal.getX() + 0.5D, 
				(double)defaultVal.getY() + 0.5D, 
				(double)defaultVal.getZ() + 0.5D
				) <= 64.0D;
	}
	
//	@Override
//    public boolean canInteractWith(PlayerEntity playerIn) {
//        return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getLevel(), tileEntity.getBlockPos()),
//                playerIn, ModBlocks.LIGHTNING_CHANNELER.get());
//    }


    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }

        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }

        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        addSlotBox(playerInv, 9, leftCol, topRow, 9, 18, 3, 18);

        topRow += 58;
        addSlotRange(playerInv, 0, leftCol, topRow, 9, 18);
    }

    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
           ItemStack itemstack1 = slot.getItem();
           itemstack = itemstack1.copy();
           if (index == 2) {
              if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                 return ItemStack.EMPTY;
              }

              slot.onQuickCraft(itemstack1, itemstack);
           } else if (index != 1 && index != 0) {
              if (this.canSmelt(itemstack1)) {
                 if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                 }
              } else if (this.isFuel(itemstack1)) {
                 if (!this.moveItemStackTo(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                 }
              } else if (index >= 3 && index < 30) {
                 if (!this.moveItemStackTo(itemstack1, 30, 39, false)) {
                    return ItemStack.EMPTY;
                 }
              } else if (index >= 30 && index < 39 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                 return ItemStack.EMPTY;
              }
           } else if (!this.moveItemStackTo(itemstack1, 3, 39, false)) {
              return ItemStack.EMPTY;
           }

           if (itemstack1.isEmpty()) {
              slot.set(ItemStack.EMPTY);
           } else {
              slot.setChanged();
           }

           if (itemstack1.getCount() == itemstack.getCount()) {
              return ItemStack.EMPTY;
           }

           slot.onTake(player, itemstack1);
        }

        return itemstack;
     }
    
    protected boolean canSmelt(ItemStack stack) {
    	return this.tileEntity.getLevel().getRecipeManager().getRecipeFor(IRecipeType.SMELTING, new Inventory(stack), this.tileEntity.getLevel()).isPresent();
    }

    protected boolean isFuel(ItemStack stack) {
    	return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, IRecipeType.SMELTING) > 0;
    }

    public int getBurnProgress() {
       int i = this.data.get(2);
       int j = this.data.get(3);
//       TileEntityOnly.LOGGER.info("Burn Progress: " + (j != 0 && i != 0 ? i * 24 / j : 0));
       return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    public int getLitProgress() {
       int i = this.data.get(1);
       if (i == 0) {
          i = 200;
       }

//       TileEntityOnly.LOGGER.info("Lit Progress is: " + (this.data.get(0) * 13 / i));
       return this.data.get(0) * 13 / i;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isLit() {
       return this.data.get(0) > 0;
    }
}
