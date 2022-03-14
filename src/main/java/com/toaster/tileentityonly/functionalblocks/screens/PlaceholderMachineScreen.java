package com.toaster.tileentityonly.functionalblocks.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.toaster.tileentityonly.TileEntityOnly;
import com.toaster.tileentityonly.functionalblocks.containers.PlaceholderMachineContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PlaceholderMachineScreen extends ContainerScreen<PlaceholderMachineContainer>{
	private final ResourceLocation GUI = new ResourceLocation(TileEntityOnly.MOD_ID, "textures/gui/container/placeholder_machine.png");
	
	public PlaceholderMachineScreen(
			PlaceholderMachineContainer menu, 
			PlayerInventory playerInv,
			ITextComponent title) {
		super(menu, playerInv, title);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
		RenderSystem.color4f(1f, 1f, 1f, 1f);
		this.minecraft.getTextureManager().bind(GUI);
		this.blit(matrixStack, this.getGuiLeft(), this.getGuiTop(), 0/**x*/, 0/**y*/, this.getXSize(), this.getYSize());
		
		//furnace fire and progress
		
		if (this.menu.isLit()) {
			int k = this.menu.getLitProgress();
			
			this.blit(matrixStack, this.getGuiLeft() + 56, this.getGuiTop() + 48 - k, 176, 12 - k, 14, k + 1);
			
//			this.blit(matrixStack, this.getGuiLeft() + 56, this.getGuiTop() + 48 - 0, 176, 12 - 0, 14, 40 + 1);
		}
		int l = this.menu.getBurnProgress();
//		TileEntityOnly.LOGGER.info("Burn progress: " + l);
		this.blit(matrixStack, this.getGuiLeft() + 79, this.getGuiTop() + 34, 176, 14, l + 1, 16);
	}
	
}
