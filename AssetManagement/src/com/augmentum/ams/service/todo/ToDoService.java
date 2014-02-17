package com.augmentum.ams.service.todo;

import java.util.List;

import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.User;

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
	
}
