package cn.nolifem.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotGrindOut extends Slot {

    public SlotGrindOut(IInventory inv, int slot, int x, int y) {
        super(inv, slot, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

}
