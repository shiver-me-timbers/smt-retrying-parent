package shiver.me.timbers.retrying.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.retrying.execution.RetryerIntervalIncrementsMethod;

@Component
public class AutoProxyRetryerIntervalIncrementsMethodFactory extends RetryerIntervalIncrementsMethodFactory {

    @Autowired
    public AutoProxyRetryerIntervalIncrementsMethodFactory(RetryerIntervalIncrementsMethod retryerIntervalIncrements) {
        super(retryerIntervalIncrements);
    }
}
