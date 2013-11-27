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
     * @param label UNIQUE KEY
     */
    public static void newStopwatch(final String label)
    {
        final BasicStopwatch data = new BasicStopwatch();
        data.label = label;

        ServerTimer.getInstance().addStopwatch(data);
    }

    /**
     * Add a personal stopwatch.
     *
     * @param label    UNIQUE KEY
     * @param username
     */
    public static void newStopwatch(final String label, final String username)
    {
        final BasicStopwatch data = new BasicStopwatch();
        data.label = label;
        data.personal = true;
        data.username = username;

        ServerTimer.getInstance().addStopwatch(data);
    }

    /**
     * Call to stop the stopwatch.
     *
     * @param label UNIQUE KEY
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
     * @param label UNIQUE KEY
     * @param pause Pause?
     */
    public static void pauseStopwatch(final String label, final boolean pause)
    {
        final IStopwatchBase data = ServerTimer.getInstance().stopwatchList.get(label);
        data.setPaused(pause);
        ServerTimer.getInstance().addStopwatch(data);
    }
}