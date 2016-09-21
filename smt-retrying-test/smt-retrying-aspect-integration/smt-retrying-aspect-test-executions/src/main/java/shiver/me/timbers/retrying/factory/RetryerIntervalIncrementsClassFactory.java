package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.RetryerIntervalIncrementsClass;

public class RetryerIntervalIncrementsClassFactory extends RetryerIntervalIncrementsFactory {

    public RetryerIntervalIncrementsClassFactory() {
        this(new RetryerIntervalIncrementsClass());
    }

    public RetryerIntervalIncrementsClassFactory(RetryerIntervalIncrementsClass retryerIntervalIncrements) {
        super(retryerIntervalIncrements);
    }
}
