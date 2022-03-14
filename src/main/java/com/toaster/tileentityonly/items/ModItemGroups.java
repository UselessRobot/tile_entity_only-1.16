package com.toaster.tileentityonly.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
	public static final ItemGroup MOD_GROUP = new ItemGroup("modTab") {
		
		@Override
		public ItemStack makeIcon() {
			// TODO Auto-generated method stub
			return new ItemStack(ModItems.PLACEHOLDER_ITEM.get());
		}
	};
}
