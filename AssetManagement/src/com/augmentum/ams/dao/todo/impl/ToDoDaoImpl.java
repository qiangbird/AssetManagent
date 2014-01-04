package com.augmentum.ams.dao.todo.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.todo.ToDoDao;
import com.augmentum.ams.model.todo.ToDo;


@Repository("toDoDao")
public class ToDoDaoImpl extends BaseDaoImpl<ToDo> implements ToDoDao{

}
