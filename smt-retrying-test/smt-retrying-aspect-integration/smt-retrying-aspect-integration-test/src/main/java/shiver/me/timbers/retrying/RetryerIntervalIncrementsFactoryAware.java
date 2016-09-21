package shiver.me.timbers.retrying;

import shiver.me.timbers.retrying.factory.RetryerIntervalIncrementsFactory;

public interface RetryerIntervalIncrementsFactoryAware {
    RetryerIntervalIncrementsFactory intervalIncrementsFactory();
}
