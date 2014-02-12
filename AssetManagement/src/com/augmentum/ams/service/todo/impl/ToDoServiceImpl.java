package com.augmentum.ams.service.todo.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.dao.todo.ToDoDao;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.service.todo.ToDoService;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.FormatUtil;

@Service("toDoService")
public class ToDoServiceImpl implements ToDoService{

	@Autowired
	private ToDoDao todoDao;
	@Autowired
	private BaseDao<ToDo> baseDao;
	
	private Logger logger = Logger.getLogger(ToDoServiceImpl.class);
	
	@Override
	public List<ToDo> findReturnedAsset() {
		return todoDao.findReturnedAsset();
	}

	@Override
	public void confirmReturnedAndReceivedAsset(String ids, String status) {
		
		logger.info("enter confirmReturnedAndReceivedAsset method successfully, parameters [ids], [status]: " 
						+ ids + "," + status);
		String[] idArr = FormatUtil.splitString(ids, Constant.SPLIT_COMMA);
		
		for (String id : idArr) {
			
			ToDo todo = todoDao.getToDoById(id);
			Asset asset = todo.getAsset();
			
			if (null != asset) {
				asset.setStatus(status);
				
				if ("RETURNED".equals(status)) {
					asset.setUser(null);
				} 
				
				baseDao.update(todo);
			}
			baseDao.delete(todo);
		}
		logger.info("leave confirmReturnedAndReceivedAsset method successfully");
	}

	@Override
	public List<ToDo> findReceivedAsset() {
		return todoDao.findReceivedAsset();
	}
	
}
