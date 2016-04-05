package cn.nolifem.gui;

import net.minecraft.util.ResourceLocation;
import cn.lambdalib.cgui.gui.CGuiScreenContainer;
import cn.lambdalib.cgui.gui.Widget;
import cn.lambdalib.cgui.gui.WidgetContainer;
import cn.lambdalib.cgui.gui.component.ProgressBar;
import cn.lambdalib.cgui.gui.component.Transform;
import cn.lambdalib.cgui.gui.event.FrameEvent;
import cn.lambdalib.cgui.gui.event.LeftClickEvent;
import cn.lambdalib.cgui.xml.CGUIDocument;
import cn.nolifem.gui.container.ContainerGrind;
import cn.nolifem.state.item.GrindToolState;

public class GuiGrind extends CGuiScreenContainer {

	static final WidgetContainer document = CGUIDocument.panicRead(new ResourceLocation("customrpg:guis/grind.xml"));
	
	private final GrindToolState state;
	
    Widget main;
    
    public GuiGrind(ContainerGrind c) {
        super(c);
        state = c.state;
        init();
    }
	
    void init() {
        main = document.getWidget("main").copy();
        
        main.getWidget("button_grind").listen(LeftClickEvent.class, (w, e) -> {
        	state.click();
        });
        
        main.getWidget("mark_grind").listen(FrameEvent.class, (w, e) -> {
            w.<Transform>getComponent("Transform").pivotX = state.getMarkPos() * -9.6D;
        });
        
        main.getWidget("prog_grind").listen(FrameEvent.class, (w, e) -> {
            w.<Transform>getComponent("Transform").pivotX = state.getProgress() * -11.0D;
        });
        
        gui.addWidget(main);
    }
    
    @Override
    public void onGuiClosed() {
    	super.onGuiClosed();
    	this.state.closeInventory();
    }
}
