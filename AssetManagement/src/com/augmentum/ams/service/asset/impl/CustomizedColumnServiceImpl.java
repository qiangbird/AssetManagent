package com.augmentum.ams.service.asset.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.CustomizedColumnDao;
import com.augmentum.ams.model.asset.CustomizedColumn;
import com.augmentum.ams.service.asset.CustomizedColumnService;
import com.augmentum.ams.util.LogHelper;

@Service("customizedColumnsService")
public class CustomizedColumnServiceImpl implements CustomizedColumnService {

    private static Logger logger = Logger.getLogger(CustomizedColumnServiceImpl.class);

    @Autowired
    private CustomizedColumnDao customizedColumnDao;

    @Override
    public List<CustomizedColumn> findDefaultCustomizedColumns(String category) {

        logger.info(LogHelper.getLogInfo("find default custom columns start", category));

        List<CustomizedColumn> customizedColumns = customizedColumnDao.findDefaultColumns(category);

        logger.info(LogHelper.getLogInfo("find default custom columns end, column size",
                customizedColumns.size()));
        return customizedColumns;
    }

    @Override
    public List<String> getValueOfColumn(String realName, String realTable) {

        String newRealName = changeRealName(realName);
        List<String> values = customizedColumnDao.getValueOfColumn(newRealName, realTable);

        return values;
    }

    private String changeRealName(String realName) {

        if ("user".equals(realName)) {
            realName = "userName";
        }

        if ("customer".equals(realName)) {
            realName = "customerName";
        }

        if ("project".equals(realName)) {
            realName = "projectName";
        }

        if ("location".equals(realName)) {
            realName = "site";
        }

        return realName;
    }

}
