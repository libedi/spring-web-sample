package kr.co.tworld.shop.common.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

/**
 * MessageSource Utility class
 * @author Sang jun, Park
 *
 */
@Component
@RequiredArgsConstructor
public class MessageSourceUtil {
	
	private final MessageSource messageSource;
	
	/**
	 * get message
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessage(final String code, @Nullable final Object... args) {
		return this.getMessage(code, LocaleContextHolder.getLocale(), args);
	}
	
	/**
	 * get message
	 * @param code
	 * @param locale
	 * @param args
	 * @return
	 */
	public String getMessage(final String code, final Locale locale, @Nullable final Object... args) {
		return this.messageSource.getMessage(code, args, locale);
	}
	
}
