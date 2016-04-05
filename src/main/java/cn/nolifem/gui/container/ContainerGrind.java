/**
* Copyright (c) Lambda Innovation, 2013-2016
* This file is part of the AcademyCraft mod.
* https://github.com/LambdaInnovation/AcademyCraft
* Licensed under GPLv3, see project root for more information.
*/
package cn.nolifem.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cn.nolifem.gui.slot.SlotGrindIn;
import cn.nolifem.gui.slot.SlotGrindOut;
import cn.nolifem.gui.slot.SlotHone;
import cn.nolifem.items.ItemCustomMelee;
import cn.nolifem.state.item.GrindToolState;

/**
 * @author WeAthFolD
 */
public class ContainerGrind extends Container {
    
    public static final int SLOT_SWORD_IN = 0, SLOT_HONE_IN = 1, SLOT_SWORD_OUT = 2;

    public final EntityPlayer player;
    public final GrindToolState state;

    public ContainerGrind(EntityPlayer player, GrindToolState state) {
        this.player = player;
        this.state = state;
        initInventory();
    }
    
	public void click() {
		Slot slot = (Slot)this.inventorySlots.get(SLOT_SWORD_IN);
		slot.onSlotChanged();
	}
    
    private void initInventory() {
        this.addSlotToContainer(new SlotGrindIn(state, SLOT_SWORD_IN, 48, 35));
        this.addSlotToContainer(new SlotHone(state, SLOT_HONE_IN, 75, 13));
        this.addSlotToContainer(new SlotGrindOut(state, SLOT_SWORD_OUT, 108, 35));
        
        InventoryPlayer inv = player.inventory;
        int STEP = 18;
        
        for(int i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(inv, i, 8 + i * STEP, 143));
        }
        
        for(int i = 1; i < 4; ++i) {
            for(int j = 0; j < 9; ++j) {
                int slot = (4 - i) * 9 + j;
                addSlotToContainer(new Slot(inv, slot, 8 + j * STEP, 139 - i * STEP));
            }
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int id) {
        ItemStack stack = null;
        Slot slot = (Slot)this.inventorySlots.get(id);
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();

            if (id < 3) { //tileInv->playerInv
                if (!this.mergeItemStack(stack1, SLOT_SWORD_OUT + 1, this.inventorySlots.size(), true))
                    return null;
            } else { 
                if(stack.getItem() instanceof ItemCustomMelee) {
                    if(!this.mergeItemStack(stack1, SLOT_SWORD_IN, SLOT_SWORD_IN + 1, false))
                        return null;
                } else if(state.isHone(stack1)) {
                    if(!this.mergeItemStack(stack1, SLOT_HONE_IN, SLOT_HONE_IN + 1, false))
                        return null;
                } else
                    return null;
            }

            if (stack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }
            slot.onPickupFromSlot(player, stack);
        }

        return stack;
    }
    
    public void onContainerClosed(EntityPlayer player){
        super.onContainerClosed(player);
        for (int i = 0; i < this.state.getSizeInventory(); ++i){
            ItemStack itemstack = this.state.getStackInSlotOnClosing(i);
            if (itemstack != null){
            	player.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }
        state.closeInventory();
    }
    
    @Override
	public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
		if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
			return null;
		}
		return super.slotClick(slot, button, flag, player);
	}
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
