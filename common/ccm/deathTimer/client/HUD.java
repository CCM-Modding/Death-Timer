package ccm.deathTimer.client;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.event.ForgeSubscribe;
import ccm.deathTimer.Config;
import ccm.deathTimer.timerTypes.IStopwatchBase;
import ccm.deathTimer.timerTypes.ITimerBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The timer HUD renderer class
 * 
 * @author Dries007
 */
@SideOnly(Side.CLIENT)
public class HUD
{
    public static final String THEADER = "---  Timers  ---";
    public static final String SHEADER = "--- Stopwatches ---";
    
    @ForgeSubscribe
    public void render(final Text event)
    {
        final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player == null || player.worldObj == null) return;
        
        this.doTimers(event, player);
        this.doStopwathes(event, player);
    }
    
    private void doTimers(final Text event, final EntityPlayer player)
    {
        final ArrayList<String> text = Config.useRightSide ? event.right : event.left;
        
        if (!ClientTimer.getInstance().serverTimerList.isEmpty()) text.add(HUD.THEADER);
        /* Add all the timers */
        for (final ITimerBase data : ClientTimer.getInstance().serverTimerList.values())
            text.addAll(data.getTimerString(player));
    }
    
    private void doStopwathes(final Text event, final EntityPlayer player)
    {
        final ArrayList<String> text = Config.useRightSide ? event.right : event.left;
        
        if (!ClientTimer.getInstance().serverStopwatchList.isEmpty()) text.add(HUD.SHEADER);
        /* Add all the timers */
        for (final IStopwatchBase data : ClientTimer.getInstance().serverStopwatchList.values())
            text.addAll(data.getTimerString(player));
    }
}
