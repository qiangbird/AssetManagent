package com.augmentum.ams.dao.asset;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.asset.Project;

public interface ProjectDao extends BaseDao<Project> {

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 5, 2013 7:33:15 PM
     * @param projectCode
     * @return
     */
    public Project getManagerByProjectCode(String projectCode);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 5, 2013 7:33:17 PM
     * @param customerCode
     * @return
     */
    public List<Project> getProjectByCustomerCode(String customerCode);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 5, 2013 7:33:22 PM
     * @param projectCode
     * @return
     */
    public Project getProjectByProjectCode(String projectCode);

}
