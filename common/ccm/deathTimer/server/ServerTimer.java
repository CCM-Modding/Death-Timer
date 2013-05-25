package ccm.deathTimer.server;

import java.util.ArrayList;
import java.util.EnumSet;

import ccm.deathTimer.utils.TimerData;
import ccm.deathTimer.utils.lib.Archive;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ServerTimer implements IScheduledTickHandler
{
    public ServerTimer()
    {
        instance = this;
        TickRegistry.registerScheduledTickHandler(this, Side.SERVER);
    }
    
    public static ServerTimer getInstance()
    {
        return instance;
    }
    
    private static ServerTimer instance;
    private ArrayList<TimerData> timerList = new ArrayList<TimerData>();
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        for (TimerData data : timerList)
        {
            data.time --;
            if (data.time % data.updateInteval == 0)
            {
                if (data.dimOnly) PacketDispatcher.sendPacketToAllInDimension(data.getPayload(), data.dim);
                else PacketDispatcher.sendPacketToAllPlayers(data.getPayload());
            }
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