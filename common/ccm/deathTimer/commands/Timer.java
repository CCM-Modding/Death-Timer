package ccm.deathTimer.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.timerTypes.BasicTimer;
import ccm.deathTimer.timerTypes.PointTimer;

public class Timer extends CommandBase
{
    public int getRequiredPermissionLevel()
    {
        return 4;
    }
    
    @Override
    public String getCommandName()
    {
        return "timer";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        /*
         * DEBUG
         * TODO: Make cmd structure
         */
        {
            BasicTimer data = new BasicTimer();
            
            data.label = "TimerTest";
            data.time = 15;
            
            ServerTimer.getInstance().addTimer(data);
        }
        {
            PointTimer data = new PointTimer();
            
            data.label = "DeathPoint";
            data.time = 15;
            EntityPlayer player = ((EntityPlayer) sender);
            data.X = (int) player.posX;
            data.Y = (int) player.posY;
            data.Z = (int) player.posZ;
            
            ServerTimer.getInstance().addTimer(data);
        }
    }
}