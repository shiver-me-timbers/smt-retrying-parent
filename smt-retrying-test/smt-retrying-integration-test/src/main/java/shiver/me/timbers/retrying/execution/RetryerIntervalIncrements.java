package shiver.me.timbers.retrying.execution;

import java.util.concurrent.Callable;

public interface RetryerIntervalIncrements {
    <T> T intervalIncrementsMethod(Callable<T> callable) throws InterruptedException;
}
