package com.ant.schedule.selfannotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//自定义注解，主要用于方法的明细或者类的说明
public @interface Comment {
    String value() default "";
}
