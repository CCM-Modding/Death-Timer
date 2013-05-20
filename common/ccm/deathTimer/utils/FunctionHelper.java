package ccm.deathTimer.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class FunctionHelper
{
    public static String timeColor(int time)
    {
        if (time <= 30) return EnumChatFormatting.RED.toString();
        else if (time <= 60) return EnumChatFormatting.GOLD.toString();
        else if (time <= 150) return EnumChatFormatting.YELLOW.toString();
        else return EnumChatFormatting.GREEN.toString();
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

        if (days != 0)output += (days < 10 ? "0" : "") + days + " days ";

        if (hours != 0)output += (hours < 10 ? "0" : "") + hours + " h ";
            
        if (minutes != 0) output += (minutes < 10 ? "0" : "") + minutes + " min ";

        output += (seconds < 10 ? "0" : "") + seconds + " sec";

        return output;
    }
    
    public static void playDragonSound()
    {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.theWorld != null)
        {
            double d4 = mc.renderViewEntity.posX;
            double d5 = mc.renderViewEntity.posY;
            double d6 = mc.renderViewEntity.posZ;
            mc.theWorld.playSound(d4, d5, d6, "mob.enderdragon.end", 5.0F, 1.0F, false);
        }
    }
}
