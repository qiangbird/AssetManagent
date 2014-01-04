package com.augmentum.ams.dao.timer.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.timer.SchedulerTaskDao;
import com.augmentum.ams.model.timer.SchedulerTask;


@Repository("schedulerTaskDao")
public class SchedulerTaskDaoImpl extends BaseDaoImpl<SchedulerTask> implements SchedulerTaskDao{

}
