package shiver.me.timbers.retrying.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.retrying.execution.ExcludePrecedenceRetryerExcludeMethod;
import shiver.me.timbers.retrying.execution.ExcludesWithIncludesRetryerExcludesMethod;

@Component
public class AutoProxyRetryerExcludesWithIncludesMethodFactory extends RetryerExcludesWithIncludesMethodFactory {

    @Autowired
    public AutoProxyRetryerExcludesWithIncludesMethodFactory(
        ExcludesWithIncludesRetryerExcludesMethod excludesWithIncludesRetryerExcludes,
        ExcludePrecedenceRetryerExcludeMethod excludePrecedenceRetryerExclude
    ) {
        super(excludesWithIncludesRetryerExcludes, excludePrecedenceRetryerExclude);
    }
}
