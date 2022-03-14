package com.toaster.tileentityonly.data;

import com.toaster.tileentityonly.TileEntityOnly;
import com.toaster.tileentityonly.items.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProvider {

	public ItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, TileEntityOnly.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		// TODO Auto-generated method stub
		ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

		withExistingParent("placeholder_machine", modLoc("block/placeholder_machine"));

		// getRegistryName() returns a resource location
		// get() is used to return the *stored* item in the RegistryObject, please,
		// please please remember that
		String pHItemPath = ModItems.PLACEHOLDER_ITEM.get().getRegistryName().getPath();
//		Item vanillaItem = 
		modelBuilder(itemGenerated, pHItemPath);

	}

	/**
	 * Takes a baseModel and and binds an items render model to it, then adds a
	 * layer with a texture to the model, kinda
	 * 
	 * @param baseModel
	 * @param pathName
	 * @return returns itself
	 */
	private ItemModelBuilder modelBuilder(ModelFile baseModel, String pathName) {
		return getBuilder(pathName).parent(baseModel).texture("layer0", "item/" + pathName);
	}

	@Override
	public String getName() {
		return "Item Models: " + TileEntityOnly.MOD_ID;
	}

}
