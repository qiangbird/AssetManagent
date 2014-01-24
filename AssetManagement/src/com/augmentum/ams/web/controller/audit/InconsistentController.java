package com.augmentum.ams.web.controller.audit;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.Inconsistent;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.audit.InconsistentService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.util.FormatEntityListToEntityVoList;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.AssetListVo;
import com.augmentum.ams.web.vo.audit.AuditVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Controller("inconsistentController")
@RequestMapping(value = "/inconsistent")
public class InconsistentController extends BaseController {

	@Autowired
	private InconsistentService inconsistentService;

	@Autowired
	private UserCustomColumnsService userCustomColumnsService;

	@RequestMapping("/getInconsistentAssetsCount")
	@ResponseBody
	public int getInconsistentAssetsCount(AuditVo auditFileVo) throws Exception {

		return inconsistentService.getInconsistentAssetsCount(auditFileVo
				.getAuditFileName());
	}

	@RequestMapping("/findInconsistentAssets")
	@ResponseBody
	public JSONObject findInconsistentAssets(AuditVo auditVo,
			HttpServletRequest servletRequest) throws Exception {

		JSONObject jsonObject = new JSONObject();

		int countIncons = this.getInconsistentAssetsCount(auditVo);
		int sEcho = Integer.parseInt(servletRequest.getParameter("sEcho"));
		int iDisplayStart = Integer.parseInt(servletRequest
				.getParameter("iDisplayStart"));
		int iDisplayLength = Integer.parseInt(servletRequest
				.getParameter("iDisplayLength"));

		jsonObject.put("sEcho", sEcho);
		jsonObject.put("iTotalRecords", countIncons);
		jsonObject.put("iTotalDisplayRecords", countIncons);

		JSONArray incons = inconsistentService.findInconsistentAssets(
				auditVo.getAuditFileName(), iDisplayStart, iDisplayLength);
		jsonObject.put("aaData", incons);

		return jsonObject;
	}

	@RequestMapping(value = "/viewInconsistentAsset", method = RequestMethod.GET)
	public ModelAndView findAllAssetsBySearchCondition(
			SearchCondition searchCondition, HttpSession session)
			throws BaseException {

		if (null == searchCondition) {
			searchCondition = new SearchCondition();
		}
		Page<Asset> page = inconsistentService
				.findAssetForInconsistent(searchCondition);
		String clientTimeOffset = (String) session.getAttribute("timeOffset");
		List<AssetListVo> list = FormatEntityListToEntityVoList
				.formatAssetListToAssetVoList(page.getResult(),
						clientTimeOffset);
		List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
				.findUserCustomColumns("asset", getUserIdByShiro());
		JSONArray array = SearchCommonUtil.formatAssetVoListTOJSONArray(list,
				userCustomColumnList, null);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("fieldsData", array);
		modelAndView.addObject("count", page.getRecordCount());
		modelAndView.addObject("totalPage", page.getTotalPage());
		return modelAndView;
	}
	
	@RequestMapping(value = "/viewInconsistentBarcode", method = RequestMethod.GET)
	public ModelAndView findInconsistentBarcode(SearchCondition searchCondition) throws BaseException {
		
		if (null == searchCondition) {
			searchCondition = new SearchCondition();
		}
		Page<Inconsistent> page = inconsistentService.findInconsistentBarcode(searchCondition);
		
		JSONArray array = new JSONArray();
		
		for (Inconsistent incons : page.getResult()) {
			
			JSONArray json = new JSONArray();
			
			json.add(incons.getId());
			json.add(incons.getBarCode());
			array.add(json);
		}
		
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.addObject("fieldsData", array);
		modelAndView.addObject("count", page.getRecordCount());
		modelAndView.addObject("totalPage", page.getTotalPage());
		return modelAndView;
	}
}
