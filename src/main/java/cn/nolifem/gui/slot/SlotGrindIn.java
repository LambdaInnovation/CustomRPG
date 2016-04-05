package cn.nolifem.gui.slot;

import cn.nolifem.items.ItemCustomMelee;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotGrindIn extends Slot {

    public SlotGrindIn(IInventory inv, int slot, int x, int y) {
        super(inv, slot, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() != null && stack.getItem() instanceof ItemCustomMelee;
    }
}
