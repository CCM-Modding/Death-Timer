package ccm.deathTimer.client;

import java.util.EnumSet;

import ccm.deathTimer.utils.lib.Archive;

import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTimer implements IScheduledTickHandler
{
    
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        
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
}