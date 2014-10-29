package com.coldfusion571.tileentities;

import com.coldfusion571.mctd.McTd;
import com.coldfusion571.mctd.MctdMob;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class TileEntityEndBlock extends TileEntity{

	public TileEntityEndBlock(World var1){
		this.setWorldObj(var1);
		McTd.instance.endBlockEntity = this;
	}
	
	public void spawnMob(){
		MctdMob mob = new MctdMob(this.getWorldObj());
		mob.setLocationAndAngles(this.xCoord+1, this.yCoord+1, this.zCoord+1, 0, 0);
		this.getWorldObj().spawnEntityInWorld(mob);
		System.out.println("Spawned mob");
	}
	
	@SubscribeEvent
	public void RenderGameOverlayEvent(RenderGameOverlayEvent event ){
		if( event.type == RenderGameOverlayEvent.ElementType.TEXT ){
			Minecraft mc = Minecraft.getMinecraft();
			ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
			   FontRenderer fontRender = mc.fontRenderer;
			   int width = res.getScaledWidth();
			   int height = res.getScaledHeight();
			  
			   String test = "Simple Test";
			   int x = width / 2;
			   int y = height / 2;
			   int color = 0xffffff;
			   mc.fontRenderer.drawStringWithShadow(test, x, y, color);
			
			//Minecraft.getMinecraft().fontRenderer.drawStringWithShadow("hello world", this.xCoord, this.yCoord, 0xffffff);
		}
	}
	
	public void readFromNBT( NBTTagCompound tagCompound ){
		super.readFromNBT(tagCompound);
	}
	
	public void writeToNBT( NBTTagCompound tagCompound ){
		super.writeToNBT(tagCompound);
	}
}
