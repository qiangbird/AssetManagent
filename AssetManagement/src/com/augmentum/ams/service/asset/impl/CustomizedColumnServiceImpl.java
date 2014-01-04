package com.augmentum.ams.service.asset.impl;

import java.util.ArrayList;
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

        List<String> values = customizedColumnDao.getValueOfColumn(realName, realTable);

        return values;
    }

}
