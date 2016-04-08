package cn.nolifem.attributes.effect;

import cn.nolifem.attributes.BaseAttributeCR;
import cn.nolifem.event.PlayerAttackEvent;

/**
 * Created by Nolife_M on 2016/3/28.
 */
public abstract class BuffPlacer extends BaseAttributeCR {

    public abstract void place(PlayerAttackEvent e);
}

