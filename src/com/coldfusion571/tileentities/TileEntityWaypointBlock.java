package com.coldfusion571.tileentities;

import com.coldfusion571.mctd.McTd;
import com.coldfusion571.mctd.MctdMob;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityWaypointBlock extends TileEntity{
	
	private int id;
	
	public TileEntityWaypointBlock(World var1){
		this.setWorldObj(var1);
		if( var1.isRemote ){
			McTd.instance.waypoints.add(this);
		}
		this.id = McTd.instance.getWpId();
		if( var1.isRemote ){
			System.out.println("next msg from server");
		}
		System.out.println("waypointslength:"+McTd.instance.waypoints.size());
	}
	
	public void spawnMob(){
		MctdMob mob = new MctdMob(this.getWorldObj());
		mob.setLocationAndAngles(this.xCoord+1, this.yCoord+1, this.zCoord+1, 0, 0);
		this.getWorldObj().spawnEntityInWorld(mob);
		System.out.println("Spawned mob");
	}	
	
	public void readFromNBT( NBTTagCompound tagCompound ){
		super.readFromNBT(tagCompound);
	}
	
	public void writeToNBT( NBTTagCompound tagCompound ){
		super.writeToNBT(tagCompound);
	}
	
	public int getId(){
		return this.id;
	}
}
