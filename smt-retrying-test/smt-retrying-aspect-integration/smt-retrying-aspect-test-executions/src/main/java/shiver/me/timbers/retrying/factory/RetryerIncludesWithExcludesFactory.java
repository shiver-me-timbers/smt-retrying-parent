package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.RetryerIncludes;

import java.util.List;

import static java.util.Arrays.asList;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_OTHER_THROWABLES;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_THROWABLES;
import static shiver.me.timbers.retrying.util.Classes.toClasses;
import static shiver.me.timbers.retrying.util.Constants.DEFAULT_RETRIES;

public class RetryerIncludesWithExcludesFactory {

    private final LookupFactory<RetryerIncludes> lookupFactory;

    public RetryerIncludesWithExcludesFactory(
        RetryerIncludes includesWithExcludesRetryerExcludes
    ) {
        this(new MapLookupFactory<RetryerIncludes>());
        add(
            includesWithExcludesRetryerExcludes,
            DEFAULT_RETRIES, asList(SOME_THROWABLES), asList(SOME_OTHER_THROWABLES)
        );
    }

    public RetryerIncludesWithExcludesFactory(LookupFactory<RetryerIncludes> lookupFactory) {
        this.lookupFactory = lookupFactory;
    }

    public RetryerIncludes create(int retries, List<Throwable> excludes, List<Throwable> includes) {
        return lookupFactory.find(retries, toClasses(excludes), toClasses(includes));
    }

    public void add(
        RetryerIncludes retryerIncludes,
        int retries,
        List<Throwable> excludes,
        List<Throwable> includes
    ) {
        lookupFactory.add(retryerIncludes, retries, toClasses(excludes), toClasses(includes));
    }
}
