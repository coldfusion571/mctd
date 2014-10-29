package com.coldfusion571.mctd;

import java.util.Iterator;

import com.coldfusion571.tileentities.TileEntityEndBlock;
import com.coldfusion571.tileentities.TileEntityWaypointBlock;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityAITdPathing extends EntityAIBase {
	
    private MctdMob entity;
    private double speed;
    private TileEntityWaypointBlock waypoint;
    private TileEntityEndBlock end;
    private Iterator<TileEntityWaypointBlock> waypointIterator;
    
    public EntityAITdPathing(MctdMob par1EntityCreature, double par2){
        this.entity = par1EntityCreature;
        this.speed  = par2;
        this.setMutexBits(1);
    }
    
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if( this.entity == null ){
    		System.out.println("should execute? no, no entity");
    		return false;
    	}
    	return true;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	this.entity.setWaypointIterator(McTd.waypoints.iterator());
    	this.entity.setEnd(McTd.endBlockEntity);
    }
    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
    	if( entity.reachedEnd ){
    		return false;
    	}
        return !this.entity.getNavigator().noPath();
    }
    
    public void updateTask(){
    	this.targeting();
    }
    	
    public void targeting(){
    	TileEntity t = this.entity.getTargetEntity();
    	if( (t !=  null) &&
    		!this.entity.reachedEnd &&
    		t.getDistanceFrom(this.entity.posX, this.entity.posY, this.entity.posZ) > 1 ){
    		//
    		// Try and move to target
    		//
	    	this.entity.getLookHelper().setLookPosition(t.xCoord, t.yCoord, t.zCoord, 5.0F, 5.0F);
	    	this.entity.getNavigator().tryMoveToXYZ(t.xCoord, t.yCoord, t.zCoord, this.speed);
    	}
    	else if( this.entity.finalTarget ){
    		//
    		// If we didn't have a target and we're on our final target
    		// then we're done with the maze
    		//
    		this.entity.reachedEnd = true;
    		this.entity.setHealth(0);
    		McTd.instance.loseLife(1);
    	}
    	else{
    		//
    		// Look for our next target
    		//
    		Iterator iter = this.entity.getWaypointIterator();
    		if( iter != null
    		 && iter.hasNext() ){
    			TileEntityWaypointBlock n = (TileEntityWaypointBlock)iter.next();
    			this.entity.setTargetEntity( (TileEntity)n );
    			this.targeting();
    		}
    		else{
    			TileEntityEndBlock e = this.entity.getEnd();
    			this.entity.setTargetEntity( (TileEntity)e );
    			if( e != null ){
    				System.out.println("Using end as next target");
    				this.entity.finalTarget = true;
    			}
    			else{
    				System.out.println("Have to figure out how to break out here");
    			}
    		}
    	}

    }
}
