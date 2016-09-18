package shiver.me.timbers.retrying.execution;

import shiver.me.timbers.retrying.Retry;

import java.util.concurrent.Callable;

import static shiver.me.timbers.retrying.util.Constants.DEFAULT_RETRIES;

@Retry(
    value = DEFAULT_RETRIES,
    includes = {IllegalMonitorStateException.class, IllegalArgumentException.class, Error.class},
    excludes = {IllegalStateException.class, ClassCastException.class, IllegalAccessError.class}
)
public class IncludesWithExcludesRetryerExcludesClass implements RetryerIncludes {

    @Override
    public <T> T includeMethod(Callable<T> callable) throws Exception {
        return callable.call();
    }
}
