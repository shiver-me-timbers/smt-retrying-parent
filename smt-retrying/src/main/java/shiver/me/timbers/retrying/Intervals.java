package shiver.me.timbers.retrying;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Karl Bennett
 */
class Intervals {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final Iterator<Long> durations;
    private final TimeUnit unit;
    private Long duration;

    Intervals(List<Long> durations, TimeUnit unit) {
        this.durations = durations.iterator();
        this.unit = unit;
    }

    void sleep() throws InterruptedException {
        if (durations.hasNext()) {
            duration = durations.next();
        }
        log.debug("Sleeping for an interval of {} {}.", duration, unit.name());
        Thread.sleep(unit.toMillis(duration));
    }
}
