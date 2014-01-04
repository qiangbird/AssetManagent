package com.augmentum.ams.service.asset;

import java.util.List;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.web.vo.asset.AssetVo;

public interface ProjectService {
    /**
     * 
     * @author Jay.He
     * @description Get All Project
     * 
     **/
    public List<Project> getAllProject();

    /**
     * 
     * @author Jay.He
     * @time Dec 9, 2013 2:12:38 PM
     * @param projectCodes
     * @return
     */
    public List<User> getProjectManagerByProjectCodes(String projectCodes);

    /**
     * 
     * @author Jay.He
     * @time Dec 9, 2013 2:12:42 PM
     * @param projectCode
     * @return
     */
    public User getProjectManagerByProjectCode(String projectCode);

    /**
     * 
     * @author Jay.He
     * @time Dec 9, 2013 2:12:47 PM
     * @param customerCode
     * @return
     */
    public List<Project> getProjectByCustomerCode(String customerCode);

    /**
     * 
     * @author Jay.He
     * @time Dec 9, 2013 2:12:51 PM
     * @param projectCode
     * @return
     */
    public Project getProjectByProjectCode(String projectCode);

    /**
     * 
     * @author Jay.He
     * @time Dec 9, 2013 2:12:54 PM
     * @param project
     */
    public void saveProject(Project project);

    /**
     * 
     * @author Jay.He
     * @time Dec 9, 2013 2:13:07 PM
     * @param assetVo
     * @param asset
     * @return
     */
    public Project getProjectForAsset(AssetVo assetVo, Asset asset);

}
