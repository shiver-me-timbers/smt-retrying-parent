package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.RetryerAddExcludesClass;
import shiver.me.timbers.retrying.execution.RetryerExcludesClass;
import shiver.me.timbers.retrying.execution.RetryerNoExcludesClass;

public class RetryerExcludesClassFactory extends RetryerExcludesFactory {

    public RetryerExcludesClassFactory() {
        this(new RetryerExcludesClass(), new RetryerNoExcludesClass(), new RetryerAddExcludesClass());
    }

    public RetryerExcludesClassFactory(
        RetryerExcludesClass retryerExcludes,
        RetryerNoExcludesClass retryerNoExcludes,
        RetryerAddExcludesClass retryerAddExcludes
    ) {
        super(retryerExcludes, retryerNoExcludes, retryerAddExcludes);
    }
}
