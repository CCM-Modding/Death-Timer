package ccm.deathTimer.server;

import java.util.EnumSet;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import ccm.deathTimer.Config;
import ccm.deathTimer.timerTypes.IStopwatchBase;
import ccm.deathTimer.timerTypes.ITimerBase;
import ccm.deathTimer.utils.lib.Archive;
import ccm.nucleum.helper.DataHelper;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/**
 * Does the timing server side. Sends updates to the client when needed. Removed expired timers too.
 * 
 * @author Dries007
 */
public class ServerTimer implements IScheduledTickHandler
{
    private static ServerTimer instance;
    
    public ServerTimer()
    {
        ServerTimer.instance = this;
        TickRegistry.registerScheduledTickHandler(this, Side.SERVER);
    }
    
    public static ServerTimer getInstance()
    {
        return ServerTimer.instance;
    }
    
    /*
     * Useful stuff starts here.
     */
    public ConcurrentHashMap<String, IStopwatchBase> stopwatchList = new ConcurrentHashMap<String, IStopwatchBase>();
    
    public void addStopwatch(final IStopwatchBase data)
    {
        this.stopwatchList.put(data.getLabel(), data);
        data.sendAutoUpdate();
    }
    
    public ConcurrentHashMap<String, ITimerBase> timerList = new ConcurrentHashMap<String, ITimerBase>();
    
    public void addTimer(final ITimerBase data)
    {
        this.timerList.put(data.getLabel(), data);
        data.sendAutoUpdate();
    }
    
    @Override
    public void tickStart(final EnumSet<TickType> type, final Object... tickData)
    {
        for (final ITimerBase data : this.timerList.values())
        {
            data.tick();
            
            if (data.getTime() <= 0) data.sendAutoUpdate();
            if (data.getTime() % Config.updateInteval == 0) data.sendAutoUpdate();
            if (data.getTime() <= 0) this.timerList.remove(data.getLabel());
        }
        
        for (final IStopwatchBase data : this.stopwatchList.values())
        {
            data.tick();
            
            if (data.getTime() < 0) data.sendAutoUpdate();
            if (data.getTime() % Config.updateInteval == 0) data.sendAutoUpdate();
            if (data.getTime() < 0) this.stopwatchList.remove(data.getLabel());
        }
    }
    
    @Override
    public void tickEnd(final EnumSet<TickType> type, final Object... tickData)
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

    public void load()
    {
        NBTTagCompound data = DataHelper.readData(Archive.MOD_ID, "timers");
        
        final NBTTagList timers = data.getTagList("timers");        
        for (int i = 0; i < timers.tagCount(); i++)
        {
            try
            {
                final NBTTagCompound timerTag = (NBTTagCompound) timers.tagAt(i);
                Class<?> c = Class.forName(timerTag.getString("class"));
                ITimerBase timer = (ITimerBase) c.newInstance();
                timer.fromNBT(timerTag);
                addTimer(timer);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                continue;
            }
        }
        
        final NBTTagList stopwatches = data.getTagList("stopwatches");        
        for (int i = 0; i < stopwatches.tagCount(); i++)
        {
            try
            {
                final NBTTagCompound stopwatchTag = (NBTTagCompound) stopwatches.tagAt(i);
                Class<?> c = Class.forName(stopwatchTag.getString("class"));
                IStopwatchBase stopwatch = (IStopwatchBase) c.newInstance();
                stopwatch.fromNBT(stopwatchTag);
                addStopwatch(stopwatch);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                continue;
            }
        }
    }

    public void save()
    {
        NBTTagCompound data = DataHelper.readData(Archive.MOD_ID, "timers");
        
        NBTTagList timers = new NBTTagList("timers");
        for (ITimerBase timer : timerList.values())
            timers.appendTag(timer.toNBT());
        data.setTag(timers.getName(), timers);
        
        NBTTagList stopwatches = new NBTTagList("stopwatches");
        for (IStopwatchBase timer : stopwatchList.values())
            stopwatches.appendTag(timer.toNBT());
        data.setTag(stopwatches.getName(), stopwatches);
        
        DataHelper.saveData(Archive.MOD_ID, "timers", data);
    }
}