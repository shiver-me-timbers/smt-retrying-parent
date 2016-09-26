package shiver.me.timbers.retrying;

import shiver.me.timbers.retrying.factory.RetryerDefaultsClassFactory;
import shiver.me.timbers.retrying.factory.RetryerIntervalIncrementsClassFactory;

public abstract class AbstractITAspectRetryerIntervalIncrementsPropertyClass
    extends AbstractITAspectRetryerIntervalIncrementsProperty {

    @Override
    public RetryerIntervalIncrementsClassFactory intervalIncrementsFactory() {
        return new RetryerIntervalIncrementsClassFactory();
    }

    @Override
    public RetryerDefaultsClassFactory defaultsFactory() {
        return new RetryerDefaultsClassFactory();
    }
}
