package com.coldfusion571.mctd;

import java.util.Random;
import java.util.Vector;

import com.coldfusion571.mctd.proxy.CommonProxy;
import com.coldfusion571.tileentities.TileEntityEndBlock;
import com.coldfusion571.tileentities.TileEntityStartBlock;
import com.coldfusion571.tileentities.TileEntityWaypointBlock;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = McTd.MODID, version = McTd.VERSION)
public class McTd
{
    public static final String MODID = "mctd";
    public static final String VERSION = "0.1";
    
    @Instance(MODID)
    public static McTd instance;
    
    public static Block startBlock;
    public static TileEntityStartBlock startBlockEntity;
    
    public static Block endBlock;
    public static TileEntityEndBlock endBlockEntity;
    
    public static Block waypointBlock;
 //   public static TileEntityWaypointBlock waypointBlockEntity;
    
    public static Vector <TileEntityWaypointBlock> waypoints;
    
    private int timer;
    private int waveInterval;
    private int waveCnt;
    private int waveLimit;
    private int waveSize;
    private int mobId;
    private int wpId;
    private int lives;
    private int warnTicks;
    private int interWaveTicks;
    private int waveMobsSent;
    private Minecraft mcObj;
    private boolean inWave;
    
    private MctdMob[] activeMobs;
    
    public static CreativeTabs tabMcTD = new CreativeTabsMcTd("McTd");
    
    @SidedProxy(clientSide="com.coldfusion571.mctd.proxy.ClientProxy", serverSide="com.coldfusion571.mctd.proxy.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	this.timer = 0;
    	this.waveInterval = 800;
    	this.waveCnt = 0;
    	this.waveLimit = 1;
    	this.waveSize = 5;
    	this.mobId = 0;
    	this.wpId = 0;
    	this.lives = 5;
    	this.warnTicks = 200; // 10 second wave announcement
    	this.interWaveTicks = 40; // 2 seconds between mobs
    	this.inWave = false;
    	this.waveMobsSent = 0;
    	
    	startBlock = new StartBlock(this.mcObj).setBlockName("startBlock").setCreativeTab(tabMcTD).setBlockTextureName(McTd.MODID + ":startBlock");
    	GameRegistry.registerBlock(startBlock, startBlock.getUnlocalizedName());
    	
    	endBlock = new EndBlock(this.mcObj).setBlockName("endBlock").setCreativeTab(tabMcTD).setBlockTextureName(McTd.MODID + ":endBlock");
    	GameRegistry.registerBlock(endBlock, endBlock.getUnlocalizedName());
    	
    	waypointBlock = new WaypointBlock(this.mcObj).setBlockName("waypointBlock").setCreativeTab(tabMcTD).setBlockTextureName(McTd.MODID + ":waypointBlock");
    	GameRegistry.registerBlock(waypointBlock, waypointBlock.getUnlocalizedName());
    	
    	this.waypoints = new Vector <TileEntityWaypointBlock>(10);
    	
    	registerEntity(MctdMob.class, "mctdMob");
    //	registerEntity(TileEntityStartBlock.class, "startBlockEntity");
    //	registerEntity(TileEntityWaypointBlock.class, "waypointBlockEntity");
    //	registerEntity(TileEntityEndBlock.class, "waypointBlockEnd");
    	
    	proxy.registerTileEntities();
        proxy.registerRenderers();
        
        this.mcObj = Minecraft.getMinecraft();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
        proxy.registerEventListeners();
    }
    
    public static void registerEntity(Class entityClass, String name){
    	int entityID = EntityRegistry.findGlobalUniqueEntityId();
    	long seed = name.hashCode();
    	Random rand = new Random(seed);
    	int primaryColor = rand.nextInt() * 16777215;
    	int secondaryColor = rand.nextInt() * 16777215;

    	EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
    	EntityRegistry.registerModEntity(entityClass, name, entityID, instance, 64, 1, true);
    	EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, primaryColor, secondaryColor));
    }
    
    public void tick(){
    	//
    	// If we've reached wave limit, stop caring
    	//
    	if( this.waveCnt > this.waveLimit ||
    		startBlockEntity == null      ||
			endBlockEntity   == null  ){
    		return;
    	}
    
    	//
    	// Increment timer and send waves when appropriate
    	//
    	++this.timer;
    	if( this.timer == this.waveInterval - this.warnTicks ){
    		String msg = "Wave coming in " + this.warnTicks/20 + " seconds!";
    		proxy.printMessageToPlayer(msg);
    	}
    	if( this.timer >= this.waveInterval
    		&& !this.inWave ){
    		this.inWave = true;
    	}
    	
    	int wavePlus = this.timer - this.waveInterval;
    	
    	if( wavePlus > 0 &&
    		wavePlus % this.interWaveTicks == 0 &&
    		this.waveMobsSent < this.waveSize ){
    		startBlockEntity.spawnMob();
    		this.waveMobsSent++;
    	}
    	
    	if( this.waveMobsSent >= this.waveSize ){
    		this.waveCnt++;
    		this.timer = 0;
    	}
    }
    
    public int getMobId(){
    	return this.mobId++;
    }
    
    public int getWpId(){
    	return this.wpId++;
    }
    
    public void loseLife( int arg1 ){
    	this.lives -= arg1;
    	String msg = "You have lost a life!";
    	proxy.printMessageToPlayer(msg);
    	if( this.lives <= 0 ){
    		msg = "You have lost all your lives!";
    		proxy.printMessageToPlayer(msg);
    	}
    }
}
