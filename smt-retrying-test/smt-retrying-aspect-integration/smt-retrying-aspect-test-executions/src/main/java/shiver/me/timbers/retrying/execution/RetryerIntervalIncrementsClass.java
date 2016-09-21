package shiver.me.timbers.retrying.execution;

import shiver.me.timbers.retrying.Interval;
import shiver.me.timbers.retrying.Retry;

import java.util.concurrent.Callable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static shiver.me.timbers.retrying.util.Constants.DURATION1;
import static shiver.me.timbers.retrying.util.Constants.DURATION2;
import static shiver.me.timbers.retrying.util.Constants.DURATION3;
import static shiver.me.timbers.retrying.util.Constants.INCREMENT_RETRIES;

@Retry(
    value = INCREMENT_RETRIES,
    interval = @Interval(duration = {DURATION1, DURATION2, DURATION3}, unit = MILLISECONDS)
)
public class RetryerIntervalIncrementsClass implements RetryerIntervalIncrements {

    @Override
    public <T> T intervalIncrementsMethod(Callable<T> callable) throws Exception {
        return callable.call();
    }
}
