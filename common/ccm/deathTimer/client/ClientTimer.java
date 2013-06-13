package ccm.deathTimer.client;

import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.player.EntityPlayer;
import ccm.deathTimer.timerTypes.IStopwatchBase;
import ccm.deathTimer.timerTypes.ITimerBase;
import ccm.deathTimer.utils.FunctionHelper;
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
 * 
 * @author Dries007
 */
@SideOnly(Side.CLIENT)
public class ClientTimer implements IScheduledTickHandler, IPlayerTracker
{
    private static ClientTimer instance;
    
    public ClientTimer()
    {
        ClientTimer.instance = this;
        TickRegistry.registerScheduledTickHandler(this, Side.CLIENT);
        GameRegistry.registerPlayerTracker(this);
    }
    
    public static ClientTimer getInstance()
    {
        return ClientTimer.instance;
    }
    
    /*
     * Useful stuff starts here.
     */
    public ConcurrentHashMap<String, ITimerBase>     serverTimerList     = new ConcurrentHashMap<String, ITimerBase>();
    
    public ConcurrentHashMap<String, IStopwatchBase> serverStopwatchList = new ConcurrentHashMap<String, IStopwatchBase>();
    
    @Override
    public void tickStart(final EnumSet<TickType> type, final Object... tickData)
    {
        for (final ITimerBase data : this.serverTimerList.values())
        {
            data.tick();
            
            if (data.getTime() < 0)
            {
                this.serverTimerList.remove(data.getLabel());
                if (data.useSound()) FunctionHelper.playSound(data.getSoundName(), data.getSoundVolume(), data.getSoundPitch());
            }
        }
        
        for (final IStopwatchBase data : this.serverStopwatchList.values())
        {
            data.tick();
            
            if (data.getTime() < 0) this.serverTimerList.remove(data.getLabel());
        }
    }
    
    @Override
    public void tickEnd(final EnumSet<TickType> type, final Object... tickData)
    {}
    
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
    
    public void updateServerTimer(final ITimerBase data)
    {
        if (data.getTime() == -1)
            this.serverTimerList.remove(data.getLabel());
        else
            this.serverTimerList.put(data.getLabel(), data);
    }
    
    public void updateServerStopwatch(final IStopwatchBase data)
    {
        if (data.getTime() == -1)
            this.serverStopwatchList.remove(data.getLabel());
        else
            this.serverStopwatchList.put(data.getLabel(), data);
    }
    
    @Override
    public void onPlayerLogin(final EntityPlayer player)
    {   
        
    }
    
    @Override
    public void onPlayerLogout(final EntityPlayer player)
    {
        this.serverTimerList.clear();
        this.serverStopwatchList.clear();
    }
    
    @Override
    public void onPlayerChangedDimension(final EntityPlayer player)
    {   
        
    }
    
    @Override
    public void onPlayerRespawn(final EntityPlayer player)
    {   
        
    }
}