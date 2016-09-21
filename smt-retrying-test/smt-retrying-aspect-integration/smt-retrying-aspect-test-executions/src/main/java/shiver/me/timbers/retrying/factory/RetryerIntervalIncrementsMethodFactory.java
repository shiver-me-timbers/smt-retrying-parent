package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.RetryerIntervalIncrementsMethod;

public class RetryerIntervalIncrementsMethodFactory extends RetryerIntervalIncrementsFactory {

    public RetryerIntervalIncrementsMethodFactory() {
        this(new RetryerIntervalIncrementsMethod());
    }

    public RetryerIntervalIncrementsMethodFactory(RetryerIntervalIncrementsMethod retryerIntervalIncrements) {
        super(retryerIntervalIncrements);
    }
}
