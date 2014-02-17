package com.augmentum.ams.dao.todo;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.User;

public interface ToDoDao extends BaseDao<ToDo>{

	/**
	 * @author Geoffrey.Zhao
	 * @return
	 */
	List<ToDo> findReturnedAsset();
	
	/**
	 * @author Geoffrey.Zhao
	 * @return
	 */
	ToDo getToDoById(String id);
	
	/**
	 * @author Geoffrey.Zhao
	 * @return
	 */
	List<ToDo> findReceivedAsset(User user);
	
}
