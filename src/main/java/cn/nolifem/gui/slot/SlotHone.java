package cn.nolifem.gui.slot;

import cn.nolifem.state.item.GrindToolState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHone extends Slot {

	private GrindToolState state;
	
    public SlotHone(GrindToolState state, int slot, int x, int y) {
        super(state, slot, x, y);
        this.state = state;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return (stack != null && state.isHone(stack));
    }
}
