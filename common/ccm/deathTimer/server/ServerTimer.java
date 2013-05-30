package ccm.deathTimer.server;

import java.util.EnumSet;
import java.util.HashMap;

import ccm.deathTimer.Config;
import ccm.deathTimer.timerTypes.ITimerBase;
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
    public HashMap<String, ITimerBase> timerList = new HashMap<String, ITimerBase>();
    
    public void addTimer(ITimerBase data)
    {
        timerList.put(data.getLabel(), data);
        data.sendAutoUpdate();
    }
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        for (ITimerBase data : timerList.values())
        {
            data.tick();
            
            if (data.getTime() % Config.updateInteval == 0 || data.getTime() <= 0)
                data.sendAutoUpdate();
            if (data.getTime() <= 0)
                timerList.remove(data.getLabel());
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