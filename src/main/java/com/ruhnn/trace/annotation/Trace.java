package com.ruhnn.trace.annotation;

import java.lang.annotation.*;

/**
 * @author damon
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Trace {
}
