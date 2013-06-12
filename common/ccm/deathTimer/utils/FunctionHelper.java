package ccm.deathTimer.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

import cpw.mods.fml.common.FMLCommonHandler;

import ccm.nucleum.utils.lib.Arrows;

/**
 * Static helper method class.
 * 
 * @author Dries007
 */
public class FunctionHelper
{

    public static String timeColor(final int time)
    {
        if (time <= 30){
            return EnumChatFormatting.RED.toString();
        }else if (time <= 60){
            return EnumChatFormatting.GOLD.toString();
        }else if (time <= 150){
            return EnumChatFormatting.YELLOW.toString();
        }else{
            return EnumChatFormatting.GREEN.toString();
        }
    }

    public static String getArrowTo(final int X, final int Z, final EntityPlayer player)
    {
        float pAngle = 360 + player.rotationYaw;
        pAngle %= 360;

        float dAngle = 360 - (float) Math.toDegrees(Math.atan2(X - player.posX, Z - player.posZ));
        dAngle %= 360;
        float angle = (360 + dAngle) - pAngle;
        angle %= 360;

        return "" + Arrows.getArrowFromAngle(angle);
    }

    /**
     * Gets a nice string with only needed elements. Max time is weeks
     * 
     * @param timeInSec
     * @return Time in string format
     */
    public static String parseTime(final int timeInSec)
    {
        String output = "";
        final int weeks = timeInSec / (86400 * 7);
        int remainder = timeInSec % (86400 * 7);
        final int days = remainder / 86400;
        remainder = timeInSec % 86400;
        final int hours = remainder / 3600;
        remainder = timeInSec % 3600;
        final int minutes = remainder / 60;
        final int seconds = remainder % 60;

        if (weeks != 0){
            output += weeks + " weeks ";
        }

        if (days != 0){
            output += (days < 10 ? days > 0 ? "0" : "" : "") + days + " days ";
        }

        if (hours != 0){
            output += (hours < 10 ? hours > 0 ? "0" : "" : "") + hours + " h ";
        }

        if (minutes != 0){
            output += (minutes < 10 ? minutes > 0 ? "0" : "" : "") + minutes + " min ";
        }

        output += (seconds < 10 ? seconds > 0 ? "0" : "" : "") + seconds + " sec";

        return output;
    }

    /**
     * Play a sound. Client only. You need to do the server <=> client yourself.
     * 
     * @param name
     * @param volume
     * @param pitch
     */
    public static void playSound(final String name, final float volume, final float pitch)
    {
        if (FMLCommonHandler.instance().getSide().isServer()){
            return;
        }
        final Minecraft mc = Minecraft.getMinecraft();
        final float d4 = (float) mc.renderViewEntity.posX;
        final float d5 = (float) mc.renderViewEntity.posY;
        final float d6 = (float) mc.renderViewEntity.posZ;
        mc.sndManager.playSound(name, d4, d5, d6, volume, pitch);
    }
}
