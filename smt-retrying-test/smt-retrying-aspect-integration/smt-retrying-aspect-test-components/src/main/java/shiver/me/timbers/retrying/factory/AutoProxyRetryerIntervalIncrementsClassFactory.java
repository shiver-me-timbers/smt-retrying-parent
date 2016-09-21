package shiver.me.timbers.retrying.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shiver.me.timbers.retrying.execution.RetryerIntervalIncrementsClass;

@Component
public class AutoProxyRetryerIntervalIncrementsClassFactory extends RetryerIntervalIncrementsClassFactory {

    @Autowired
    public AutoProxyRetryerIntervalIncrementsClassFactory(RetryerIntervalIncrementsClass retryerIntervalIncrements) {
        super(retryerIntervalIncrements);
    }
}
