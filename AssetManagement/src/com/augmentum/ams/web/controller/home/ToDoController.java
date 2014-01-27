package com.augmentum.ams.web.controller.home;

import java.util.List;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.service.todo.ToDoService;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.controller.base.BaseController;

@Controller("toDoController")
@RequestMapping(value="/todo")
public class ToDoController extends BaseController{

	@Autowired
	private ToDoService todoService;
	@Autowired
	private UserCustomColumnsService userCustomColumnsService;
	
	@RequestMapping(value = "/viewReturnedAsset", method = RequestMethod.GET)
	public ModelAndView viewReturnedAsset(HttpSession session) {
		
		ModelAndView modelAndView = new ModelAndView("todo/returnedAssetList");
		
		List<ToDo> todoList = todoService.findReturnedAsset();
		List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
				.findUserCustomColumns("todo", getUserIdByShiro());
		String timeOffset = (String) session.getAttribute("timeOffset");
		
		JSONArray array = SearchCommonUtil.formatReturnedAssetTOJSONArray(todoList, userCustomColumnList, timeOffset);
		
		modelAndView.addObject("fieldsData", array);
		modelAndView.addObject("count", todoList.size());
		modelAndView.addObject("totalPage", 1);
		return modelAndView;
	}
	
	@RequestMapping(value = "/viewReturnedAssetPanel", method = RequestMethod.GET)
	public JSONObject viewReturnedAssetPanel() {
		
		List<ToDo> todoList = todoService.findReturnedAsset();
		
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < todoList.size() && i < 3; i++) {
			
			JSONObject obj = new JSONObject();
			ToDo todo = todoList.get(i);
			
			obj.put("id", todo.getId());
			obj.put("assetName", todo.getAsset().getAssetName());
			if (null == todo.getAsset().getProject()) {
				obj.put("projectName", "");
			} else {
				obj.put("projectName", todo.getAsset().getProject().getProjectName());
			}
			obj.put("returnedTime", UTCTimeUtil.formatDateToString(todo.getReturnedTime()));
			
			jsonArray.add(obj);
		}
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("todoList", jsonArray);
		return jsonObject;
	}
	
	@RequestMapping(value = "/confirmReturnedAsset")
	@ResponseBody
	public String confirmReturnedAsset(String ids) {
		
		todoService.confirmReturnedAndReceivedAsset(ids, "RETURNED");
		return null;
	}
	
	// TODO temp redirect test
	@RequestMapping(value = "/redirectDashboard")
	public String redirect() {
		return "home/dashboard";
	}
	
	//TODO redirect returnedAssetList page
	@RequestMapping(value = "/redirectReturnedAssetList")
	public String redirectListPage() {
		return "asset/returnedAssetList";
	}
}
