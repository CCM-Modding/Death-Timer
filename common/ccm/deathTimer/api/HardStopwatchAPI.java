package ccm.deathTimer.api;

import ccm.deathTimer.DeathTimer;
import ccm.deathTimer.server.ServerTimer;
import ccm.deathTimer.timerTypes.BasicStopwatch;
import ccm.deathTimer.timerTypes.IStopwatchBase;

public class HardStopwatchAPI
{
    
    /**
     * If false, the class is not loaded and you can't use the stopwatch functions.
     * 
     * @return
     */
    public static boolean isLoaded()
    {
        return DeathTimer.instance != null;
    }
    
    /**
     * Add a stopwatch.
     * 
     * @param label
     *            UNIQUE KEY
     * @return
     */
    public static void newStopwatch(final String label)
    {
        final BasicStopwatch data = new BasicStopwatch();
        data.label = label;
        
        ServerTimer.getInstance().addStopwatch(data);
    }
    
    /**
     * Call to stop the stopwatch.
     * 
     * @param label
     *            UNIQUE KEY
     * @return
     */
    public static void stopStopwatch(final String label)
    {
        final IStopwatchBase data = ServerTimer.getInstance().stopwatchList.get(label);
        data.setTime(-1);
        ServerTimer.getInstance().addStopwatch(data);
    }

    /**
     * Call to pause/unpause the stopwatch.
     * 
     * @param label
     *            UNIQUE KEY
     * @param pause
     *            Pause?
     * @return
     */
    public static void pauseStopwatch(String label, boolean pause)
    {
        final IStopwatchBase data = ServerTimer.getInstance().stopwatchList.get(label);
        data.setPaused(pause);
        ServerTimer.getInstance().addStopwatch(data);
    }
}