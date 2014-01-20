package com.augmentum.ams.dao.asset.impl;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.ProjectDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Project;

@Repository("projectDao")
public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao {

    @Override
    public Project getManagerByProjectCode(String projectCode) {
        String hql = "from Project where projectCode=?";
        List<Project> list = super.find(hql, projectCode);
        return list.get(0);
    }

    @Override
    public List<Project> getProjectByCustomerCode(String customerCode) {
        String hql = "from Project where customer.customerCode=?";
        List<Project> list = super.find(hql, customerCode);
        return list;
    }

    @Override
    public Project getProjectByProjectCode(String projectCode) {
        String hql = "from Project where projectCode = ?";
        return super.getUnique(hql, projectCode);
    }

}
