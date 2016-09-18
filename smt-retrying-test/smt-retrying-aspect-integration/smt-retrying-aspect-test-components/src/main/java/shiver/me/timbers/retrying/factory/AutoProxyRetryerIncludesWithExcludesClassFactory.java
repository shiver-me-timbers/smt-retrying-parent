package shiver.me.timbers.retrying.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.retrying.execution.IncludesWithExcludesRetryerExcludesClass;

@Component
public class AutoProxyRetryerIncludesWithExcludesClassFactory extends RetryerIncludesWithExcludesClassFactory {

    @Autowired
    public AutoProxyRetryerIncludesWithExcludesClassFactory(
        IncludesWithExcludesRetryerExcludesClass includesWithExcludesRetryerExcludes
    ) {
        super(includesWithExcludesRetryerExcludes);
    }
}
