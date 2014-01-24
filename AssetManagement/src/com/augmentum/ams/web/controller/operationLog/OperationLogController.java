package com.augmentum.ams.web.controller.operationLog;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.asset.TransferLog;
import com.augmentum.ams.model.operationLog.OperationLog;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.operationLog.OperationLogService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Controller("operationLogController")
@RequestMapping(value = "/operationLog")
public class OperationLogController extends BaseController {

	Logger logger = Logger.getLogger(OperationLogController.class);
	@Autowired
	private OperationLogService operationLogService;
	@Autowired
	private UserCustomColumnsService userCustomColumnsService;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String listOperationLog() {
		return "operationLog/operationLogList";
	}

	@RequestMapping("/search")
	public ModelAndView searchTransferLog(SearchCondition searchCondition,
			HttpSession session, String id) {

		logger.info("searchTransferLog method start!");

		String clientTimeOffset = (String) session.getAttribute("timeOffset");

		if (null == searchCondition) {
			searchCondition = new SearchCondition();
		}
		Page<OperationLog> page = operationLogService
				.findOperationLogBySearchCondition(searchCondition);
		List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
				.findUserCustomColumns("operation log", getUserIdByShiro());
		JSONArray array = SearchCommonUtil.formatOperationLogListTOJSONArray(
				page.getResult(), userCustomColumnList, clientTimeOffset);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("fieldsData", array);
		modelAndView.addObject("count", page.getRecordCount());
		modelAndView.addObject("totalPage", page.getTotalPage());

		logger.info("searchTransferLog method end!");
		return modelAndView;
	}

}
