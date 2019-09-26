package LokEngine.Tools;

public class Timer {

    private long startTime;
    private long duration;

    public Timer(){
        resetTimer();
    }

    public boolean checkTime(){
        return System.nanoTime() - startTime >= duration;
    }

    public void resetTimer(){
        startTime = System.nanoTime();
    }

    public Timer setDurationInSeconds(float seconds){
        return setDurationInMilliseconds((long)(seconds * 1000L));
    }

    public Timer setDurationInMilliseconds(long milliseconds){
        return setDurationInNanoseconds(milliseconds * 1000000L);
    }

    public Timer setDurationInNanoseconds(long nanoseconds){
        duration = nanoseconds;
        return this;
    }
}
