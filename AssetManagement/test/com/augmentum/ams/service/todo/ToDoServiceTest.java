package com.augmentum.ams.service.todo;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.util.SearchCommonUtil;

public class ToDoServiceTest extends BaseCaseTest {

	@Autowired
	private ToDoService todoService;
	@Autowired
	private UserCustomColumnsService userCustomColumnsService;
	
	
	private Logger logger = Logger.getLogger(ToDoServiceTest.class);
	
	@Test
	public void testFindReturnedAsset() {
		List<ToDo> list = todoService.findReturnedAsset();
		for (ToDo todo : list) {
			logger.info(list.size() + "--" + todo.getAsset().getAssetId());
		}
		
		List<UserCustomColumn> userCustomColumnList = userCustomColumnsService.findUserCustomColumns("todo", "T00245");
		JSONArray array = SearchCommonUtil.formatReturnedAssetTOJSONArray(list, userCustomColumnList, "8,0");
		logger.info(array.size() + "--" + array);
	} 
	
	@Test
	public void testConfirmReturnedAsset() {
		todoService.confirmReturnedAndReceivedAsset("4028961242fa20eb0142fb206b5c123d", "RETURNED");
	}
}
