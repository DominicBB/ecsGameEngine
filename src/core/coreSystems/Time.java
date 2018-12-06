package core.coreSystems;

public class Time {

    static float deltaTime = 0f;
    static float unscaledDeltaTime = 0f;

    static long nanoTime = 0l;
    static long timeMili = 0l;
    static float time = 0f;

    static long startTime;
    static float totalElapsedTime = 0f;

    static long lastTime;
    static long deltaNano;
    static double numUpdatesTodo;

    static void start(){
        startTime = System.currentTimeMillis();
        lastTime = System.nanoTime();
    }

    static void updateTimes(double ns) {
        nanoTime = System.nanoTime();
        timeMili = System.currentTimeMillis();
        time = timeMili/1000f;

        deltaNano = nanoTime - lastTime;
        unscaledDeltaTime = (float) (deltaNano / 1000000000.0);
        deltaTime = unscaledDeltaTime * timeScale;

        numUpdatesTodo += deltaNano / ns;
        lastTime = nanoTime;
    }

    public static float timeScale = 1f;

    /**
     * Time since last frame in seconds
     *
     * @return
     */
    public static float getDeltaTime() {
        return deltaTime;
    }

    /**
     * Time since last frame in seconds
     *
     * @return
     */
    public static float getUnscaledDeltaTime() {
        return unscaledDeltaTime;
    }

    /**
     * Current time of frame in nano seconds
     *
     * @return
     */
    public static long getNanoTime() {
        return nanoTime;
    }

    /**
     * Current time of frame in mili seconds
     *
     * @return
     */
    public static long getTimeMili() {
        return timeMili;
    }

    /**
     * Current time of frame in seconds
     *
     * @return
     */
    public static float getTime() {
        return time;
    }

    /**
     * Total elapsed time since start
     *
     * @return
     */
    public static float getTotalElapsedTime() {
        return totalElapsedTime;
    }

    /**
     * The start time in seconds
     *
     * @return
     */
    public static long getStartTime() {
        return startTime;
    }
}
