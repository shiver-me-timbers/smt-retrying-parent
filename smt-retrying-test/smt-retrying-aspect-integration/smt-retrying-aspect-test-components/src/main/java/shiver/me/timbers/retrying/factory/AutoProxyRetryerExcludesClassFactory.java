package shiver.me.timbers.retrying.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.retrying.execution.RetryerAddExcludesClass;
import shiver.me.timbers.retrying.execution.RetryerExcludesClass;
import shiver.me.timbers.retrying.execution.RetryerNoExcludesClass;

@Component
public class AutoProxyRetryerExcludesClassFactory extends RetryerExcludesClassFactory {

    @Autowired
    public AutoProxyRetryerExcludesClassFactory(
        RetryerExcludesClass retryerExcludes,
        RetryerNoExcludesClass retryerNoExcludes,
        RetryerAddExcludesClass retryerAddExcludes
    ) {
        super(retryerExcludes, retryerNoExcludes, retryerAddExcludes);
    }
}
