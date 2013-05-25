package ccm.deathTimer.client;

import java.util.EnumSet;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

import ccm.deathTimer.timerTypes.TimerData;
import ccm.deathTimer.utils.lib.Archive;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Does the timing client side. Receives updates from the server and updates the timers appropriately.
 * @author Dries007
 *
 */
@SideOnly(Side.CLIENT)
public class ClientTimer implements IScheduledTickHandler,  IPlayerTracker
{
    private static ClientTimer instance;
    public ClientTimer()
    {
        instance = this;
        TickRegistry.registerScheduledTickHandler(this, Side.CLIENT);
        GameRegistry.registerPlayerTracker(this);
    }
    
    public static ClientTimer getInstance()
    {
        return instance;
    }
    
    /*
     * Useful stuff starts here.
     */
    public HashMap<String, TimerData> timerList = new HashMap<String, TimerData>();
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        for (TimerData data : timerList.values())
        {
            data.time --;
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel()
    {
        return Archive.MOD_NAME + "-Client";
    }

    @Override
    public int nextTickSpacing()
    {
        return 19;
    }

    public void updateTimer(TimerData data)
    {
        if (data.time == -1)
            timerList.remove(data.label);
        else
            timerList.put(data.label, data);
    }

    @Override
    public void onPlayerLogin(EntityPlayer player)
    {
        timerList.put("LOGIN TEST", new TimerData("LOGIN TEST", 300));
    }

    @Override
    public void onPlayerLogout(EntityPlayer player)
    {
        timerList.clear();
    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player)
    {
        
    }

    @Override
    public void onPlayerRespawn(EntityPlayer player)
    {
        
    }
}