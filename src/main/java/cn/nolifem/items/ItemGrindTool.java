package cn.nolifem.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.annoreg.mc.gui.GuiHandlerBase;
import cn.lambdalib.annoreg.mc.gui.RegGuiHandler;
import cn.nolifem.CustomRPG;
import cn.nolifem.api.IStateContainer;
import cn.nolifem.api.IStateItem;
import cn.nolifem.core.ModProps;
import cn.nolifem.gui.GuiGrind;
import cn.nolifem.gui.container.ContainerGrind;
import cn.nolifem.state.PlayerItemStateBuffer;
import cn.nolifem.state.item.GrindToolState;
import cn.nolifem.util.Lang;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Registrant
public class ItemGrindTool extends Item implements IStateItem<GrindToolState>{

	@RegGuiHandler
    public static GuiHandlerBase guiHandler = new GuiHandlerBase() {
        @SideOnly(Side.CLIENT)
        @Override
        protected Object getClientContainer(EntityPlayer player, World world, int x, int y, int z) {
            ContainerGrind container = (ContainerGrind) getServerContainer(player, world, x, y, z);
            return container == null ? null : new GuiGrind(container);
        }
        
        @Override
        protected Object getServerContainer(EntityPlayer player, World world, int x, int y, int z) {
            return player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemGrindTool ?
            		new ContainerGrind(player, ((GrindToolState) PlayerItemStateBuffer.get(player).getItemState(player.getHeldItem())) ) : null;
        }
    };

	public String name;
	
	public ItemGrindTool(String name){
		this.name = name;
		setUnlocalizedName(ModProps.PREFIX + this.name);
		setTextureName(ModProps.RESOURCE + this.name);
		setMaxStackSize(1);
		setCreativeTab(CustomRPG.tcct);
	}
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		if(guiHandler != null && !player.isSneaking()) {
	        if(!world.isRemote)
	            guiHandler.openGuiContainer(player, world, player.serverPosX, player.serverPosY, player.serverPosZ);
	    }
        return stack;
    }
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) {
		arraylist.add(Lang.translate(ModProps.lore(this.getClass().getSimpleName())));
	}

	@Override
	public GrindToolState getState(EntityPlayer player, ItemStack stack, IStateContainer container) {
		return GrindToolState.get(player, stack, container);
	}
}
