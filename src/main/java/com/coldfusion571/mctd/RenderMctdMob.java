package com.coldfusion571.mctd;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMctdMob extends RenderBiped{

	private static final ResourceLocation textureLocation = new ResourceLocation( McTd.MODID + ":" + "/textures/models/mctdMob.png");
	
	public RenderMctdMob(ModelBiped par1ModelBiped, float par2) {
		super(par1ModelBiped, par2);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity){
		return textureLocation;
	}
}
