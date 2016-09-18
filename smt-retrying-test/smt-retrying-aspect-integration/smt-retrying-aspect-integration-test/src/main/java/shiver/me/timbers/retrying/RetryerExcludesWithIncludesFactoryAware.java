package shiver.me.timbers.retrying;

import shiver.me.timbers.retrying.factory.RetryerExcludesWithIncludesFactory;

public interface RetryerExcludesWithIncludesFactoryAware {
    RetryerExcludesWithIncludesFactory excludesWithIncludesFactory();
}
