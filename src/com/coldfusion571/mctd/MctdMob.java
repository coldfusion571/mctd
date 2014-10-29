package com.coldfusion571.mctd;

import java.util.Iterator;

import com.coldfusion571.tileentities.TileEntityEndBlock;
import com.coldfusion571.tileentities.TileEntityWaypointBlock;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MctdMob extends EntityMob {
	
	private boolean completedWaypoints;
	private boolean completedPathing;
	private TileEntityWaypointBlock waypoint;
	private TileEntityEndBlock end;
	private TileEntity target;
	private float target_x;
	private float target_y;
	private float target_z;
	private Iterator waypointIterator;
	private int id;
	public boolean finalTarget;
	public boolean reachedEnd;
	
	public MctdMob(World world){
		super(world);
		this.completedWaypoints = false;
		this.completedPathing = false;
		this.end = McTd.endBlockEntity;
		this.tasks.addTask(0, new EntityAITdPathing(this, 1.5D));
		this.id = McTd.instance.getMobId();
	}

	public boolean isAIEnabled(){
		return true;
	}
	
	@Override
	protected void applyEntityAttributes(){
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}
	
	public void setWaypoint(TileEntityWaypointBlock arg1){
		this.waypoint = arg1;
	}
	
	public TileEntityWaypointBlock getWaypoint(){
		return this.waypoint;
	}

	public void setEnd(TileEntityEndBlock arg1){
		this.end = arg1;
	}
	
	public TileEntityEndBlock getEnd(){
		return this.end;
	}
	
	public void setTargetEntity(TileEntity arg1){
		this.target = arg1;
	}
	
	public TileEntity getTargetEntity(){
		return this.target;
	}
	
	public void setWaypointIterator(Iterator arg1){
		this.waypointIterator = arg1;
	}
	
	public Iterator getWaypointIterator(){
		return this.waypointIterator;
	}
}
