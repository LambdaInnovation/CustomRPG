package cn.nolifem.attributes.effect.buffPlacer;

import cn.lambdalib.s11n.SerializeIncluded;
import cn.nolifem.api.attributes.BuffPlacer;
import cn.nolifem.event.PlayerAttackEvent;
import cn.nolifem.state.EntityState;
import cn.nolifem.util.Lang;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Nolife_M on 2016/3/28.
 */
public class HealthStealth extends BuffPlacer {

    @SerializeIncluded
    private double percent = 0.0;

    public void place(PlayerAttackEvent e){
        System.out.println("DMG:" + e.dmg);
        System.out.println("Stealth Percent:" + percent);
        double amount = e.dmg * percent;
        double result = e.entityPlayer.getHealth() + amount;
        if(result > e.entityPlayer.getMaxHealth()){
            result = e.entityPlayer.getMaxHealth();
        }
        System.out.println("Stealth HP:" + amount);
        e.entityPlayer.setHealth((float) result);
    }

    @Override
    public void addInfo(List arraylist) {
        arraylist.add(EnumChatFormatting.DARK_GREEN +  Lang.translateFormatted(super.getLang(), (this.percent*100.0 + "%")));
    }

    @Override
    public <T> void addToDealer(T dealer){
        if(dealer instanceof EntityState){
            ((EntityState) dealer).addBuffPlacer(this);
        }
    }
    public int getDisplayPreference(){ return 3;}

    @Override
    public int getApplyPreference() { return 0; }
}

