package shiver.me.timbers.retrying.execution;

import shiver.me.timbers.retrying.Retry;

import java.util.concurrent.Callable;

import static shiver.me.timbers.retrying.util.Constants.DEFAULT_RETRIES;

@Retry(
    value = DEFAULT_RETRIES,
    excludes = {IllegalMonitorStateException.class, IllegalArgumentException.class, Error.class},
    includes = {IllegalStateException.class, ClassCastException.class, IllegalAccessError.class}
)
public class ExcludesWithIncludesRetryerExcludesClass implements RetryerExcludes {

    @Override
    public <T> T excludeMethod(Callable<T> callable) throws Exception {
        return callable.call();
    }
}
