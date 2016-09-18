package shiver.me.timbers.retrying.factory;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.retrying.execution.RetryerExcludes;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.retrying.random.RandomThrowables.someThrowablesArray;
import static shiver.me.timbers.retrying.util.Classes.toClasses;

public class RetryerExcludesFactoryTest {

    private LookupFactory<RetryerExcludes> lookupFactory;
    private RetryerExcludesFactory factory;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        lookupFactory = mock(LookupFactory.class);
        factory = new RetryerExcludesFactory(lookupFactory);
    }

    @Test
    public void Can_create_a_waiting_exclude_with_some_throwable() {

        final int retries = someInteger();
        final Throwable[] excludes = someThrowablesArray();

        final RetryerExcludes expected = mock(RetryerExcludes.class);

        // Given
        given(lookupFactory.find(retries, toClasses(asList(excludes)))).willReturn(expected);

        // When
        final RetryerExcludes actual = factory.create(retries, excludes);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_add_a_waiting_exclude() {

        // Given
        final RetryerExcludes waitingExclude = mock(RetryerExcludes.class);
        final int retries = someInteger();
        final Throwable[] excludes = someThrowablesArray();

        // When
        factory.add(waitingExclude, retries, excludes);

        // Then
        verify(lookupFactory).add(waitingExclude, retries, toClasses(asList(excludes)));
    }
}