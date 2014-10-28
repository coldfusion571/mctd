package com.coldfusion571.mctd;

import com.coldfusion571.tileentities.TileEntityStartBlock;

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


public class StartBlock extends BlockContainer{
	
	protected StartBlock( Minecraft mcObj ){
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

	@Override
	public TileEntity createNewTileEntity(World var1, int var2){
		System.out.println("create new tile entity");
		return new TileEntityStartBlock(var1);
	}
	
	public void breakBlock( World world, int x, int y, int z, Block block, int meta){
		TileEntityStartBlock tileentitystartblock = (TileEntityStartBlock) world.getTileEntity(x, y, z);
		if( tileentitystartblock != null ){
			world.func_147453_f(x, y, z, block);
			McTd.startBlockEntity = null;
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	public void onPlayerUseItemEvent(){
		
	}

}
