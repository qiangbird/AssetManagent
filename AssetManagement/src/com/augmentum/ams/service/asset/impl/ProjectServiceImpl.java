package com.augmentum.ams.service.asset.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.ProjectDao;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.ProjectService;
import com.augmentum.ams.web.vo.asset.AssetVo;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

    @Override
    public List<Project> getAllProject() {
        return projectDao.findAll(Project.class);
    }

    @Override
    public List<User> getProjectManagerByProjectCodes(String projectCodes) {
        List<User> pmList = new ArrayList<User>();
        return pmList;
    }

    @Override
    public List<Project> getProjectByCustomerCode(String customerCode) {
        List<Project> list = projectDao.getProjectByCustomerCode(customerCode);
        return list;
    }

    @Override
    public Project getProjectByProjectCode(String projectCode) {
        return projectDao.getProjectByProjectCode(projectCode);
    }

    @Override
    public void saveProject(Project project) {
        projectDao.save(project);
    }

    @Override
    public Project getProjectForAsset(AssetVo assetVo, Asset asset) {
        Project project = null;
        if (assetVo.getProject().getProjectCode() != ""
                && assetVo.getProject().getProjectName() != "") {
            project = getProjectByProjectCode(assetVo.getProject().getProjectCode());

            if (null == project) {
                Project newProject = new Project();
                newProject.setProjectCode(assetVo.getProject().getProjectCode());
                newProject.setProjectName(assetVo.getProject().getProjectName());
                newProject.setCustomer(asset.getCustomer());
                saveProject(newProject);
                project = getProjectByProjectCode(assetVo.getProject().getProjectCode());
            }
        }
        return project;
    }

    @Override
    public Map<String, Project> findAllCustomersFromLocal() {

        Map<String, Project> localProjects = new HashMap<String, Project>();
        List<Project> projects = projectDao.findAll(Project.class);

        for (Project project : projects) {
            localProjects.put(project.getProjectName(), project);
        }
        return localProjects;
    }

}
