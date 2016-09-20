package shiver.me.timbers.retrying.execution;

import shiver.me.timbers.retrying.Options;
import shiver.me.timbers.retrying.Retryer;
import shiver.me.timbers.retrying.Until;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class ManualRetryerIntervalncrements<R extends Retryer, O extends Options> extends RetryerCreater<R, O>
    implements RetryerIntervalIncrements {

    private final int retries;
    private final TimeUnit unit;
    private final long[] increments;

    public ManualRetryerIntervalncrements(int retries, TimeUnit unit, long... increments) {
        this.retries = retries;
        this.unit = unit;
        this.increments = increments;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T intervalIncrementsMethod(final Callable<T> callable) throws InterruptedException {
        return retryer((O) options().withRetries(retries).withIntervals(unit, increments)).retry(new Until<T>() {
            @Override
            public T success() throws Throwable {
                return callable.call();
            }
        });
    }
}
