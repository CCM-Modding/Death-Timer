package ccm.deathTimer.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.timerTypes.BasicTimer;
import ccm.deathTimer.timerTypes.PointTimer;

/**
 * The main (and only) command.
 * You need OP to change server wide settings,
 * Cient only changes are send back via packets and are saved in the client config.
 * 
 * TODO: Make command.
 * 
 * @author Dries007
 *
 */
public class Timer extends CommandBase
{
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
    
    @Override
    public String getCommandName()
    {
        return "timer";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        boolean op = sender instanceof EntityPlayer ? MinecraftServer.getServer().getConfigurationManager().getOps().contains(sender.getCommandSenderName()) : true;
        
        /* TODO: DEBUG */
        
        if (!op) return;
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