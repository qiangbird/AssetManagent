package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.SoftwareDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Software;


@Repository("softwareDao")
public class SoftwareDaoImpl extends BaseDaoImpl<Software> implements SoftwareDao{

}
