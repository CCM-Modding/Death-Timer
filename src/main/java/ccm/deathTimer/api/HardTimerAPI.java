package ccm.deathTimer.api;

import ccm.deathTimer.DeathTimer;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.timerTypes.BasicTimer;
import ccm.deathTimer.timerTypes.ITimerBase;
import ccm.deathTimer.timerTypes.PointTimer;

public class HardTimerAPI
{
    
    /**
     * If false, the class is not loaded and you can't use the timer functions.
     * 
     * @return
     */
    public static boolean isLoaded()
    {
        return DeathTimer.instance != null;
    }
    
    /**
     * Add a timer.
     * 
     * @param label
     *            UNIQUE KEY
     * @param time
     */
    public static void newBasicTimer(final String label, final int time)
    {
        final BasicTimer data = new BasicTimer();
        data.time = time;
        data.label = label;
        
        ServerTimer.getInstance().addTimer(data);
    }
    
    /**
     * Add a timer with arrow, distance and point.
     * 
     * @param label
     *            UNIQUE KEY
     * @param time
     * @param X
     * @param Y
     * @param Z
     * @param dim
     */
    public static void newPointTimer(final String label, final int time, final int X, final int Y, final int Z, final int dim)
    {
        final PointTimer data = new PointTimer();
        
        data.time = time;
        data.label = label;
        
        data.X = X;
        data.Y = Y;
        data.Z = Z;
        data.dim = dim;
        
        ServerTimer.getInstance().addTimer(data);
    }
    
    /**
     * Set the sound settings for a timer.
     * 
     * @param label
     *            UNIQUE KEY
     * @param sound
     * @param volume
     * @param pitch
     */
    public static void setSound(final String label, final String sound, final float volume, final float pitch)
    {
        final ITimerBase data = ServerTimer.getInstance().timerList.get(label);
        
        data.setSoundName(sound);
        data.setSoundVolume(volume);
        data.setSoundPitch(pitch);
        
        ServerTimer.getInstance().addTimer(data);
    }
    
    /**
     * Call to stop the timer.
     * 
     * @param label
     *            UNIQUE KEY
     */
    public static void stopTimer(final String label)
    {
        final ITimerBase data = ServerTimer.getInstance().timerList.get(label);
        data.setTime(-1);
        ServerTimer.getInstance().addTimer(data);
    }
}