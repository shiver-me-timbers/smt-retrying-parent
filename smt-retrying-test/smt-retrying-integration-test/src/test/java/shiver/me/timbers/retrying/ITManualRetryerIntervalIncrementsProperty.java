package shiver.me.timbers.retrying;

import org.junit.Rule;
import shiver.me.timbers.retrying.execution.ManualRetryerDefaults;
import shiver.me.timbers.retrying.execution.ManualRetryerIntervalncrements;
import shiver.me.timbers.retrying.execution.RetryerDefaults;
import shiver.me.timbers.retrying.execution.RetryerIntervalIncrements;
import shiver.me.timbers.retrying.junit.RetryerPropertyRule;
import shiver.me.timbers.retrying.property.SystemPropertyManager;

import java.util.concurrent.TimeUnit;

public class ITManualRetryerIntervalIncrementsProperty extends AbstractITRetryerIntervalIncrementsProperty {

    @Rule
    public RetryerPropertyRule properties = new RetryerPropertyRule(new SystemPropertyManager());

    @Override
    public RetryerPropertyRule properties() {
        return properties;
    }

    @Override
    public RetryerDefaults defaults() {
        return new ManualRetryerDefaults();
    }

    @Override
    protected RetryerIntervalIncrements overrideIntervalIncrements(int retries, TimeUnit unit, Long... durations) {
        return new ManualRetryerIntervalncrements<>(retries, unit, durations);
    }
}
