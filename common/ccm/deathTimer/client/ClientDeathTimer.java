package ccm.deathTimer.client;

import java.util.EnumSet;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import ccm.deathTimer.Config;
import ccm.deathTimer.utils.FunctionHelper;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientDeathTimer implements IScheduledTickHandler
{
    public static boolean enable;
    public static int time;
    
    @ForgeSubscribe
    public void handleDeath(PlayerDropsEvent e)
    {
        if (!Config.enableDeathTimer) return;
        
        enable = true;
        time = 60 * 5;
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        if (time != 0)
        {
            time = time - 30;
        }
        else if (enable)
        {
            enable = false;
            if (Config.enableDeathSound) FunctionHelper.playDragonSound();
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
        return "DeathTimer-Client";
    }

    @Override
    public int nextTickSpacing()
    {
        return 19;
    }
}
