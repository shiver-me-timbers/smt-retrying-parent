package shiver.me.timbers.retrying;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertThat;

public class IntervalsTest {

    private static final long INTERVAL1 = 100L;
    private static final long INTERVAL2 = 200L;
    private static final long INTERVAL3 = 300L;
    private StopWatch stopWatch;

    @Before
    public void setUp() {
        stopWatch = new StopWatch();
    }

    @Test
    public void Can_sleep_for_different_intervals() throws InterruptedException {

        final Intervals intervals = new Intervals(asList(INTERVAL1, INTERVAL2, INTERVAL3), MILLISECONDS);

        // Given
        startStopWatch();

        // When
        intervals.sleep();
        final long interval1 = readTime();
        intervals.sleep();
        final long interval2 = readTime();
        intervals.sleep();
        final long interval3 = readTime();
        stopStopWatch();

        // Then
        assertThat(interval1, greaterThanOrEqualTo(INTERVAL1));
        assertThat(interval2, greaterThanOrEqualTo(INTERVAL2));
        assertThat(interval3, greaterThanOrEqualTo(INTERVAL3));
    }

    @Test
    public void Will_sleep_for_last_interval_when_called_more_than_the_intervals_supplied()
        throws InterruptedException {

        final Intervals intervals = new Intervals(asList(INTERVAL1, INTERVAL3), MILLISECONDS);

        // Given
        startStopWatch();

        // When
        intervals.sleep();
        final long interval1 = readTime();
        intervals.sleep();
        final long interval2 = readTime();
        intervals.sleep();
        final long interval3 = readTime();
        stopStopWatch();

        // Then
        assertThat(interval1, greaterThanOrEqualTo(INTERVAL1));
        assertThat(interval2, greaterThanOrEqualTo(INTERVAL3));
        assertThat(interval3, greaterThanOrEqualTo(INTERVAL3));
    }

    private void startStopWatch() {
        stopWatch.start();
    }

    private long readTime() {
        stopWatch.stop();
        final long time = stopWatch.getLastTaskTimeMillis();
        stopWatch.start();
        return time;
    }

    private void stopStopWatch() {
        stopWatch.stop();
    }
}