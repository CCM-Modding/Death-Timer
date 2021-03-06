package ccm.deathTimer.commands;

import static ccm.deathTimer.utils.FunctionHelper.sendChat;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import ccm.deathTimer.api.HardStopwatchAPI;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.timerTypes.IStopwatchBase;

/**
 * You need OP to change server wide settings, Client only changes are send back via packets and are saved in the client config.
 *
 * @author Dries007
 */
public class Stopwatch extends CommandBase
{
    public final static String ListSyntax = "list";
    public final static String AddSyntax = "add <name> [true|false (personal)]";
    public final static String StopSyntax = "stop <name>";
    public final static String PauseSyntax = "pause <name>";
    public final static String OpArgs = "[" + Stopwatch.ListSyntax + "|" + Stopwatch.AddSyntax + "|" + Stopwatch.StopSyntax + "]";
    public final static String NonOpArgs = "[" + Stopwatch.ListSyntax + "|" + Stopwatch.StopSyntax + "|" + Stopwatch.StopSyntax + "|" + Stopwatch.PauseSyntax + "]";

    @Override
    public String getCommandName()
    {
        return "stopwatch";
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        final boolean op = sender instanceof EntityPlayer ? MinecraftServer.getServer().isDedicatedServer() ? MinecraftServer.getServer().getConfigurationManager().getOps().contains(sender.getCommandSenderName()) : true : true;

        if ((args.length == 1) && args[0].equalsIgnoreCase("help"))
        {
            sendChat(sender, EnumChatFormatting.GREEN + "Useage: /" + getCommandName() + " " + (op ? Stopwatch.OpArgs : Stopwatch.NonOpArgs));
            if (!op)
            {
                sendChat(sender, EnumChatFormatting.GREEN + "Note that you can only change your own stopwatches!");
            }
            return;
        }

        if ((args.length == 0) || args[0].equalsIgnoreCase("list"))
        {
            sendChat(sender, "--- Stopwatches ---");
            for (final IStopwatchBase timer : ServerTimer.getInstance().stopwatchList.values())
            {
                final StringBuilder sb = new StringBuilder();
                for (final String s : timer.getTimerString(sender))
                {
                    sb.append(EnumChatFormatting.RESET.toString() + EnumChatFormatting.WHITE.toString() + s);
                }
                sendChat(sender, sb.toString());
            }
            sendChat(sender, "-----------------");
        }
        else if (op && args[0].equalsIgnoreCase("add"))
        {
            processCommandAdd(sender, args, op);
        }
        else if (args[0].equalsIgnoreCase("stop"))
        {
            processCommandStop(sender, args, op);
        }
        else if (args[0].equalsIgnoreCase("pause"))
        {
            processCommandPause(sender, args, op);
        }
    }

    /**
     * Stop part
     */
    public void processCommandStop(final ICommandSender sender, final String[] args, final boolean op)
    {
        if (args.length < 1)
        {
            sendChat(sender, EnumChatFormatting.RED + "Syntax error. Use: /" + getCommandName() + " " + Timer.StopSyntax);
        }
        else if (ServerTimer.getInstance().stopwatchList.containsKey(args[1]))
        {
            final IStopwatchBase timer = ServerTimer.getInstance().stopwatchList.get(args[1]);

            if ((timer.isRelevantFor(sender) && timer.isPersonal()) || op)
            {
                HardStopwatchAPI.stopStopwatch(args[1]);
                sendChat(sender, EnumChatFormatting.GREEN + "Done.");
            }
            else
            {
                sendChat(sender, EnumChatFormatting.RED + "You can't edit this stopwatch.");
            }
        }
        else
        {
            sendChat(sender, EnumChatFormatting.RED + "This stopwatch doesn't exists.");
        }
    }

    /**
     * Add part
     */
    public void processCommandAdd(final ICommandSender sender, final String[] args, final boolean op)
    {
        if (args.length <= 1)
        {
            sendChat(sender, EnumChatFormatting.RED + "Syntax error. Use: /" + getCommandName() + " " + Stopwatch.AddSyntax);
        }
        else if (ServerTimer.getInstance().stopwatchList.containsKey(args[1]))
        {
            sendChat(sender, EnumChatFormatting.RED + "This stopwatch already exists.");
        }
        else if ((args.length >= 3) && Boolean.parseBoolean(args[2]))
        {
            HardStopwatchAPI.newStopwatch(args[1], sender.getCommandSenderName());
        }
        else
        {
            HardStopwatchAPI.newStopwatch(args[1]);
        }
    }

    /**
     * Add part
     */
    public void processCommandPause(final ICommandSender sender, final String[] args, final boolean op)
    {
        if (args.length < 1)
        {
            sendChat(sender, EnumChatFormatting.RED + "Syntax error. Use: /" + getCommandName() + " " + Timer.StopSyntax);
        }
        else if (ServerTimer.getInstance().stopwatchList.containsKey(args[1]))
        {
            final IStopwatchBase timer = ServerTimer.getInstance().stopwatchList.get(args[1]);

            if ((timer.isRelevantFor(sender) && timer.isPersonal()) || op)
            {
                HardStopwatchAPI.pauseStopwatch(args[1], !ServerTimer.getInstance().stopwatchList.get(args[1]).isPaused());
                sendChat(sender, EnumChatFormatting.GREEN + "Done.");
            }
            else
            {
                sendChat(sender, EnumChatFormatting.RED + "You can't edit this stopwatch.");
            }
        }
        else
        {
            sendChat(sender, EnumChatFormatting.RED + "This stopwatch doesn't exists.");
        }
    }

    @Override
    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args)
    {
        if (args.length == 1)
        {
            return CommandBase.getListOfStringsMatchingLastWord(args, "help", "list", "add", "stop", "pause");
        }
        else if ((args.length == 2) && (args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("pause")))
        {
            return CommandBase.getListOfStringsFromIterableMatchingLastWord(args, ServerTimer.getInstance().stopwatchList.keySet());
        }
        else if ((args.length == 3) && args[0].equalsIgnoreCase("add"))
        {
            return CommandBase.getListOfStringsMatchingLastWord(args, "true", "false");
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(final ICommandSender par1ICommandSender)
    {
        return true;
    }

    @Override
    public String getCommandUsage(final ICommandSender icommandsender)
    {
        return "Add, change or remove a stopwatch.";
    }

    @Override
    public int compareTo(Object o)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}