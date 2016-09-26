package shiver.me.timbers.retrying;

import shiver.me.timbers.retrying.factory.RetryerDefaultsMethodFactory;
import shiver.me.timbers.retrying.factory.RetryerIntervalIncrementsMethodFactory;

public abstract class AbstractITAspectRetryerIntervalIncrementsPropertyMethod
    extends AbstractITAspectRetryerIntervalIncrementsProperty {

    @Override
    public RetryerIntervalIncrementsMethodFactory intervalIncrementsFactory() {
        return new RetryerIntervalIncrementsMethodFactory();
    }

    @Override
    public RetryerDefaultsMethodFactory defaultsFactory() {
        return new RetryerDefaultsMethodFactory();
    }
}
