package ccm.deathTimer.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import ccm.deathTimer.api.SoftTimerAPI;

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
        boolean op = sender instanceof EntityPlayer ? MinecraftServer.getServer().isDedicatedServer() ? MinecraftServer.getServer().getConfigurationManager().getOps().contains(sender.getCommandSenderName()) : true : true;
        
        /* TODO: DEBUG */
        
        if (!op) return;
        
        SoftTimerAPI.newBasicTimer("TEST", 60 * 5);
        
        SoftTimerAPI.newPointTimer("TEST_POINT", 60 * 5, 0, 128, 0, 0);
        
        
        
    }
}