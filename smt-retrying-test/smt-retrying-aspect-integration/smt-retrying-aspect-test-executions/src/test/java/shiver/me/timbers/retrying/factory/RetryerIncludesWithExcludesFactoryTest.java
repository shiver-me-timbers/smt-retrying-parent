package shiver.me.timbers.retrying.factory;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.retrying.execution.RetryerIncludes;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static shiver.me.timbers.data.random.RandomIntegers.someInteger;
import static shiver.me.timbers.retrying.random.RandomThrowables.someOtherThrowables;
import static shiver.me.timbers.retrying.random.RandomThrowables.someThrowables;
import static shiver.me.timbers.retrying.util.Classes.toClasses;

public class RetryerIncludesWithExcludesFactoryTest {

    private LookupFactory<RetryerIncludes> lookupFactory;
    private RetryerIncludesWithExcludesFactory factory;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        lookupFactory = mock(LookupFactory.class);
        factory = new RetryerIncludesWithExcludesFactory(lookupFactory);
    }

    @Test
    public void Can_create_a_waiting_exclude() {

        final int retries = someInteger();
        final List<Throwable> excludes = someThrowables();
        final List<Throwable> includes = someOtherThrowables();

        final RetryerIncludes expected = mock(RetryerIncludes.class);

        // Given
        given(lookupFactory.find(retries, toClasses(excludes), toClasses(includes))).willReturn(expected);

        // When
        final RetryerIncludes actual = factory.create(retries, excludes, includes);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_add_a_waiting_exclude() {

        // Given
        final RetryerIncludes RetryerIncludes = mock(RetryerIncludes.class);
        final int retries = someInteger();
        final List<Throwable> excludes = someThrowables();
        final List<Throwable> includes = someOtherThrowables();

        // When
        factory.add(RetryerIncludes, retries, excludes, includes);

        // Then
        verify(lookupFactory).add(RetryerIncludes, retries, toClasses(excludes), toClasses(includes));
    }
}