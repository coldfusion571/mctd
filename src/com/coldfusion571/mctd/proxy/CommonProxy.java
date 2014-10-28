package com.coldfusion571.mctd.proxy;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

import com.coldfusion571.mctd.McTd;
import com.coldfusion571.mctd.eventhandlers.WorldTickHandler;
import com.coldfusion571.tileentities.TileEntityEndBlock;
import com.coldfusion571.tileentities.TileEntityStartBlock;
import com.coldfusion571.tileentities.TileEntityWaypointBlock;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void registerRenderers() {
		// TODO Auto-generated method stub
		
	}
	
	public void registerTileEntities(){
		GameRegistry.registerTileEntity(TileEntityStartBlock.class, McTd.MODID + "TileEntityStartBlock");
		GameRegistry.registerTileEntity(TileEntityWaypointBlock.class, McTd.MODID + "TileEntityWaypointBlock");
		GameRegistry.registerTileEntity(TileEntityEndBlock.class, McTd.MODID + "TileEntityEndBlock");
	}
	
	public void registerEventListeners(){
		FMLCommonHandler.instance().bus().register(new WorldTickHandler(Minecraft.getMinecraft()));
	}

	public void printMessageToPlayer(String msg) {
		// Nothing to do
	}

}
