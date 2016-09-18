package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.ExcludePrecedenceRetryerExcludeMethod;
import shiver.me.timbers.retrying.execution.ExcludesWithIncludesRetryerExcludesMethod;

public class RetryerExcludesWithIncludesMethodFactory extends RetryerExcludesWithIncludesFactory {

    public RetryerExcludesWithIncludesMethodFactory() {
        this(new ExcludesWithIncludesRetryerExcludesMethod(), new ExcludePrecedenceRetryerExcludeMethod());
    }

    public RetryerExcludesWithIncludesMethodFactory(
        ExcludesWithIncludesRetryerExcludesMethod excludesWithIncludesRetryerExcludes,
        ExcludePrecedenceRetryerExcludeMethod excludePrecedenceRetryerExclude
    ) {
        super(excludesWithIncludesRetryerExcludes, excludePrecedenceRetryerExclude);
    }
}
