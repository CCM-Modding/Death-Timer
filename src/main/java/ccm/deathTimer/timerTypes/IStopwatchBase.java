package ccm.deathTimer.timerTypes;

import net.minecraft.command.ICommandSender;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public interface IStopwatchBase
{
    public void tick();

    public void sendAutoUpdate();

    public Packet250CustomPayload getPacket();

    public boolean isRelevantFor(ICommandSender player);

    public boolean isPersonal();

    public IStopwatchBase getUpdate(DataInputStream stream) throws IOException;

    public String getLabel();

    public int getTime();

    public void setLabel(String label);

    public void setTime(int time);

    public ArrayList<String> getTimerString(ICommandSender player);

    public boolean isPaused();

    public boolean setPaused(boolean paused);

    public NBTTagCompound toNBT();

    public IStopwatchBase fromNBT(NBTTagCompound tag);
}