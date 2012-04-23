package com.gilt.jdbi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.skife.jdbi.v2.sqlobject.BindingAnnotation;

/**
 * Similar to @BindBean, allows for convenient binding
 * of a case class object in a JDBI SQLObject query
 */

/*
 * Note that annotations with runtime retention
 * cannot be implemented in Scala at this time
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@BindingAnnotation(BindCaseClassFactory.class)
public @interface BindCaseClass
{
    String value() default "___jdbi_bare___";
}
