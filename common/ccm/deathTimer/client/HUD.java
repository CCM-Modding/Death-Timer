package ccm.deathTimer.client;

import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.event.ForgeSubscribe;
import ccm.deathTimer.utils.FunctionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * The timer HUD renderer class
 * @author Dries007
 *
 */
@SideOnly(Side.CLIENT)
public class HUD
{
    @ForgeSubscribe
    public void render(Text event)
    {
        boolean b = (event.left.size() != 0);
        if (ClientDeathTimer.enable)
        {
            if (b) event.left.add("----------------");
            event.left.add("T- item despawn:");
            event.left.add(FunctionHelper.timeColor(ClientDeathTimer.time) + FunctionHelper.parseTime(ClientDeathTimer.time));
            if (b) event.left.add("----------------");
        }
    }
}
