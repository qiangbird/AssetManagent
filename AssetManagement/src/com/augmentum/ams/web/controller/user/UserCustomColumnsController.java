package com.augmentum.ams.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.web.controller.base.BaseController;

@Controller("userCustomColumnsController")
@RequestMapping(value="/userCustomColumn")
public class UserCustomColumnsController extends BaseController{
	
	@Autowired
	private UserCustomColumnsService userCustomColumnsService;

	@RequestMapping(value="/initUserCustomColumn")
	@ResponseBody
	public String initUserCustomColumn() {
	    String userId = getUserIdByShiro();
	    userCustomColumnsService.initUserCustomColumn(userId);
	    return null;
	}
}
