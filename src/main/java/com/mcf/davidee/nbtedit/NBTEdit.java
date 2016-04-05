package com.mcf.davidee.nbtedit;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.config.Configuration;

import com.mcf.davidee.nbtedit.forge.CommonProxy;
import com.mcf.davidee.nbtedit.nbt.NBTNodeSorter;
import com.mcf.davidee.nbtedit.nbt.NBTTree;
import com.mcf.davidee.nbtedit.nbt.NamedNBT;
import com.mcf.davidee.nbtedit.nbt.SaveStates;
import com.mcf.davidee.nbtedit.packets.PacketPipeline;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

/*
 * The NBTEdit Mod class - woo singletons. Sorry if you're reading through this mod, its formatting is very
 * odd and the code very messy. Hopefully someday this can be ported to GuiLib to provide much more flexibility.
 * 
 * TODO: Beg for the old clientSideRequired and serverSideRequired flags. Accepting all remote versions is stupid.
 */
@Mod(modid="NBTEdit", name = "In-game NBTEdit",  version = "1.7.10", acceptableRemoteVersions="*")
public class NBTEdit {
	
	private static final String SEP = System.getProperty("line.separator");
	public static final NBTNodeSorter SORTER = new NBTNodeSorter();
	public static final PacketPipeline DISPATCHER = new PacketPipeline();
	public static final char SECTION_SIGN = '\u00A7';
	
	private static FileHandler logHandler = null;
	private static Logger logger = Logger.getLogger("NBTEdit");
	
	public static NamedNBT clipboard = null;
	public static boolean opOnly = true;
	
	@Instance("NBTEdit")
	private static NBTEdit instance;
	
	@SidedProxy(clientSide = "com.mcf.davidee.nbtedit.forge.ClientProxy", serverSide = "com.mcf.davidee.nbtedit.forge.CommonProxy")
	public static CommonProxy proxy;
	
	public static void log(Level l, String s){
		logger.log(l, s);
	}
	
	public static void throwing(String cls, String mthd, Throwable thr){
		logger.throwing(cls, mthd, thr);
	}
	
	public static void logTag(NBTTagCompound tag){
		NBTTree tree = new NBTTree(tag);
		String sb = "";
		for (String s : tree.toStrings()){
			sb += SEP + "\t\t\t"+ s;
		}
		NBTEdit.log(Level.FINE, sb);
	}
	
	public static SaveStates getSaveStates(){
		return instance.saves;
	}
	
	private SaveStates saves;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		ModMetadata modMeta = event.getModMetadata();
		modMeta.authorList = Arrays.asList(new String[] { "Davidee" });
		modMeta.autogenerated = false;
		modMeta.credits = "Thanks to Mojang, Forge, and all your support.";
		modMeta.description = "Allows you to edit NBT Tags in-game.\nPlease visit the URL above for help.";
		modMeta.url = "http://www.minecraftforum.net/topic/1558668-151/";
		
		
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		opOnly = config.get("General", "opOnly", true, "true if only Ops can NBTEdit; false allows users in creative mode to NBTEdit").getBoolean(true);
		config.save();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event){
		logger.setLevel(Level.ALL);
		
		try {
			File logfile = new File(proxy.getMinecraftDirectory(),"NBTEdit.log");
			if ((logfile.exists() || logfile.createNewFile()) && logfile.canWrite() && logHandler == null)
			{
				logHandler = new FileHandler(logfile.getPath());
				logHandler.setFormatter(new LogFormatter());
				logger.addHandler(logHandler);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		logger.fine("NBTEdit Initalized");
		saves = new SaveStates(new File(new File(proxy.getMinecraftDirectory(),"saves"),"NBTEdit.dat"));
		DISPATCHER.initialize();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		proxy.registerInformation();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		MinecraftServer server= event.getServer();
		ServerCommandManager serverCommandManager = (ServerCommandManager) server.getCommandManager();
		serverCommandManager.registerCommand(new CommandNBTEdit());
		logger.fine("Server Starting -- Added \"/nbtedit\" command");
	}
	
}