package kr.co.tworld.shop.framework.annotation.profile;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Profile;

/**
 * Development Profile Annotation 
 * @author Sangjun, Park
 *
 */
@Retention(RUNTIME)
@Target({ TYPE, METHOD })
@Profile("dev")
public @interface Dev {

}
