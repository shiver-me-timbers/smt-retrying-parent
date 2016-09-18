package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.ExcludePrecedenceRetryerExcludeClass;
import shiver.me.timbers.retrying.execution.ExcludesWithIncludesRetryerExcludesClass;

public class RetryerExcludesWithIncludesClassFactory extends RetryerExcludesWithIncludesFactory {

    public RetryerExcludesWithIncludesClassFactory() {
        this(new ExcludesWithIncludesRetryerExcludesClass(), new ExcludePrecedenceRetryerExcludeClass());
    }

    public RetryerExcludesWithIncludesClassFactory(
        ExcludesWithIncludesRetryerExcludesClass excludesWithIncludesRetryerExcludes,
        ExcludePrecedenceRetryerExcludeClass excludePrecedenceRetryerExclude
    ) {
        super(excludesWithIncludesRetryerExcludes, excludePrecedenceRetryerExclude);
    }
}
