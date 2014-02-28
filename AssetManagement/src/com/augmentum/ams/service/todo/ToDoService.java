package com.augmentum.ams.service.todo;

import java.util.List;

import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public interface ToDoService {

	/**
	 * @author Geoffrey.Zhao
	 * @return
	 */
	List<ToDo> findReturnedAsset();
	
	/**
	 * @author Geoffrey.Zhao
	 */
	void confirmReturnedAndReceivedAsset(String ids, String status);
	
	/**
	 * @author Geoffrey.Zhao
	 * @return
	 */
	List<ToDo> findReceivedAsset(User user);
	
	/**
	 * @author Geoffrey.Zhao
	 * @param condition
	 * @return
	 */
	Page<ToDo> findToDoListBySearchCondition(SearchCondition condition, User user);
	
}
