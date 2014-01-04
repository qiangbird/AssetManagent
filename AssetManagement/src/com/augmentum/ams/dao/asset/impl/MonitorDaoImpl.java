package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.MonitorDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Monitor;


@Repository("monitorDao")
public class MonitorDaoImpl extends BaseDaoImpl<Monitor> implements MonitorDao{

}
