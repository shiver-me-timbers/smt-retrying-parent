package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.IncludesWithExcludesRetryerExcludesClass;

public class RetryerIncludesWithExcludesClassFactory extends RetryerIncludesWithExcludesFactory {

    public RetryerIncludesWithExcludesClassFactory() {
        this(new IncludesWithExcludesRetryerExcludesClass());
    }

    public RetryerIncludesWithExcludesClassFactory(
        IncludesWithExcludesRetryerExcludesClass includesWithExcludesRetryerExcludes
    ) {
        super(includesWithExcludesRetryerExcludes);
    }
}
