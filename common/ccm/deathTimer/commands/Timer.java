package ccm.deathTimer.commands;

import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.timerTypes.TimerData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

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
    public void processCommand(ICommandSender icommandsender, String[] astring)
    {
        /*
         * DEBUG
         * TODO: Make cmd structure
         */
        TimerData data = new TimerData("LOL", 25);
        
        data.point = false;
        
        ServerTimer.getInstance().addTimer(data);
    }
}