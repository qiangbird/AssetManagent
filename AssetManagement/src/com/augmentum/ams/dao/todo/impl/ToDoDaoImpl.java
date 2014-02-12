package com.augmentum.ams.dao.todo.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.todo.ToDoDao;
import com.augmentum.ams.model.todo.ToDo;


@Repository("toDoDao")
public class ToDoDaoImpl extends BaseDaoImpl<ToDo> implements ToDoDao{

	@Override
	public List<ToDo> findReturnedAsset() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ToDo.class);
		criteria.setFetchMode("asset", FetchMode.JOIN)
				.setFetchMode("returner", FetchMode.JOIN)
				.setFetchMode("assigner", FetchMode.JOIN)
				.setFetchMode("asset.customer", FetchMode.JOIN)
				.setFetchMode("asset.project", FetchMode.JOIN)
				.setFetchMode("asset.user", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("isExpired", Boolean.FALSE))
				.add(Restrictions.isNotNull("returnedTime"))
				.addOrder(Order.desc("returnedTime"));
		
		return findByCriteria(criteria);
	}

	@Override
	public ToDo getToDoById(String id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ToDo.class).setFetchMode("asset", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("isExpired", Boolean.FALSE))
				.add(Restrictions.eq("id", id));
		return getUnique(criteria);
	}

	@Override
	public List<ToDo> findReceivedAsset() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ToDo.class);
		criteria.setFetchMode("asset", FetchMode.JOIN)
				.setFetchMode("returner", FetchMode.JOIN)
				.setFetchMode("assigner", FetchMode.JOIN)
				.setFetchMode("asset.customer", FetchMode.JOIN)
				.setFetchMode("asset.project", FetchMode.JOIN)
				.setFetchMode("asset.user", FetchMode.JOIN);
		
		criteria.add(Restrictions.eq("isExpired", Boolean.FALSE))
				.add(Restrictions.isNotNull("receivedTime"))
				.addOrder(Order.desc("receivedTime"));
		
		return findByCriteria(criteria);
	}

}
