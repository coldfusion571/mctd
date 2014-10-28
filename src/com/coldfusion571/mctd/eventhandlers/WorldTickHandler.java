package com.coldfusion571.mctd.eventhandlers;

import com.coldfusion571.mctd.McTd;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class WorldTickHandler {
	private Minecraft mc;
	
	public WorldTickHandler( Minecraft mc ){
		this.mc = mc;
	}
	
	@SubscribeEvent
	public void onWorldTick( WorldTickEvent event){
		if( event.phase == Phase.START){
			McTd.instance.tick();
		}
	}
}
