package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.RetryerAddExcludesMethod;
import shiver.me.timbers.retrying.execution.RetryerExcludesMethod;
import shiver.me.timbers.retrying.execution.RetryerNoExcludesMethod;

public class RetryerExcludesMethodFactory extends RetryerExcludesFactory {

    public RetryerExcludesMethodFactory() {
        this(new RetryerExcludesMethod(), new RetryerNoExcludesMethod(), new RetryerAddExcludesMethod());
    }

    public RetryerExcludesMethodFactory(
        RetryerExcludesMethod retryerExcludes,
        RetryerNoExcludesMethod retryerNoExcludes,
        RetryerAddExcludesMethod retryerAddExcludes
    ) {
        super(retryerExcludes, retryerNoExcludes, retryerAddExcludes);
    }
}
