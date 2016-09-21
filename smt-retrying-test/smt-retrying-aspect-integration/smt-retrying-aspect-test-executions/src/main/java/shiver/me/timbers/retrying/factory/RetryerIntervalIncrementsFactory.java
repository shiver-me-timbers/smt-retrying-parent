package shiver.me.timbers.retrying.factory;

import shiver.me.timbers.retrying.execution.RetryerIntervalIncrements;

import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static shiver.me.timbers.retrying.util.Constants.DURATION1;
import static shiver.me.timbers.retrying.util.Constants.DURATION2;
import static shiver.me.timbers.retrying.util.Constants.DURATION3;
import static shiver.me.timbers.retrying.util.Constants.INCREMENT_RETRIES;

public class RetryerIntervalIncrementsFactory {

    private final LookupFactory<RetryerIntervalIncrements> lookupFactory;

    public RetryerIntervalIncrementsFactory(RetryerIntervalIncrements retryerIntervalIncrements) {
        this(new MapLookupFactory<RetryerIntervalIncrements>());
        add(retryerIntervalIncrements, INCREMENT_RETRIES, MILLISECONDS, DURATION1, DURATION2, DURATION3);
    }

    public RetryerIntervalIncrementsFactory(LookupFactory<RetryerIntervalIncrements> lookupFactory) {
        this.lookupFactory = lookupFactory;
    }

    public RetryerIntervalIncrements create(Integer retries, TimeUnit unit, Long... durations) {
        return lookupFactory.find(retries, unit, asList(durations));
    }

    public void add(RetryerIntervalIncrements retryerIntervalIncrements, Integer retries, TimeUnit unit, Long... durations) {
        lookupFactory.add(retryerIntervalIncrements, retries, unit, asList(durations));
    }
}
