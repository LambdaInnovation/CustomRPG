package cn.nolifem.state.item;

import java.util.*;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cn.nolifem.api.IAttributeCR;
import cn.nolifem.api.IAttributeContainer;
import cn.nolifem.api.IStateBuffer;
import cn.nolifem.api.attributes.BaseAttributeCR;
import cn.nolifem.attributes.Material;
import cn.nolifem.attributes.general.PhysicalDamage;
import cn.nolifem.attributes.item.MaterialType;
import cn.nolifem.attributes.item.Sharpness;

public class MeleeState extends ItemState implements IAttributeContainer {
	
	//private static HashMap<String, MeleeState> stateMap = new HashMap<String, MeleeState>();
	
	private int STEP = 5;
	
	private Map<String, IAttributeCR> attrMaps = new HashMap<>();
	private List<IAttributeCR> attrInfoList = new ArrayList<>();

	public static Optional<MeleeState> get(EntityLivingBase living, ItemStack stack, IStateBuffer buffer){
		Optional<MeleeState> state;
		if(living instanceof  EntityPlayer && buffer != null){
			String uuid = getStackID(stack);
			state = Optional.ofNullable(
					(MeleeState) buffer.getStateMap().get(uuid)
			);
			if(!state.isPresent()){
				state = Optional.of( new MeleeState(living, stack) );
				buffer.getStateMap().put(uuid, state.get());
			}
			state.get().setStack(stack);
		}
		state = Optional.of( new MeleeState(living, stack) );
		return state;
	}
	
	public MeleeState(EntityLivingBase living, ItemStack stack){
		super(living, stack);
		initState();
		readFromStack();
		saveToStack();
	}
	
	//////////////
	void initState(){
		if(stack.getItem() instanceof IAttributeContainer){
			this.attrMaps = ((IAttributeContainer)stack.getItem()).copyAttrMap();
			Sharpness sattr = new Sharpness();
			IAttributeCR mtype = ((IAttributeContainer)stack.getItem()).getAttr(MaterialType.class);
			if(mtype != null){
				sattr.sharpnessDecrease = Material.get(((MaterialType)mtype).getValue()).sharpnessDecrease;
			}
			this.putAttr(sattr);
			//System.out.println(this.getAttrMapAsList());
		}
	}

	//info
	public void addInfo(List arraylist){
		//add State info
		readFromStack();
		for(IAttributeCR att : this.getAttrMapAsList()){
			if(att instanceof BaseAttributeCR && !(att instanceof PhysicalDamage))
				((BaseAttributeCR)att).addInfo(arraylist);
		}
	}
	
	//dealing
	public void dealAttack(EntityPlayer player){
		System.out.println("Dealing-Attack");
		if(!((Sharpness)getAttr(Sharpness.class)).dealAttack(player)){
			System.out.println("Damaging-Weapon");
			stack.damageItem(1, player);
		}
		saveToStack();
	}
	
	public void dealGrind(int multi){
		Sharpness shpns = ((Sharpness)getAttr(Sharpness.class));
		shpns.dealGrind(damageItem((int) Math.round(100 - shpns.sharpness), getLivingBase()));
		saveToStack();
	}
	
	public int damageItem(int amount, EntityLivingBase e){
		int dmg = Math.round(amount/(float)STEP);
		System.out.println(dmg);
		if(stack.getMaxDamage() - stack.getItemDamage() < amount)
			dmg = stack.getMaxDamage() - stack.getItemDamage();
		stack.damageItem(dmg, e);
		return dmg*STEP;
	}

	//S/L
	void readFromTag(NBTTagCompound tag){
		if(tag.hasKey("Sharpness"))
			((Sharpness)getAttr(Sharpness.class)).sharpness = tag.getDouble("Sharpness");
	}

	void saveToTag(NBTTagCompound tag){
		tag.setDouble("Sharpness", ((Sharpness)getAttr(Sharpness.class)).sharpness);
	}

	//Interface
	@Override
	public void initAttr() {}

	@Override
	public Map<String, IAttributeCR> getAttrMap() {
		return this.attrMaps;
	}
}
