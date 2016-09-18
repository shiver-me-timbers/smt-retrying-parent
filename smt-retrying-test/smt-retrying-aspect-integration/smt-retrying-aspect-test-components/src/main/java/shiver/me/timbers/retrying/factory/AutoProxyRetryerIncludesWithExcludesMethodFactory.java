package shiver.me.timbers.retrying.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.retrying.execution.IncludesWithExcludesRetryerExcludesMethod;

@Component
public class AutoProxyRetryerIncludesWithExcludesMethodFactory extends RetryerIncludesWithExcludesMethodFactory {

    @Autowired
    public AutoProxyRetryerIncludesWithExcludesMethodFactory(
        IncludesWithExcludesRetryerExcludesMethod includesWithExcludesRetryerExcludes
    ) {
        super(includesWithExcludesRetryerExcludes);
    }
}
