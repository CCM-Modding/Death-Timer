package ccm.deathTimer.commands;

import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.timerTypes.TimerData;
import ccm.deathTimer.utils.lib.Sound;
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
    public void processCommand(ICommandSender sender, String[] args)
    {
        /*
         * DEBUG
         * TODO: Make cmd structure
         */
        TimerData data = new TimerData("DIE!", 15);
        
        data.useSound = true;
        data.sound = Sound.mob_silverfish_kill.toString();
        data.soundPitch = 0.1F;
        data.soundVolume = 10F;
        
        ServerTimer.getInstance().addTimer(data);
    }
}