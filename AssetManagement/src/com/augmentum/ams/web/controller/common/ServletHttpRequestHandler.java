package com.augmentum.ams.web.controller.common;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

/**
 * @author Rudy.Gao
 * @time Oct 10, 2013 10:09:14 AM
 */

public class ServletHttpRequestHandler extends DefaultServletHttpRequestHandler{
	
	private static Logger logger = Logger.getLogger(ServletHttpRequestHandler.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			super.handleRequest(request, response);
        } catch (Exception e) {
        	logger.warn("Server has already response to client, but server tries to jump again");
	        return ; 
        }
	}

}
