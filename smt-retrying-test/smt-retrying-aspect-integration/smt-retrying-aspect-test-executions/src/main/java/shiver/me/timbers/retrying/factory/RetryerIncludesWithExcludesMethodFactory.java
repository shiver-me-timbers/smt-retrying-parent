package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.IncludesWithExcludesRetryerExcludesMethod;

public class RetryerIncludesWithExcludesMethodFactory extends RetryerIncludesWithExcludesFactory {

    public RetryerIncludesWithExcludesMethodFactory() {
        this(new IncludesWithExcludesRetryerExcludesMethod());
    }

    public RetryerIncludesWithExcludesMethodFactory(
        IncludesWithExcludesRetryerExcludesMethod includesWithExcludesRetryerExcludes
    ) {
        super(includesWithExcludesRetryerExcludes);
    }
}
