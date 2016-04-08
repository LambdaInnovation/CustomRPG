package cn.nolifem.state.item;

import cn.nolifem.state.ModuleState;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.core.LambdaLib;
import cn.lambdalib.networkcall.TargetPointHelper;
import cn.lambdalib.s11n.network.NetworkMessage;
import cn.lambdalib.s11n.network.NetS11nAdapterRegistry.RegNetS11nAdapter;
import cn.lambdalib.s11n.network.NetworkS11n;
import cn.lambdalib.s11n.network.NetworkS11n.ContextException;
import cn.lambdalib.s11n.network.NetworkS11n.NetS11nAdaptor;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.core.ModProps;
import cn.nolifem.state.PlayerItemStateBuffer;

@Registrant
public abstract class ItemState implements IAttributeContainer {
	
	public static final String CHANNEL = "item_state";
	
	@RegNetS11nAdapter(ItemState.class)
    public static NetS11nAdaptor<ItemState> adaptor = new NetS11nAdaptor<ItemState>() {
		@Override
		public void write(ByteBuf buf, ItemState obj) {
			NetworkS11n.serializeWithHint(buf, obj.getLivingBase(), EntityLivingBase.class);
			NetworkS11n.serializeWithHint(buf, obj.getStack().getItem(), Item.class);
			NetworkS11n.serializeWithHint(buf, obj.getStack().writeToNBT(new NBTTagCompound()), NBTTagCompound.class);
		}

		@Override
		public ItemState read(ByteBuf buf) throws ContextException {
			EntityLivingBase livingBase = NetworkS11n.deserializeWithHint(buf, EntityLivingBase.class);
			ItemStack stack = new ItemStack(NetworkS11n.deserializeWithHint(buf, Item.class));
			stack.readFromNBT(NetworkS11n.deserializeWithHint(buf, NBTTagCompound.class));
			return ModuleState.get(stack, livingBase);
		}
    };

	EntityLivingBase livingBase;
	ItemStack stack;
	
	boolean isTick;

	public ItemState(EntityLivingBase livingBase, ItemStack stack){
		this.livingBase = livingBase;
		this.stack = stack;
		
		this.isTick = false;
	}
	
	public void setTick(){
		this.isTick = true;
	}
	
	public boolean isTick(){
		return this.isTick;
	}
	
	public void tick(){}

	public static String getStackID(ItemStack stack){
		String uuid = "nothing";
		if(stack != null){
			NBTTagCompound tag = getTag(stack, ModProps.TAG);
			if(!tag.hasKey("UUID")){
				uuid = UUID.randomUUID().toString();
				tag.setString("UUID", uuid);
				stack.getTagCompound().setTag(ModProps.TAG, tag);
			}else{
				uuid = tag.getString("UUID");
			}
		}
		return uuid;
	}

	abstract void initState();
	
	public void setStack(ItemStack stack){
		this.stack = stack;
	}
	
	public ItemStack getStack(){
		return this.stack;
	}

	public EntityLivingBase getLivingBase(){
		return this.livingBase;
	}

	protected boolean isClient() {
        return getLivingBase().worldObj.isRemote;
    }
	
	//NetWork
	protected void sendMessage(String channel, Object ...params) {
        if (isClient()) {
            NetworkMessage.sendToServer(this, channel, params);
        } else {
            NetworkMessage.sendToAllAround(TargetPointHelper.convert(getLivingBase(), 12), this, channel, params);
        }
    }

	//DamageItem
	public int damageItem(int amount, EntityLivingBase e){
		int dmg = amount;
		if(stack.getMaxDamage() - stack.getItemDamage() < amount)
			dmg = stack.getMaxDamage() - stack.getItemDamage();
		stack.damageItem(dmg, e);
		return dmg;
	}

	////NBT
	/**Get a tag with specified key
	 * @param stack
	 * @return usable tag
	 */
	static NBTTagCompound getTag(ItemStack stack,String key) {
		if (stack.getTagCompound() == null){
			stack.setTagCompound(new NBTTagCompound());
		}
		NBTTagCompound tag = stack.getTagCompound().getCompoundTag(key);
		return tag;
	}

	/**Call if need to read
	 */
	final void readFromStack(){
		NBTTagCompound tag = getTag(stack, this.getClass().getSimpleName());
		readFromTag(tag);
	}
	
	/**Call if need to save
	 */
	final void saveToStack(){
		NBTTagCompound tag = getTag(stack, this.getClass().getSimpleName());
		saveToTag(tag);
		stack.getTagCompound().setTag(this.getClass().getSimpleName(), tag);
	}

	abstract void readFromTag(NBTTagCompound tag);
	abstract void saveToTag(NBTTagCompound tag);

}
