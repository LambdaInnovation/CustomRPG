package cn.nolifem.state;

import cn.lambdalib.annoreg.core.Registrant;
import cn.lambdalib.util.datapart.RegDataPart;
import cn.lambdalib.util.mc.ControlOverrider;
import cn.nolifem.CustomRPG;
import cn.nolifem.api.*;
import cn.nolifem.attributes.effect.BuffPlacer;
import cn.nolifem.attributes.general.*;
import cn.nolifem.attributes.player.Dexterity;
import cn.nolifem.attributes.player.PlayerAttribute;
import cn.nolifem.attributes.player.Proficient;
import cn.nolifem.attributes.player.Strength;
import cn.nolifem.event.PlayerAttackEvent;
import cn.nolifem.state.item.ItemState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.*;
import java.util.function.Function;


@Registrant
@RegDataPart(value=EntityPlayer.class)
public class PlayerState extends EntityState implements IAttributeContainer, IAttributeDealer {
	
	private int respawnUpdateTick = 10;

    private Random r = new Random();

	public int attackCD = 0;

	private Map<String, IAttributeCR> playerAttrMap = new HashMap<>();
	private Map<String, List<Function>> calcMapSIGMA = new HashMap<>();
	private Map<String, List<Function>> calcPAI = new HashMap<>();
	
	private List<IAttributeCR> attrList = new ArrayList<>();
    private List<IAttributeCR> buffPlacerList = new ArrayList<>();
	private List<IAttributeCR> buffList = new ArrayList<>();
	
	private final Class[] playerAttrs = new Class[]{
		Strength.class,
		Dexterity.class,
		Proficient.class	
	};
	
	private final Class[] generalAttrs = new Class[]{
			PhysicalDamage.class,
			Health.class,
			Vitality.class,
			Defence.class,
			AttackSpeed.class,
			MovementSpeed.class,
			PrepareSpeed.class,
			CriticalRate.class,
			CriticalMulti.class,
	};

	@Override
	public void initAttr() {
		try {
			//init all map
			for(Class clazz : this.playerAttrs){
				this.getAttrMap().put(clazz.getSimpleName(), (PlayerAttribute) CustomRPG.construct(clazz.getName()));
			}
		} catch (InstantiationException | IllegalAccessException| ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    
    public PlayerState() {
    	setTick(true);
    	setNBTStorage();
		initAttr();
	}
    
    public EntityPlayer getPlayer(){
    	return (EntityPlayer)this.getEntity();
    }
    
    public void tick() {
    	super.tick();
    	if(respawnUpdateTick > 0){
    		respawnUpdateTick--;
    		this.updateAttrListForCalc();
    	}
    	KeyBinding bind = Minecraft.getMinecraft().gameSettings.keyBindAttack;
    	ItemStack weapon = getPlayer().getHeldItem();

    	if(getPlayer().isSwingInProgress){
    		this.attacking();
    	}
    	//testing
    	((PlayerAttribute)this.getAttr(Strength.class)).setValue(40);
    	((PlayerAttribute)this.getAttr(Dexterity.class)).setValue(40);
    	((PlayerAttribute)this.getAttr(Proficient.class)).setValue(40);
    	    	
    	if(this.attackCD > 0){
    		this.attackCD--;
    		//System.out.println((isClient() ? "Client" : "Server") + "----" + this.attackCD);
    	}	
			
		if(isClient()){
			ControlOverrider.override("stop_attack", bind.getKeyCode());
			if(weapon == null || !(weapon.getItem() instanceof IAttributeContainer) || readyToAttack()){
				ControlOverrider.endOverride("stop_attack");
			}
		}
    }
    //FinalValue
    
    /**Attention! Before call this , make sure you have called (or need/no need to call) @link {@link #updateAttrListForCalc()}
     * @param clazz
     * @return
     */
    public double getFinalValue(Class<? extends GeneralAttribute> clazz){
    	return this.calc(clazz.getSimpleName(), 0.0D);
    }

	private void cleanAll(){
		this.getAttrListForCalc().clear();
		this.getCalcMapSIGMA().clear();
		this.getCalcMapPAI().clear();
		this.buffPlacerList.clear();
		this.buffList.clear();
	}

    public void updateAttrListForCalc(){
		cleanAll();

		//add player attrs
    	for(IAttributeCR attr : this.getAttrMapAsList()){
			attr.addToDealer(this);
    		/*this.getAttrListForCalc().add(attr);
            if(attr instanceof BuffPlacer)
                this.buffPlacerList.add(attr);
    		if(attr instanceof IOriginalModifier)
    			((IOriginalModifier) attr).applyOriginalModify(this, this.getPlayer());*/
    	}

		//add equipments attrs
    	ItemStack equipment;
    	for(int i=0; i < 5; i++){
    		equipment = getPlayer().getEquipmentInSlot(i);
    		if(equipment != null && equipment.getItem() instanceof IStateItem){
    			ItemState state = PlayerItemState.get(getPlayer()).getItemState(equipment);
    			for(IAttributeCR attr : state.getAttrMapAsList()){
					attr.addToDealer(this);
    				/*this.getAttrListForCalc().add(attr);
                    if(attr instanceof BuffPlacer)
                        this.buffPlacerList.add(attr);
    				if(attr instanceof IOriginalModifier)
    					((IOriginalModifier) attr).applyOriginalModify(this, this.getPlayer());*/
    	    	}
    		}
    	};
    }   

    //Deal Attacking
	@Override
    public boolean readyToAttack(){
    	return attackCD == 0;
    }

	private void attacking() {
		updateAttrListForCalc();

		this.attackCD = (int)(Math.round(this.getFinalValue(AttackSpeed.class)));
		if(!isClient())
			sync();
	}
	
	public double getAttackDmg() {
		double dmg;
		if(this.getPlayer().getHeldItem() == null || !(this.getPlayer().getHeldItem().getItem() instanceof IAttributeContainer))
			dmg = getFinalValue(BaseDamage.class);
		else
			dmg = getFinalValue(BaseDamage.class) + getFinalValue(PhysicalDamage.class);
        double cRate = getFinalValue(CriticalRate.class);
        double cMulti = getFinalValue(CriticalMulti.class);
        double rate = r.nextDouble();

        if(rate < cRate){
            dmg = dmg * cMulti;
            System.out.println("Critical Hit!" + "R:" + cRate + "M:" + cMulti);
        }
		System.out.println("get Dmg = " + dmg);
		return dmg;
	}

    public void applyBuffPlacer(PlayerAttackEvent e){
		System.out.println("applyingEffect");
		for(IAttributeCR effect : buffPlacerList){
			((BuffPlacer)effect).place(e);
		}
	}

	public void applyBuff(PlayerAttackEvent e){
		System.out.println("applyingEffect");
		for(IAttributeCR effect : buffPlacerList){
			((BuffPlacer)effect).place(e);
		}
	}

	//Defence
	public double reducePhyDmg(double dmg) {
		System.out.println("减免后伤害" + dmg * (1.0D - getFinalValue(Defence.class)));
		return dmg * (1.0D - getFinalValue(Defence.class));
	}
	
	//NBT
    public void fromNBT(NBTTagCompound tag) {
    	super.fromNBT(tag);
    	this.attackCD = tag.getInteger("AttackCD");
    	NBTTagCompound atag = tag.getCompoundTag("Attr");
    	((PlayerAttribute)getAttr(Strength.class)).setValue(atag.getDouble("Strength"));
    	((PlayerAttribute)getAttr(Dexterity.class)).setValue(atag.getDouble("Dexterity"));
    	((PlayerAttribute)getAttr(Proficient.class)).setValue(atag.getDouble("Proficient"));
    	updateAttrListForCalc();
    }

    public void toNBT(NBTTagCompound tag) {
    	tag.setInteger("AttackCD", this.attackCD);
    	NBTTagCompound atag = tag.getCompoundTag("Attr");
    	atag.setDouble("Strength", ((PlayerAttribute)getAttr(Strength.class)).getValue());
    	atag.setDouble("Dexterity", ((PlayerAttribute)getAttr(Dexterity.class)).getValue());
    	atag.setDouble("Proficient", ((PlayerAttribute)getAttr(Proficient.class)).getValue());
    	tag.setTag("Attr", atag);
    }
    
    //For Interface
	@Override
	public List<IAttributeCR> getAttrListForCalc() {
		return this.attrList;
	}
	
	@Override
	public Map<String, IAttributeCR> getAttrMap() {
		return this.playerAttrMap;
	}
	
	@Override
	public Map<String, List<Function>> getCalcMapSIGMA() {
		return this.calcMapSIGMA;
	}

	@Override
	public Map<String, List<Function>> getCalcMapPAI() {
		return this.calcPAI;
	}

}
