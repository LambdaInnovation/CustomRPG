package cn.nolifem.state.item;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import cn.lambdalib.core.LambdaLib;
import cn.lambdalib.s11n.network.NetworkMessage.Listener;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IStateContainer;
import cn.nolifem.attributes.item.MaterialType;
import cn.nolifem.gui.container.ContainerGrind;
import cn.nolifem.items.ItemCustomMelee;
import cn.nolifem.items.ItemCustomMisc;
import cn.nolifem.state.PlayerItemStateBuffer;

public class GrindToolState extends ItemState implements IInventory{
	
    private static void debug(Object msg) {
        LambdaLib.log.info("[TestEnv]" + msg);
    }
	
	private Map<String, IAttributeCR> attrMaps = new HashMap<String, IAttributeCR>();
	
	public static final int
		SLOT_SWORD_IN = ContainerGrind.SLOT_SWORD_IN,
		SLOT_HONE_IN = ContainerGrind.SLOT_HONE_IN,
		SLOT_SWORD_OUT = ContainerGrind.SLOT_SWORD_OUT;
	public static final double GRIND_SPEED = 0.2;
	
	//
	double progress;
	double markpos;
	boolean grinding;
	NBTTagCompound tag;
	//
	public ItemStack[] inventory;
	
	Random r = new Random();

	//State getter
	public static GrindToolState get(EntityPlayer player, ItemStack stack, IStateContainer container){
		String uuid = getStackID(stack);
		GrindToolState state = (GrindToolState) container.getStateMap().get(uuid);
		if(state == null){
			state = new GrindToolState(player, stack);
			container.getStateMap().put(uuid, state);
		}
		state.setStack(stack);
		return state;	
	}
	
	
	//State
	public GrindToolState(EntityPlayer player, ItemStack stack){
		super(player, stack);
		initState();
		readFromStack();
		saveToStack();
	}

	@Override
	void initState() {
		this.setTick();
		this.inventory = new ItemStack[3];
		this.progress = 0;
		this.grinding = false;
		tag = getTag(stack, getClass().getSimpleName());
	}
	
	public void tick(){
		if(grinding){
			progress += GRIND_SPEED;
			if(progress >= 10 || this.getStackInSlot(SLOT_SWORD_IN) == null){
				endGrind();
			}
			this.markDirty();
		}
	}

	@Override
	void readFromTag(NBTTagCompound tag) {
		this.progress = tag.getDouble("Progress");
		this.markpos = tag.getDouble("MarkPos");
		this.grinding = tag.getBoolean("Grinding");
		readInvFromNBT(tag);
	}

	@Override
	void saveToTag(NBTTagCompound tag) {
		tag.setDouble("Progress", this.progress);
		tag.setDouble("MarkPos", this.markpos);
		tag.setBoolean("Grinding", this.grinding);
		writeInvToNBT(tag);

	}
	public boolean isHone(ItemStack stack){
		return stack != null && stack.getItem() instanceof ItemCustomMisc 
				&& ((ItemCustomMisc)stack.getItem()).getAttr(MaterialType.class) != null
				&& ((MaterialType)((ItemCustomMisc)stack.getItem()).getAttr(MaterialType.class)).getValue().equals("flint");
	}
	//
	public double getProgress(){
		return this.progress;
	}
	
	public double getMarkPos(){
		return this.markpos;
	}
	
	public boolean grinding(){
		return this.grinding;
	}
	
	// do works
	
	/**Only call at Client
	 */
	public void click(){
		if(!grinding)
			this.markpos = r.nextDouble() * 6 + 2;
		sendMessage(this.CHANNEL, grinding, markpos);
		this.doThings();
	}
	
	@Listener(channel = CHANNEL, side = Side.SERVER )
	public void onMessage(boolean grinding, double markpos){
		this.grinding = grinding;
		this.markpos = markpos;
		this.doThings();
	}
	
	public void doThings(){
		if(grinding)
			this.endGrind();
		else
			this.startGrind();
	}
	
	public void startGrind(){
		if(isClient())
			getPlayer().playSound("minecraft:random.click", 1.0f, 1.0f);
		ItemStack hone = this.getStackInSlot(SLOT_HONE_IN);
		if(this.getStackInSlot(SLOT_SWORD_IN) != null && hone != null && hone.stackSize > 0 && this.getStackInSlot(SLOT_SWORD_OUT) == null){
			hone.stackSize--;
			this.grinding = true;
		}
		this.markDirty();
	}
	
	public void endGrind(){
		ItemStack stack = this.getStackInSlot(SLOT_SWORD_IN);
		if(this.grinding && stack != null && stack.getItem() instanceof ItemCustomMelee){
			MeleeState mstate = (MeleeState) PlayerItemStateBuffer.get(getPlayer()).getItemState(stack);
			System.out.println("pro:" + progress);
			System.out.println("mrk:" + markpos);
			if(progress >= markpos - 0.5 && progress <= markpos + 0.5){
				System.out.println("Best Grinded!");
				mstate.dealGrind(1);
			}else if(progress >= markpos - 1 && progress <= markpos + 1){
				System.out.println("Oops");
				mstate.dealGrind(3);
			}else{
				System.out.println("Fine Grinded!");
				mstate.dealGrind(2);
			}
			this.setInventorySlotContents(SLOT_SWORD_OUT, stack);
			this.setInventorySlotContents(SLOT_SWORD_IN, null);	
		}
		this.grinding = false;
		this.progress = 0;
	}
	//inv
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
        	if (stack.stackSize <= amt) {
                	 setInventorySlotContents(slot, null);
        	} else {
        		stack = stack.splitStack(amt);
        		if (stack.stackSize == 0) {
        			setInventorySlotContents(slot, null);

        		}
        	}
        }
        return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
                setInventorySlotContents(slot, null);
        }
        return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;
        
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
                stack.stackSize = getInventoryStackLimit();
        }
        this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return "Grind";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {
		for (int i = 0; i < getSizeInventory(); ++i){
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) {
				inventory[i] = null;
			}
		}
		saveToStack();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
		this.endGrind();
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		System.out.println(slot == SLOT_SWORD_OUT ?  false : slot == SLOT_HONE_IN ? isHone(stack) : true);
		return slot == SLOT_SWORD_OUT ?  false : slot == SLOT_HONE_IN ? isHone(stack) : true;
	}

	public void readInvFromNBT(NBTTagCompound tag){
		NBTTagList items = tag.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

		for (int i = 0; i < items.tagCount(); ++i)
		{
			NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
			int slot = item.getInteger("Slot");

			if (slot >= 0 && slot < getSizeInventory()) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(item);
			}
		}
	}

	public void writeInvToNBT(NBTTagCompound tag)
	{
		NBTTagList items = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); ++i)
		{
			if (getStackInSlot(i) != null)
			{
				NBTTagCompound item = new NBTTagCompound();
				item.setInteger("Slot", i);
				getStackInSlot(i).writeToNBT(item);

				items.appendTag(item);
			}
		}
		tag.setTag("ItemInventory", items);
	}
	
	//Interface
	@Override
	public Map<String, IAttributeCR> getAttrMap() {
		return this.attrMaps;
	}

	@Override
	public void initAttr() {}
}
