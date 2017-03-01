package shiver.me.timbers.retrying.execution;

import shiver.me.timbers.retrying.ITRetryerDefaults;
import shiver.me.timbers.retrying.junit.RetryerPropertyRule;

import java.util.List;
import java.util.concurrent.Callable;

public class PropertyIncludesWithExcludesRetryerExcludes implements RetryerIncludes, RetryerExcludes {

    private final RetryerPropertyRule propertyRule;
    private final int retries;
    private final List<Throwable> includes;
    private final List<Throwable> excludes;
    private final ITRetryerDefaults retryerDefaults;

    public PropertyIncludesWithExcludesRetryerExcludes(
        RetryerPropertyRule propertyRule,
        int retries,
        List<Throwable> includes,
        List<Throwable> excludes,
        ITRetryerDefaults retryerDefaults
    ) {
        this.propertyRule = propertyRule;
        this.retries = retries;
        this.includes = includes;
        this.excludes = excludes;
        this.retryerDefaults = retryerDefaults;
    }

    @Override
    public <T> T excludeMethod(Callable<T> callable) throws Throwable {
        return method(callable);
    }

    @Override
    public <T> T includeMethod(Callable<T> callable) throws Throwable {
        return method(callable);
    }

    private <T> T method(Callable<T> callable) throws Throwable {
        propertyRule.setRetries(retries);
        propertyRule.setIncludes(includes);
        propertyRule.setExcludes(excludes);
        return retryerDefaults.defaults().defaultsMethod(callable);
    }
}
