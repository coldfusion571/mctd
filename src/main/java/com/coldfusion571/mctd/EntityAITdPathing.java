package com.coldfusion571.mctd;

import java.util.Iterator;

import com.coldfusion571.tileentities.TileEntityEndBlock;
import com.coldfusion571.tileentities.TileEntityWaypointBlock;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class EntityAITdPathing extends EntityAIBase {
	
    private MctdMob entity;
    private double xPosition;
    private double yPosition;
    private double zPosition;
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
    		System.out.println("should execute no, no entity");
    		return false;
    	}
    	return true;
    	/*
    	if( McTd.waypoints.size() > 0 &&
    		this.waypoint < McTd.waypoints.size()-1 ){
    		this.xPosition = McTd.waypoints.get(this.waypoint).xCoord;
    		this.yPosition = McTd.waypoints.get(this.waypoint).yCoord;
    		this.zPosition = McTd.waypoints.get(this.waypoint).zCoord;
    		return true;
    	}
    	else if( this.waypoint >= McTd.waypoints.size()-1 &&
    			 McTd.endBlockEntity != null ){
    		this.xPosition = McTd.endBlockEntity.xCoord;
        	this.yPosition = McTd.endBlockEntity.yCoord;
        	this.zPosition = McTd.endBlockEntity.zCoord;
    		return true;
    	}
    	else{
    		return false;
    	}
    	*/
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
    		System.out.println("TDMob AI finished!");
    		return false;
    	}
        return !this.entity.getNavigator().noPath();
    }
    
    public void updateTask(){
    	this.targeting();
    }
    	
    public void targeting(){
    	// Is there a target?
		float[] t = this.entity.getTarget();
    	if( t != null ){
    		// Are we at the target point?
    		// int i = MathHelper.floor_double(this.theOwner.posX) - 2;
    		int pxf = MathHelper.floor_double(this.entity.posX);
    		int pyf = MathHelper.floor_double(this.entity.posY)-1;
    		int pzf = MathHelper.floor_double(this.entity.posZ)-1;
    		
    		//System.out.println("TARG X["+t[0]+"] Y["+t[1]+"] Z["+t[2]+"],  POS X["+pxf+"] Y["+pyf+"] Z["+pzf+"]");
    		if( MathHelper.abs_int((int) (t[0]-pxf)) < 2 &&
    			MathHelper.abs_int((int) (t[1]-pyf)) < 2 &&
    			MathHelper.abs_int((int) (t[2]-pzf)) < 2 ){
    			System.out.println("zeroing out target");
    			this.entity.setTarget(0.0F, 0.0F, 0.0F);
    			if( this.entity.finalTarget ){
    				this.entity.reachedEnd = true;
    			}
    			else{
    				this.targeting();
    			}
    		}
    		else{
    			//double degree = 180/Math.PI;
    	    	//double dX = this.entity.posX - t[0];
    	    	//double dY = this.entity.posY - t[1];
    	    	//double dZ = this.entity.posZ - t[2];
    	    	//float yaw = (float) (Math.atan2(dZ, dX)*degree) + 180;
    	    	//float pitch = (float) (Math.atan2(dY, Math.sqrt(dZ*dZ+dX*dX)) * degree);
    	    	//this.entity.setLocationAndAngles(this.entity.posX, this.entity.posY, this.entity.posZ, yaw, pitch);
    	    	
    	    	this.entity.getLookHelper().setLookPosition(t[0],t[1],t[2], 5.0F, 5.0F);
    	    	this.entity.getNavigator().tryMoveToXYZ(t[0],t[1],t[2], this.speed);
    		}
    	}
    	else{
    		// Is there a waypoint
    		Iterator iter = this.entity.getWaypointIterator();
    		if( iter != null
    		 && iter.hasNext() ){
    			TileEntityWaypointBlock n = (TileEntityWaypointBlock)iter.next();
    			System.out.println("Grabbing waypoint as next target["+n.getId()+"] x["+n.xCoord+"] y["+n.yCoord+"] z["+n.zCoord+"]");
    			this.entity.setTarget(n.xCoord, n.yCoord, n.zCoord);
    			this.targeting();
    		}
    		else{
    			TileEntityEndBlock e = this.entity.getEnd();
    			if( e != null ){
    				System.out.println("Using end as next target");
    				this.entity.finalTarget = true;
    				this.entity.setTarget(e.xCoord,e.yCoord,e.zCoord);
    			}
    			else{
    				System.out.println("Have to figure out how to break out here");
    			}
    		}
    	}

    }
}
