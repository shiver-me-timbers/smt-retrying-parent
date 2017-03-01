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
    private final Long[] durations;

    public ManualRetryerIntervalncrements(int retries, TimeUnit unit, Long... durations) {
        this.retries = retries;
        this.unit = unit;
        this.durations = durations;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T intervalIncrementsMethod(final Callable<T> callable) throws Throwable {
        return retryer((O) options().withRetries(retries).withIntervals(unit, durations)).retry(new Until<T>() {
            @Override
            public T success() throws Throwable {
                return callable.call();
            }
        });
    }
}
