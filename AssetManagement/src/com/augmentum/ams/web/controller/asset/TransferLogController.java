package com.augmentum.ams.web.controller.asset;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.asset.TransferLog;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.asset.TransferLogService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Controller("transferLogController")
@RequestMapping(value = "/transferLog")
public class TransferLogController extends BaseController {

	Logger logger = Logger.getLogger(TransferLogController.class);

	@Autowired
	private TransferLogService transferLogService;
	@Autowired
	private UserCustomColumnsService userCustomColumnsService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listTransferLog(HttpServletRequest request, String id) {

		logger.info("listTransferLog method start!");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("transferLog/transferLogList");
		if (!StringUtils.isBlank(id)) {
			request.setAttribute("assetUuId", id);
		}

		logger.info("listTransferLog method end!");
		return modelAndView;
	}

	@RequestMapping("/search")
	public ModelAndView searchTransferLog(SearchCondition searchCondition,
			HttpSession session, String id) {

		logger.info("searchTransferLog method start!");

		if (null == searchCondition) {
			searchCondition = new SearchCondition();
		}
		Page<TransferLog> page = transferLogService
				.findTransferLogBySearchCondition(searchCondition, id);
		List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
				.findUserCustomColumns("transfer log", getUserIdByShiro());
		JSONArray array = SearchCommonUtil.formatTransferLogListTOJSONArray(
				page.getResult(), userCustomColumnList);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("fieldsData", array);
		modelAndView.addObject("count", page.getRecordCount());
		modelAndView.addObject("totalPage", page.getTotalPage());

		logger.info("searchTransferLog method end!");
		return modelAndView;
	}
}
