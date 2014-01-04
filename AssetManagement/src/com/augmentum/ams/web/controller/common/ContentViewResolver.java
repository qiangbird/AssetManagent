package com.augmentum.ams.web.controller.common;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 * @author Rudy.Gao
 * @time Sep 29, 2013 5:45:57 PM
 */
public class ContentViewResolver extends ContentNegotiatingViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale) throws Exception {
		HttpServletRequest request =
		        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
		                .getRequest();
		String contentType = request.getContentType();
		if (contentType != null && contentType.indexOf("application/json") > -1) {
			return new MappingJacksonJsonView();
		}
		return super.resolveViewName(viewName, locale);
	}

}
