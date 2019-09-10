package io.github.shengchaojie.spring.extend.strategy;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * 由于spring对repeatable的api方法不是public，所以这边自己写一个
 * @author shengchaojie
 * @date 2019-09-10
 **/
public class RepeatableAnnotationUtil {

    private static final String REPEATABLE_CLASS_NAME = "java.lang.annotation.Repeatable";

    public static boolean existRepeatableAnnotation(Class<? extends Annotation> annotationType){
        Annotation repeatable = getAnnotation(annotationType, REPEATABLE_CLASS_NAME);
        if(repeatable != null){
            Object value = AnnotationUtils.getValue(repeatable);
            return value!=null;
        }
        return false;
    }


    static Annotation getAnnotation(AnnotatedElement element, String annotationName) {
        for (Annotation annotation : element.getAnnotations()) {
            if (annotation.annotationType().getName().equals(annotationName)) {
                return annotation;
            }
        }
        return null;
    }
}
