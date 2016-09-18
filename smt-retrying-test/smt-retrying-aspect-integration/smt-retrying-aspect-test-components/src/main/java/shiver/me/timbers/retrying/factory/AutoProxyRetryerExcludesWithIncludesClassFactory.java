package shiver.me.timbers.retrying.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.retrying.execution.ExcludePrecedenceRetryerExcludeClass;
import shiver.me.timbers.retrying.execution.ExcludesWithIncludesRetryerExcludesClass;

@Component
public class AutoProxyRetryerExcludesWithIncludesClassFactory extends RetryerExcludesWithIncludesClassFactory {

    @Autowired
    public AutoProxyRetryerExcludesWithIncludesClassFactory(
        ExcludesWithIncludesRetryerExcludesClass excludesWithIncludesRetryerExcludes,
        ExcludePrecedenceRetryerExcludeClass excludePrecedenceRetryerExclude
    ) {
        super(excludesWithIncludesRetryerExcludes, excludePrecedenceRetryerExclude);
    }
}
