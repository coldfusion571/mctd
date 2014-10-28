package com.coldfusion571.mctd;

import com.coldfusion571.tileentities.TileEntityWaypointBlock;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;


public class WaypointBlock extends BlockContainer{
	
	protected WaypointBlock( Minecraft mcObj ){
		super(Material.ground);

	}
	
	@SideOnly(Side.CLIENT)
	protected IIcon blockIcon;
	protected IIcon blockIconTop;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister side){
		blockIcon    = side.registerIcon(McTd.MODID + ":" + this.getUnlocalizedName().substring(5));
		blockIconTop = side.registerIcon(McTd.MODID + ":" + this.getUnlocalizedName().substring(5) + "Top");
		System.out.println("wpblockicon:"+blockIcon);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon( int side, int metadata ){
		if( side == 1 ){
			return blockIconTop;
		}
		else{
			return blockIcon;
		}
	}

	public TileEntity createNewTileEntity(World var1, int var2){
		if( var1.isRemote){
			System.out.println("cnte from server");
		}
		System.out.println("create new tile entity");
		return new TileEntityWaypointBlock(var1);
	}
	
	public void breakBlock( World world, int x, int y, int z, Block block, int meta){
		TileEntityWaypointBlock tileentitywaypointblock = (TileEntityWaypointBlock) world.getTileEntity(x, y, z);
		if( tileentitywaypointblock != null ){
			world.func_147453_f(x, y, z, block);
			McTd.instance.waypoints.remove(tileentitywaypointblock);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	public void onPlayerUseItemEvent(){
		
	}

}
