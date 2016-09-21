package shiver.me.timbers.retrying;

import shiver.me.timbers.retrying.execution.RetryerIntervalIncrements;

import java.util.concurrent.TimeUnit;

public interface ITRetryerIntervalIncrements {

    void Can_set_the_interval_increments() throws Throwable;

    RetryerIntervalIncrements intervalIncrements(int retries, TimeUnit unit, Long... increments);
}
