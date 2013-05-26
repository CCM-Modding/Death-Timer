package ccm.deathTimer.server;

import java.util.EnumSet;
import java.util.HashMap;

import ccm.deathTimer.timerTypes.TimerData;
import ccm.deathTimer.utils.lib.Archive;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ServerTimer implements IScheduledTickHandler
{
    private static ServerTimer instance;
    public ServerTimer()
    {
        instance = this;
        TickRegistry.registerScheduledTickHandler(this, Side.SERVER);
    }
    
    public static ServerTimer getInstance()
    {
        return instance;
    }
    
    /*
     * Useful stuff starts here.
     */
    public HashMap<String, TimerData> timerList = new HashMap<String, TimerData>();
    
    public void addTimer(TimerData data)
    {
        timerList.put(data.label, data);
        data.sendUpdate();
    }
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        for (TimerData data : timerList.values())
        {
            data.time --;
            
            if (data.time % data.updateInteval == 0 || data.time <= 0)
                data.sendUpdate();
            if (data.time <= 0)
                timerList.remove(data.label);
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {}

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.SERVER);
    }

    @Override
    public String getLabel()
    {
        return Archive.MOD_NAME + "-Server";
    }

    @Override
    public int nextTickSpacing()
    {
        return 19;
    }
}