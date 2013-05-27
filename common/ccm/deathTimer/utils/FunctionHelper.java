package ccm.deathTimer.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import ccm.nucleum_omnium.utils.lib.Arrows;
import cpw.mods.fml.common.FMLCommonHandler;

public class FunctionHelper
{
    public static String timeColor(int time)
    {
        if (time <= 30) return EnumChatFormatting.RED.toString();
        else if (time <= 60) return EnumChatFormatting.GOLD.toString();
        else if (time <= 150) return EnumChatFormatting.YELLOW.toString();
        else return EnumChatFormatting.GREEN.toString();
    }
    
    public static String getArrowTo(int X, int Z, EntityPlayer player)
    {
        float pAngle = 360 + player.rotationYaw;
        pAngle %= 360;
        
        float dAngle = 360 - (float) Math.toDegrees(Math.atan2(X - player.posX, Z - player.posZ));
        dAngle %= 360;
        float angle = 360 + dAngle - pAngle;
        angle %= 360;
        
        return "" + Arrows.getArrowFromAngle(angle);
    }
    
    /**
     * Gets a nice string with only needed elements.
     * Max time is weeks
     * @param timeInSec
     * @return Time in string format
     */
    public static String parseTime(int timeInSec)
    {
        String output = "";
        int weeks = timeInSec / (86400 * 7);
        int remainder = timeInSec % (86400 * 7);
        int days = remainder / 86400;
        remainder = timeInSec % 86400;
        int hours = remainder / 3600;
        remainder = timeInSec % 3600;
        int minutes = remainder / 60;
        int seconds = remainder % 60;

        if (weeks != 0)output += weeks + " weeks ";

        if (days != 0)output += (days < 10 ? (days > 0 ? "0" : "") : "") + days + " days ";

        if (hours != 0)output += (hours < 10 ? (hours > 0 ? "0" : "") : "") + hours + " h ";
            
        if (minutes != 0) output += (minutes < 10 ? (minutes > 0 ? "0" : "") : "") + minutes + " min ";

        output += (seconds < 10 ? (seconds > 0 ? "0" : "") : "") + seconds + " sec";

        return output;
    }
    
    /**
     * Play a sound. Client only. You need to do the server <=> client yourself.
     * @param name
     * @param volume
     * @param pitch
     */
    public static void playSound(String name, float volume, float pitch)
    {
        if (FMLCommonHandler.instance().getSide().isServer()) return;
        Minecraft mc = Minecraft.getMinecraft();
        float d4 = (float) mc.renderViewEntity.posX;
        float d5 = (float) mc.renderViewEntity.posY;
        float d6 = (float) mc.renderViewEntity.posZ;
        mc.sndManager.playSound(name, d4, d5, d6, volume, pitch);
    }
}
