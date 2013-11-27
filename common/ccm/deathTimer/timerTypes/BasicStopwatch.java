package ccm.deathTimer.timerTypes;

import ccm.deathTimer.utils.FunctionHelper;
import ccm.deathTimer.utils.lib.Archive;
import com.google.common.base.Strings;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Extend to make it more useful (or implement {@link IStopwatchBase}).
 *
 * @author Dries007
 */
public class BasicStopwatch implements IStopwatchBase
{
    public static final int PACKETID = 10;

    public String label;
    public int time;
    public boolean personal;
    public String username;
    public boolean paused;

    public BasicStopwatch()
    {
        this.paused = false;
    }

    @Override
    public void tick()
    {
        if (!this.isPaused()) this.time++;
    }

    @Override
    public void sendAutoUpdate()
    {
        if (this.isPersonal())
        {
            final Player p = (Player) MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(this.username);
            if (p != null) PacketDispatcher.sendPacketToPlayer(this.getPacket(), p);
        }
        else
            PacketDispatcher.sendPacketToAllPlayers(this.getPacket());
    }

    @Override
    public Packet250CustomPayload getPacket()
    {
        final ByteArrayOutputStream streambyte = new ByteArrayOutputStream();
        final DataOutputStream stream = new DataOutputStream(streambyte);

        try
        {
            stream.writeInt(BasicStopwatch.PACKETID);

            stream.writeUTF(this.getLabel());
            stream.writeInt(this.getTime());

            stream.writeBoolean(this.isPaused());

            stream.close();
            streambyte.close();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }

        return PacketDispatcher.getPacket(Archive.MOD_CHANNEL_TIMERS, streambyte.toByteArray());
    }

    @Override
    public boolean isRelevantFor(final ICommandSender player)
    {
        return this.personal ? true : player.getCommandSenderName().equals(this.username);
    }

    @Override
    public boolean isPersonal()
    {
        return this.personal;
    }

    @Override
    public IStopwatchBase getUpdate(final DataInputStream stream) throws IOException
    {
        final BasicStopwatch data = new BasicStopwatch();

        data.label = stream.readUTF();
        data.time = stream.readInt();
        data.paused = stream.readBoolean();

        return data;
    }

    @Override
    public String getLabel()
    {
        return this.label;
    }

    @Override
    public int getTime()
    {
        return this.time;
    }

    @Override
    public void setLabel(final String label)
    {
        this.label = label;
    }

    @Override
    public void setTime(final int time)
    {
        if (time == -1) this.setPaused(true);
        this.time = time;
    }

    @Override
    public ArrayList<String> getTimerString(final ICommandSender player)
    {
        final ArrayList<String> text = new ArrayList<String>();

        text.add(this.getLabel().replaceAll("&", "\u00a7") + ": " + FunctionHelper.parseTime(this.getTime()) + " ");

        return text;
    }

    @Override
    public boolean isPaused()
    {
        return this.paused;
    }

    @Override
    public boolean setPaused(final boolean paused)
    {
        return this.paused = paused;
    }

    @Override
    public NBTTagCompound toNBT()
    {
        final NBTTagCompound tag = new NBTTagCompound(this.label);
        tag.setString("class", this.getClass().getName());

        tag.setInteger("time", this.time);
        tag.setString("label", this.label);
        tag.setBoolean("personal", this.personal);
        if (!Strings.isNullOrEmpty(this.username)) tag.setString("username", this.username);
        tag.setBoolean("paused", this.paused);

        return tag;
    }

    @Override
    public IStopwatchBase fromNBT(final NBTTagCompound tag)
    {
        final BasicStopwatch out = new BasicStopwatch();

        out.time = tag.getInteger("time");
        out.label = tag.getString("label");
        out.personal = tag.getBoolean("personal");
        if (tag.hasKey("username")) out.username = tag.getString("username");
        out.paused = tag.getBoolean("paused");

        return out;
    }
}