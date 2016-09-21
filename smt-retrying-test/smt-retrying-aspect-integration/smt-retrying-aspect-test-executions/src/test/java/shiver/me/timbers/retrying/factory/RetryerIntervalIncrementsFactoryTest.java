package shiver.me.timbers.retrying.factory;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.retrying.execution.RetryerIntervalIncrements;

import java.util.concurrent.TimeUnit;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomEnums.someEnum;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.data.random.RandomLongs.someLong;

public class RetryerIntervalIncrementsFactoryTest {

    private LookupFactory<RetryerIntervalIncrements> lookupFactory;
    private RetryerIntervalIncrementsFactory factory;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        lookupFactory = mock(LookupFactory.class);
        factory = new RetryerIntervalIncrementsFactory(lookupFactory);
    }

    @Test
    public void Can_create_a_retryer_interval_increment() {

        final Integer retries = someInteger();
        final TimeUnit unit = someEnum(TimeUnit.class);
        final Long duration1 = someLong();
        final Long duration2 = someLong();
        final Long duration3 = someLong();

        final RetryerIntervalIncrements expected = mock(RetryerIntervalIncrements.class);

        // Given
        given(lookupFactory.find(retries, unit, asList(duration1, duration2, duration3))).willReturn(expected);

        // When
        final RetryerIntervalIncrements actual = factory.create(retries, unit, duration1, duration2, duration3);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_add_a_retryer_interval_increment() {

        // Given
        final RetryerIntervalIncrements retryerIntervalIncrements = mock(RetryerIntervalIncrements.class);
        final Integer retries = someInteger();
        final Long duration1 = someLong();
        final Long duration2 = someLong();
        final Long duration3 = someLong();
        final TimeUnit unit = someEnum(TimeUnit.class);

        // When
        factory.add(retryerIntervalIncrements, retries, unit, duration1, duration2, duration3);

        // Then
        verify(lookupFactory).add(retryerIntervalIncrements, retries, unit, asList(duration1, duration2, duration3));
    }
}