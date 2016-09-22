package shiver.me.timbers.retrying;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Karl Bennett
 */
class Intervals {

    private final Iterator<Long> durations;
    private final TimeUnit unit;
    private Long duration;

    Intervals(List<Long> durations, TimeUnit unit) {
        this.durations = durations.iterator();
        this.unit = unit;
    }

    void sleep() throws InterruptedException {
        if (durations.hasNext()) {
            Thread.sleep(unit.toMillis(duration = durations.next()));
            return;
        }
        Thread.sleep(unit.toMillis(duration));
    }
}
