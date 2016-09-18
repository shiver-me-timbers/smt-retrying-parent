package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.RetryerExcludes;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_OTHER_THROWABLES;
import static shiver.me.timbers.retrying.random.RandomThrowables.SOME_THROWABLES;
import static shiver.me.timbers.retrying.util.Classes.toClasses;
import static shiver.me.timbers.retrying.util.Constants.DEFAULT_RETRIES;

public class RetryerExcludesWithIncludesFactory {

    private final LookupFactory<RetryerExcludes> lookupFactory;

    public RetryerExcludesWithIncludesFactory(
        RetryerExcludes excludesWithIncludesRetryerExcludes,
        RetryerExcludes excludePrecedenceRetryerExclude
    ) {
        this(new MapLookupFactory<RetryerExcludes>());
        add(
            excludesWithIncludesRetryerExcludes,
            DEFAULT_RETRIES, asList(SOME_THROWABLES), asList(SOME_OTHER_THROWABLES)
        );
        add(
            excludePrecedenceRetryerExclude,
            DEFAULT_RETRIES, singletonList(SOME_THROWABLES[0]), singletonList(SOME_THROWABLES[0])
        );
    }

    public RetryerExcludesWithIncludesFactory(LookupFactory<RetryerExcludes> lookupFactory) {
        this.lookupFactory = lookupFactory;
    }

    public RetryerExcludes create(int retries, List<Throwable> excludes, List<Throwable> includes) {
        return lookupFactory.find(retries, toClasses(excludes), toClasses(includes));
    }

    public void add(
        RetryerExcludes retryerExcludes,
        int retries,
        List<Throwable> excludes,
        List<Throwable> includes
    ) {
        lookupFactory.add(retryerExcludes, retries, toClasses(excludes), toClasses(includes));
    }
}
