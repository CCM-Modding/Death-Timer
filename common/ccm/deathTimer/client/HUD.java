package ccm.deathTimer.client;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.event.ForgeSubscribe;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import ccm.deathTimer.Config;
import ccm.deathTimer.timerTypes.ITimerBase;

/**
 * The timer HUD renderer class
 * 
 * @author Dries007
 */
@SideOnly(Side.CLIENT)
public class HUD
{

    public static final String HEADER = "--- Timers ---";

    public static final String FOOTER = "-------------";

    @ForgeSubscribe
    public void render(final Text event)
    {
        final EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if ((player == null) || (player.worldObj == null)){
            return;
        }

        final boolean b = Config.useRightSide ? (event.right.size() != 0) : (event.left.size() != 0);
        final ArrayList<String> text = Config.useRightSide ? event.right : event.left;

        /* Header */
        if (b && (ClientTimer.getInstance().serverTimerList.size() != 0)){
            text.add(HEADER);
        }

        /* Add all the timers */
        for (final ITimerBase data : ClientTimer.getInstance().serverTimerList.values()){
            text.addAll(data.getTimerString(player));
        }

        /* Footer */
        if (b && (ClientTimer.getInstance().serverTimerList.size() != 0)){
            text.add(FOOTER);
        }
    }
}
