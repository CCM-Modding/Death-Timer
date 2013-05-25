package ccm.deathTimer.client;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.event.ForgeSubscribe;
import ccm.deathTimer.Config;
import ccm.deathTimer.timerTypes.TimerData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The timer HUD renderer class
 * @author Dries007
 *
 */
@SideOnly(Side.CLIENT)
public class HUD
{
    public static final String HEADER = "--- Timers ---";
    public static final String FOOTER = "-------------";
    
    @ForgeSubscribe
    public void render(Text event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null || player.worldObj == null) return;
        
        boolean b = Config.useRightSide ? (event.right.size() != 0) : (event.left.size() != 0);
        ArrayList<String> text = Config.useRightSide ? event.right : event.left;
        
        /* Header */
        if (b && ClientTimer.getInstance().timerList.size() != 0) text.add(HEADER);
        
        /* Add all the timers */
        for (TimerData data : ClientTimer.getInstance().timerList.values())
            text.addAll(data.getTimerString(player));
        
        /* Footer */
        if (b && ClientTimer.getInstance().timerList.size() != 0) text.add(FOOTER);
        
        /*
         * OLD CODE: 
         * TODO replace.
         * 
        if (ClientDeathTimer.enable)
        {
            if (b) event.left.add("----------------");
            event.left.add("T- item despawn:");
            event.left.add(FunctionHelper.timeColor(ClientDeathTimer.time) + FunctionHelper.parseTime(ClientDeathTimer.time));
            event.left.add(ClientDeathTimer.getDistance() + "m " + ClientDeathTimer.getArrow());
            if (b) event.left.add("----------------");
        }
        */
    }
}
