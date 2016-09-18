package shiver.me.timbers.retrying.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.retrying.execution.RetryerAddExcludesMethod;
import shiver.me.timbers.retrying.execution.RetryerExcludesMethod;
import shiver.me.timbers.retrying.execution.RetryerNoExcludesMethod;

@Component
public class AutoProxyRetryerExcludesMethodFactory extends RetryerExcludesMethodFactory {

    @Autowired
    public AutoProxyRetryerExcludesMethodFactory(
        RetryerExcludesMethod retryerExcludes,
        RetryerNoExcludesMethod retryerNoExcludes,
        RetryerAddExcludesMethod retryerAddExcludes
    ) {
        super(retryerExcludes, retryerNoExcludes, retryerAddExcludes);
    }
}
