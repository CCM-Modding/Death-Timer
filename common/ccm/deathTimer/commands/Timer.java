package ccm.deathTimer.commands;

import static ccm.deathTimer.utils.FunctionHelper.sendChat;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import ccm.deathTimer.api.HardTimerAPI;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.timerTypes.DeathTimer;
import ccm.deathTimer.timerTypes.ITimerBase;
import ccm.deathTimer.timerTypes.PointTimer;
import ccm.deathTimer.utils.FunctionHelper;

/**
 * You need OP to change server wide settings, Client only changes are send back via packets and are saved in the client config.
 *
 * @author Dries007
 */
public class Timer extends CommandBase
{
    public final static String ListSyntax = "list";
    public final static String AddSyntax = "add <name> <time> [X] [Y] [Z] [dim]";
    public final static String StopSyntax = "stop <name>";
    public final static String SoundSyntax = "sound <name> [volume] [pitch]";
    public final static String PointSyntax = "point <name> [X] [Y] [Z] [dim]";
    public final static String OpArgs = "[" + Timer.ListSyntax + "|" + Timer.AddSyntax + "|" + Timer.StopSyntax + "|" + Timer.SoundSyntax + "|" + Timer.PointSyntax + "]";
    public final static String NonOpArgs = "[" + Timer.ListSyntax + "|" + Timer.StopSyntax + "|" + Timer.SoundSyntax + "]";

    @Override
    public String getCommandName()
    {
        return "timer";
    }

    /**
     * Main command thing
     */
    @Override
    public void processCommand(final ICommandSender sender, final String[] args)
    {
        final boolean op = sender instanceof EntityPlayer ? MinecraftServer.getServer().isDedicatedServer() ? MinecraftServer.getServer().getConfigurationManager().getOps().contains(sender.getCommandSenderName()) : true : true;

        if ((args.length == 1) && args[0].equalsIgnoreCase("help"))
        {
            sendChat(sender, EnumChatFormatting.GREEN + "Useage: /" + getCommandName() + " " + (op ? Timer.OpArgs : Timer.NonOpArgs));
            if (!op)
            {
                sendChat(sender, EnumChatFormatting.GREEN + "Note that you can only change your own timers!");
            }
            return;
        }

        if ((args.length == 0) || args[0].equalsIgnoreCase("timers") || args[0].equalsIgnoreCase("list"))
        {
            sendChat(sender, "--- Timers ---");
            for (final ITimerBase timer : ServerTimer.getInstance().timerList.values())
            {
                final StringBuilder sb = new StringBuilder();
                for (final String s : timer.getTimerString(sender))
                {
                    sb.append(EnumChatFormatting.RESET.toString() + EnumChatFormatting.WHITE.toString() + s);
                }
                sendChat(sender, sb.toString());
            }
            sendChat(sender, "--------------");
        }
        else if (op && args[0].equalsIgnoreCase("add"))
        {
            processCommandAdd(sender, args, op);
        }
        else if (op && args[0].equalsIgnoreCase("point"))
        {
            processCommandPoint(sender, args, op);
        }
        else if (args[0].equalsIgnoreCase("stop"))
        {
            processCommandStop(sender, args, op);
        }
        else if (args[0].equalsIgnoreCase("sound"))
        {
            processCommandSound(sender, args, op);
        }
        else
        {
            sendChat(sender, EnumChatFormatting.RED + "Syntax error. Use: /" + getCommandName() + " " + (op ? Timer.OpArgs : Timer.NonOpArgs));
            if (!op)
            {
                sendChat(sender, EnumChatFormatting.RED + "Note that you can only change your own timers!");
            }
            return;
        }
    }

    /**
     * Stop part
     */
    public void processCommandStop(final ICommandSender sender, final String[] args, final boolean op)
    {
        if (args.length < 1)
        {
            if (ServerTimer.getInstance().timerList.containsKey(DeathTimer.PREFIX + sender.getCommandSenderName()))
            {
                HardTimerAPI.stopTimer(DeathTimer.PREFIX + sender.getCommandSenderName());
            }
            else
            {
                sendChat(sender, EnumChatFormatting.RED + "Syntax error. Use: /" + getCommandName() + " " + Timer.StopSyntax);
            }
        }
        else if (ServerTimer.getInstance().timerList.containsKey(args[1]))
        {
            final ITimerBase timer = ServerTimer.getInstance().timerList.get(args[1]);

            if ((timer.isRelevantFor(sender) && timer.isPersonal()) || op)
            {
                HardTimerAPI.stopTimer(args[1]);
                sendChat(sender, EnumChatFormatting.GREEN + "Done.");
            }
            else
            {
                sendChat(sender, EnumChatFormatting.RED + "You can't edit this timer.");
            }
        }
        else
        {
            sendChat(sender, EnumChatFormatting.RED + "This timer doesn't exists.");
        }
    }

    /**
     * Sound part
     */
    public void processCommandSound(final ICommandSender sender, final String[] args, final boolean op)
    {
        if (args.length < 1)
        {
            sendChat(sender, EnumChatFormatting.RED + "Syntax error. Use: /" + getCommandName() + " " + Timer.SoundSyntax);
        }
        else if (ServerTimer.getInstance().timerList.containsKey(args[1]))
        {
            final ITimerBase timer = ServerTimer.getInstance().timerList.get(args[1]);

            if (args.length < 5)
            {
                if ((timer.isRelevantFor(sender) && timer.isPersonal()) || op)
                {
                    if (FunctionHelper.isNumeric(args[3]) || FunctionHelper.isNumeric(args[3]))
                    {
                        HardTimerAPI.setSound(args[1], args[2], Float.valueOf(args[3]), Float.valueOf(args[4]));
                        sendChat(sender, EnumChatFormatting.GREEN + "Done.");
                    }
                    else if (!FunctionHelper.isNumeric(args[3]))
                    {
                        sendChat(sender, EnumChatFormatting.RED + " " + args[3] + " is not a number!");
                    }
                    else if (!FunctionHelper.isNumeric(args[4]))
                    {
                        sendChat(sender, EnumChatFormatting.RED + " " + args[4] + " is not a number!");
                    }
                    else
                    {
                        sendChat(sender, "?????????? < Please report to modmaker, idk how you got here...");
                    }
                }
                else
                {
                    sendChat(sender, EnumChatFormatting.RED + "You can't edit this timer.");
                }
            }
            else
            {
                sendChat(sender, "Current sound: " + timer.getSoundName() + " Volume:" + timer.getSoundVolume() + " Pitch: " + timer.getSoundPitch());
            }
        }
        else
        {
            sendChat(sender, EnumChatFormatting.RED + "This timer doesn't exists.");
        }
    }

    /**
     * Point part
     */
    public void processCommandPoint(final ICommandSender sender, final String[] args, final boolean op)
    {
        if (args.length < 1)
        {
            sendChat(sender, EnumChatFormatting.RED + "Syntax error. Use: /" + getCommandName() + " " + Timer.PointSyntax);
        }
        else if (ServerTimer.getInstance().timerList.containsKey(args[1]) && (ServerTimer.getInstance().timerList.get(args[1]) instanceof PointTimer))
        {
            final PointTimer timer = (PointTimer) ServerTimer.getInstance().timerList.get(args[1]);

            if (args.length < 5)
            {
                if ((timer.isRelevantFor(sender) && timer.isPersonal()) || op)
                {
                    if (FunctionHelper.isNumeric(args[2]) || FunctionHelper.isNumeric(args[3]) || FunctionHelper.isNumeric(args[4]))
                    {
                        timer.X = Integer.parseInt(args[2]);
                        timer.Y = Integer.parseInt(args[3]);
                        timer.Z = Integer.parseInt(args[4]);
                        if ((args.length == 6) && FunctionHelper.isNumeric(args[5]))
                        {
                            timer.dim = Integer.parseInt(args[5]);
                        }
                        sendChat(sender, EnumChatFormatting.GREEN + "Done.");
                        timer.sendAutoUpdate();
                    }
                    else if (!FunctionHelper.isNumeric(args[3]))
                    {
                        sendChat(sender, EnumChatFormatting.RED + " " + args[3] + " is not a number!");
                    }
                    else if (!FunctionHelper.isNumeric(args[4]))
                    {
                        sendChat(sender, EnumChatFormatting.RED + " " + args[4] + " is not a number!");
                    }
                    else if (!FunctionHelper.isNumeric(args[5]))
                    {
                        sendChat(sender, EnumChatFormatting.RED + " " + args[5] + " is not a number!");
                    }
                    else if ((args.length < 5) && !FunctionHelper.isNumeric(args[6]))
                    {
                        sendChat(sender, EnumChatFormatting.RED + " " + args[6] + " is not a number!");
                    }
                    else
                    {
                        sendChat(sender, "?????????? < Please report to modmaker, idk how you got here...");
                    }
                }
                else
                {
                    sendChat(sender, EnumChatFormatting.RED + "You can't edit this timer.");
                }
            }
            else
            {
                sendChat(sender, "Current point: [" + timer.X + ";" + timer.Y + ";" + timer.Z + "] in world " + timer.dim);
            }
        }
        else
        {
            sendChat(sender, EnumChatFormatting.RED + "This timer doesn't exists or is not a point timer.");
        }
    }

    /**
     * Add part
     */
    public void processCommandAdd(final ICommandSender sender, final String[] args, final boolean op)
    {
        if (args.length < 3)
        {
            sendChat(sender, EnumChatFormatting.RED + "Syntax error. Use: /" + getCommandName() + " " + Timer.AddSyntax);
        }
        else if (ServerTimer.getInstance().timerList.containsKey(args[1]))
        {
            sendChat(sender, EnumChatFormatting.RED + "This timer already exists.");
        }
        else if (!FunctionHelper.isNumeric(args[2]))
        {
            sendChat(sender, EnumChatFormatting.RED + " " + args[2] + " is not a number!");
        }
        else if ((args.length == 6) || (args.length == 7))
        {
            if (FunctionHelper.isNumeric(args[3]) || FunctionHelper.isNumeric(args[4]) || FunctionHelper.isNumeric(args[5]))
            {
                if (!(sender instanceof EntityPlayer) && (args.length == 6))
                {
                    sendChat(sender, "You need to specify a dimention!");
                }
                else if ((args.length == 7) && !FunctionHelper.isNumeric(args[6]))
                {
                    sendChat(sender, EnumChatFormatting.RED + " " + args[2] + " is not a number!");
                }
                else
                {
                    HardTimerAPI.newPointTimer(args[1], Integer.valueOf(args[2]), Integer.valueOf(args[3]), Integer.valueOf(args[4]), Integer.valueOf(args[5]),
                            args.length == 7 ? Integer.valueOf(args[6]) : ((EntityPlayer) sender).dimension);
                }
            }
            else if (!FunctionHelper.isNumeric(args[3]))
            {
                sendChat(sender, EnumChatFormatting.RED + " " + args[3] + " is not a number!");
            }
            else if (!FunctionHelper.isNumeric(args[4]))
            {
                sendChat(sender, EnumChatFormatting.RED + " " + args[4] + " is not a number!");
            }
            else if (!FunctionHelper.isNumeric(args[5]))
            {
                sendChat(sender, EnumChatFormatting.RED + " " + args[5] + " is not a number!");
            }
            else
            {
                sendChat(sender, "?????????? < Please report to modmaker, idk how you got here...");
            }
        }
        else
        {
            HardTimerAPI.newBasicTimer(args[1], Integer.valueOf(args[2]));
        }
    }

    @Override
    public List<String> addTabCompletionOptions(final ICommandSender sender, final String[] args)
    {
        if (args.length == 1)
        {
            return CommandBase.getListOfStringsMatchingLastWord(args, "help", "list", "add", "stop", "sound", "point");
        }
        else if ((args.length == 2) && (args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("sound") || args[0].equalsIgnoreCase("stop")))
        {
            return CommandBase.getListOfStringsFromIterableMatchingLastWord(args, ServerTimer.getInstance().timerList.keySet());
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
        return "Add, change or remove a timer.";
    }

    @Override
    public int compareTo(Object o)
    {
        // TODO Auto-generated method stub
        return 0;
    }
}