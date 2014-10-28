package com.coldfusion571.mctd.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;

import com.coldfusion571.mctd.MctdMob;
import com.coldfusion571.mctd.RenderMctdMob;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(MctdMob.class, new RenderMctdMob(new ModelBiped(), 0.5F));
	}
	
	@Override
    public void printMessageToPlayer(String msg) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(msg);
    }
}
