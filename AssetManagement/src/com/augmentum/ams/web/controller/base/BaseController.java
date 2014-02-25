package com.augmentum.ams.web.controller.base;

import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.exception.AuthorityException;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.exception.RemoteException;
import com.augmentum.ams.exception.ValidatorException;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("baseController")
@RequestMapping(value = "/base")
public abstract class BaseController {

    public String getUserIdByShiro() {

        String userId = getUserVoByShiro().getEmployeeId();

        return userId;
    }

    public String getUserNameByShiro() {

        return getUserVoByShiro().getEmployeeName();
    }

    public UserVo getUserVoByShiro() {

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();

        return (UserVo) session.getAttribute("userVo");
    }

    @ExceptionHandler({ ExcelException.class })
    @ResponseBody
    protected JSONObject handleExcelException(ExcelException e) {

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("error", e.getErrorCode());

        return jSONObject;
    }

    @ExceptionHandler({ AuthorityException.class })
    @ResponseBody
    protected ModelAndView handleAuthorityException(AuthorityException e) {

        ModelAndView mv = new ModelAndView(SystemConstants.AUTHORITY_ERROR_PAGE);

        return mv;
    }

    @ExceptionHandler({ RemoteException.class, DataAccessException.class, BusinessException.class,
            Exception.class })
    protected ModelAndView handleServerErrorException() {

        ModelAndView mv = new ModelAndView(SystemConstants.SERVER_ERROR_PAGE);

        return mv;
    }

    @ExceptionHandler({ ValidatorException.class })
    @ResponseBody
    protected JSONObject handleValidatorException(ValidatorException ve) {

        JSONObject jSONObject = new JSONObject();
        jSONObject.put("error", ve.getErrorCode().replaceAll(",", " ").trim());

        return jSONObject;
    }

}
