package shiver.me.timbers.retrying.factory;

import org.junit.Before;
import org.junit.Test;
import shiver.me.timbers.retrying.execution.RetryerExcludes;

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

public class RetryerExcludesWithIncludesFactoryTest {

    private LookupFactory<RetryerExcludes> lookupFactory;
    private RetryerExcludesWithIncludesFactory factory;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        lookupFactory = mock(LookupFactory.class);
        factory = new RetryerExcludesWithIncludesFactory(lookupFactory);
    }

    @Test
    public void Can_create_a_waiting_exclude() {

        final int retries = someInteger();
        final List<Throwable> excludes = someThrowables();
        final List<Throwable> includes = someOtherThrowables();

        final RetryerExcludes expected = mock(RetryerExcludes.class);

        // Given
        given(lookupFactory.find(retries, toClasses(excludes), toClasses(includes))).willReturn(expected);

        // When
        final RetryerExcludes actual = factory.create(retries, excludes, includes);

        // Then
        assertThat(actual, is(expected));
    }

    @Test
    public void Can_add_a_waiting_exclude() {

        // Given
        final RetryerExcludes RetryerExcludes = mock(RetryerExcludes.class);
        final int retries = someInteger();
        final List<Throwable> excludes = someThrowables();
        final List<Throwable> includes = someOtherThrowables();

        // When
        factory.add(RetryerExcludes, retries, excludes, includes);

        // Then
        verify(lookupFactory).add(RetryerExcludes, retries, toClasses(excludes), toClasses(includes));
    }
}