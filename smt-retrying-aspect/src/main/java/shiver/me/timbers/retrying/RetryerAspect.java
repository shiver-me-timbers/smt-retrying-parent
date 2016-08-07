/*
 * Copyright 2016 Karl Bennett
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shiver.me.timbers.retrying;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.Method;

/**
 * Use this aspect to add support for the {@link Retry} annotation at the class and method level.
 *
 * @author Karl Bennett
 */
@Aspect
public class RetryerAspect {

    private final Factory<RetryerService, OptionsService> retryerLoader;
    private final Factory<OptionsService, Retry> optionsLoader;

    public RetryerAspect() {
        this(
            new ServiceClassLoader<>(RetryerService.class, Retryer.class).load(),
            new ServiceClassLoader<>(OptionsService.class, Options.class).load()
        );
    }

    private RetryerAspect(Class<RetryerService> retryerServiceClass, Class<OptionsService> optionsServiceClass) {
        this(
            new RetryerServiceFactory(new Instantiater<>(retryerServiceClass, optionsServiceClass)),
            new OptionsServiceFactory(new Instantiater<>(optionsServiceClass), new OptionsServiceConfigurer())
        );
    }

    RetryerAspect(
        Factory<RetryerService, OptionsService> retryerLoader,
        Factory<OptionsService, Retry> optionsLoader
    ) {
        this.retryerLoader = retryerLoader;
        this.optionsLoader = optionsLoader;
    }

    @Around("@within(retry) && isMethod()")
    public Object retryOnClass(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        if (methodHasRetry(joinPoint)) {
            return joinPoint.proceed();
        }

        return retry(joinPoint, retry);
    }

    @Around("@annotation(retry) && isMethod()")
    public Object retryOnMethod(ProceedingJoinPoint joinPoint, Retry retry) throws Exception {
        return retry(joinPoint, retry);
    }

    @Pointcut("execution(* *(..))")
    private void isMethod() {
    }

    @SuppressWarnings("unchecked")
    private Object retry(final ProceedingJoinPoint joinPoint, Retry retry) throws Exception {
        return retryerLoader.create(optionsLoader.create(retry)).retry(new Until() {
            @Override
            public Object success() throws Throwable {
                return joinPoint.proceed();
            }

            @Override
            public String toString() {
                return joinPoint.toString();
            }
        });
    }

    private static boolean methodHasRetry(ProceedingJoinPoint joinPoint) {
        final Signature signature = joinPoint.getSignature();
        final String methodName = signature.getName();

        for (Method method : signature.getDeclaringType().getDeclaredMethods()) {
            if (methodName.equals(method.getName())) {
                return method.isAnnotationPresent(Retry.class);
            }
        }

        return false;
    }
}
