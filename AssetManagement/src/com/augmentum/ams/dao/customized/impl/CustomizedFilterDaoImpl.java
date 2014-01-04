package com.augmentum.ams.dao.customized.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.customized.CustomizedFilterDao;
import com.augmentum.ams.model.customized.CustomizedFilter;


@Repository("customizedFilterDao")
public class CustomizedFilterDaoImpl extends BaseDaoImpl<CustomizedFilter> implements CustomizedFilterDao{

}
