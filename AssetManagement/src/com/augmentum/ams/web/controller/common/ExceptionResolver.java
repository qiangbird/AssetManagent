package com.augmentum.ams.web.controller.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.ExceptionUtil;

/**
 * @author Rudy.Gao
 * @time Sep 28, 2013 4:23:22 PM
 */
public class ExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,HttpServletResponse response,Object handler,Exception e) {
		ModelAndView modelAndView = new ModelAndView(); 
		if(e instanceof BaseException) {
			BaseException baseException = (BaseException) e;
			modelAndView.addObject("errorCode", baseException.getErrorCode());
         	modelAndView.addObject("errorMessage", baseException.getErrorMessage());
		}
		else {
			modelAndView.addObject("errorCode", ErrorCodeUtil.UNKNOWN_EXCEPTION_0401001);
			modelAndView.addObject("errorMessage", ExceptionUtil.processErrorMessageByErrorCode(ErrorCodeUtil.UNKNOWN_EXCEPTION_0401001));
		}
		String viewName = super.determineViewName(e, request);
		modelAndView.setViewName(viewName);
     	return modelAndView;
	}
}
